Concat array's elements of a DataFrame's column
---
Input df: 
```aidl
+--------------------------+
|DateInfos                 |
+--------------------------+
|[[3, A, 111], [4, B, 222]]|
|[[1, C, 333], [2, D, 444]]|
|[[5, E, 555]]             |
+--------------------------+
```
Expected result:
```aidl
+------------------------+
|DateInfos               |
+------------------------+
|[[3, A-111], [4, B-222]]|
|[[1, C-333], [2, D-444]]|
|[[5, E-555]]            |
+------------------------+
```
###Solution:
```aidl
import org.apache.spark.sql._
import org.apache.spark.sql.functions._

val sparkSession = ...
import sparkSession.implicits._

val input = sc.parallelize(Seq(
  Seq((3, "A", 111), (4, "B", 222)),
  Seq((1, "C", 333), (2, "D", 444)),
  Seq((5, "E", 555))
)).toDF("DateInfos")

val concatElems = udf { seq: Seq[Row] =>
  seq.map { case Row(x: Int, y: String, z: Int) => 
    (x, s"$y-$z")
  }
}

val output = input.select(concatElems($"DateInfos").as("DateInfos"))

output.show(truncate = false)
```