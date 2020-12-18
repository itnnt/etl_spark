Counting Stations for each NetworkID.
```scala
val df = Seq(
    ("N1", "M1", "1")
   ,("N1", "M1", "2")
   ,("N1", "M2", "3")
   ,("N2", "M1", "4")
   ,("N2", "M2", "2")).toDF("NetworkID", "Station", "value")
import org.apache.spark.sql.functions.window
val w = Window.partitionBy("NetworkID", "Station")
df.withColumn("count", count("Station").over(w)).show()
```
```scala
+---------+-------+-----+-----+
|NetworkID|Station|value|count|
+---------+-------+-----+-----+
|       N2|     M2|    2|    1|
|       N1|     M2|    3|    1|
|       N2|     M1|    4|    1|
|       N1|     M1|    1|    2|
|       N1|     M1|    2|    2|
+---------+-------+-----+-----+
```

