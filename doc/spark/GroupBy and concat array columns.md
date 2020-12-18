GroupBy and concat array columns
---
Input data df:
```aidl
+-----+---------+
|store|   values|
+-----+---------+
|    1|[1, 2, 3]|
|    1|[4, 5, 6]|
|    2|      [2]|
|    2|      [3]|
+-----+---------+
```
Expected result:
```aidl
+-----+------------------+
|store|      values      |
+-----+------------------+
|    1|[1, 2, 3, 4, 5, 6]|
|    2|            [2, 3]|
+-----+------------------+
```

###Solution 1:
```aidl
from pyspark.sql import functions as F

df = sc.parallelize([(1, [1, 2, 3]), (1, [4, 5, 6]) , (2,[2]),(2,[3])]).toDF(['store', 'values'])

df2 = df.withColumn('values', F.explode('values'))
# +-----+------+
# |store|values|
# +-----+------+
# |    1|     1|
# |    1|     2|
# |    1|     3|
# |    1|     4|
# |    1|     5|
# |    1|     6|
# |    2|     2|
# |    2|     3|
# +-----+------+

// Note: you could use F.collect_set() in the aggregation or .drop_duplicates() on df2 to remove duplicate values. 
df3 = df2.groupBy('store').agg(F.collect_list('values').alias('values'))
# +-----+------------------+
# |store|           values |
# +-----+------------------+
# |1    |[4, 5, 6, 1, 2, 3]|
# |2    |[2, 3]            |
# +-----+------------------+

```

###Solution 2:
If you want to maintain ordered values in the collected list, I found the following method in another SO answer:
```aidl
from pyspark.sql.window import Window

w = Window.partitionBy('store').orderBy('values')
df3 = df2.withColumn('ordered_value_lists', F.collect_list('values').over(w))
# +-----+------+-------------------+
# |store|values|ordered_value_lists|
# +-----+------+-------------------+
# |1    |1     |[1]                |
# |1    |2     |[1, 2]             |
# |1    |3     |[1, 2, 3]          |
# |1    |4     |[1, 2, 3, 4]       |
# |1    |5     |[1, 2, 3, 4, 5]    |
# |1    |6     |[1, 2, 3, 4, 5, 6] |
# |2    |2     |[2]                |
# |2    |3     |[2, 3]             |
# +-----+------+-------------------+

df4 = df3.groupBy('store').agg(F.max('ordered_value_lists').alias('values'))
df4.show(truncate=False)
# +-----+------------------+
# |store|values            |
# +-----+------------------+
# |1    |[1, 2, 3, 4, 5, 6]|
# |2    |[2, 3]            |
# +-----+------------------+
```

If the values themselves don't determine the order, you can use F.posexplode() and use the 'pos' column in your window functions instead of 'values' to determine order. Note: you will also need a higher level order column to order the original arrays, then use the position in the array to order the elements of the array.
```aidl
df = sc.parallelize([(1, [1, 2, 3], 1), (1, [4, 5, 6], 2) , (2, [2], 1),(2, [3], 2)]).toDF(['store', 'values', 'array_order'])
# +-----+---------+-----------+
# |store|values   |array_order|
# +-----+---------+-----------+
# |1    |[1, 2, 3]|1          |
# |1    |[4, 5, 6]|2          |
# |2    |[2]      |1          |
# |2    |[3]      |2          |
# +-----+---------+-----------+

df2 = df.select('*', F.posexplode('values'))
# +-----+---------+-----------+---+---+
# |store|values   |array_order|pos|col|
# +-----+---------+-----------+---+---+
# |1    |[1, 2, 3]|1          |0  |1  |
# |1    |[1, 2, 3]|1          |1  |2  |
# |1    |[1, 2, 3]|1          |2  |3  |
# |1    |[4, 5, 6]|2          |0  |4  |
# |1    |[4, 5, 6]|2          |1  |5  |
# |1    |[4, 5, 6]|2          |2  |6  |
# |2    |[2]      |1          |0  |2  |
# |2    |[3]      |2          |0  |3  |
# +-----+---------+-----------+---+---+

w = Window.partitionBy('store').orderBy('array_order', 'pos')
df3 = df2.withColumn('ordered_value_lists', F.collect_list('col').over(w))
# +-----+---------+-----------+---+---+-------------------+
# |store|values   |array_order|pos|col|ordered_value_lists|
# +-----+---------+-----------+---+---+-------------------+
# |1    |[1, 2, 3]|1          |0  |1  |[1]                |
# |1    |[1, 2, 3]|1          |1  |2  |[1, 2]             |
# |1    |[1, 2, 3]|1          |2  |3  |[1, 2, 3]          |
# |1    |[4, 5, 6]|2          |0  |4  |[1, 2, 3, 4]       |
# |1    |[4, 5, 6]|2          |1  |5  |[1, 2, 3, 4, 5]    |
# |1    |[4, 5, 6]|2          |2  |6  |[1, 2, 3, 4, 5, 6] |
# |2    |[2]      |1          |0  |2  |[2]                |
# |2    |[3]      |2          |0  |3  |[2, 3]             |
# +-----+---------+-----------+---+---+-------------------+

df4 = df3.groupBy('store').agg(F.max('ordered_value_lists').alias('values'))
# +-----+------------------+
# |store|values            |
# +-----+------------------+
# |1    |[1, 2, 3, 4, 5, 6]|
# |2    |[2, 3]            |
# +-----+------------------+
```

###Solution 3:
```aidl
df = sc.parallelize([(1, [1, 2, 3]), (1, [4, 5, 6]) , (2,[2]),(2,[3])]).toDF(["store", "values"])
df.show()
+-----+---------+
|store|   values|
+-----+---------+
|    1|[1, 2, 3]|
|    1|[4, 5, 6]|
|    2|      [2]|
|    2|      [3]|
+-----+---------+

df.rdd.map(lambda r: (r.store, r.values)).reduceByKey(lambda x,y: x + y).toDF(['store','values']).show()
+-----+------------------+
|store|            values|
+-----+------------------+
|    1|[1, 2, 3, 4, 5, 6]|
|    2|            [2, 3]|
+-----+------------------+
```

###Solution 4 (applied since Spark 2.4):
```aidl
df = df.groupBy("store").agg(collect_list("values").alias("values"))

df = df.select("store", array_sort(array_distinct(expr("reduce(values, array(), (x,y) -> concat(x, y))"))).alias("values"))

```