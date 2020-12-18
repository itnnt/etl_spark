https://intellipaat.com/community/17272/collectlist-by-preserving-order-based-on-another-variable
Input data:
```
------------------------
id | date        | value
------------------------
1  |2014-01-03   | 10
1  |2014-01-04   | 5
1  |2014-01-05   | 15
1  |2014-01-06   | 20
2  |2014-02-10   | 100  
2  |2014-03-11   | 500
2  |2014-04-15   | 1500
```
The expected output is:
```
id | value_list
------------------------
1  | [10, 5, 15, 20]
2  | [100, 500, 1500]
```


```
from pyspark.sql import functions as F
from pyspark.sql import Window
w = Window.partitionBy('id').orderBy('date')

sorted_list_df = input_df.withColumn('sorted_list', F.collect_list('value').over(w))
        .groupBy('id')
        .agg(F.max('sorted_list').alias('sorted_list'))
```