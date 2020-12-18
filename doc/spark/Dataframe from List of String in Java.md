```java
public class SparkSample{
public static void main(String[] args) {
    SparkConf conf = new SparkConf().setAppName("SparkSample").setMaster("local[*]");
    JavaSparkContext jsc = new JavaSparkContext(conf);
    SQLContext sqc = new SQLContext(jsc);
    // sample data
    List<String> data = new ArrayList<String>();
    data.add("dev, engg, 10000");
    data.add("karthik, engg, 20000");
    // DataFrame
    DataFrame df = sqc.createDataset(data, Encoders.STRING()).toDF();
    df.printSchema();
    df.show();
    // Convert
    DataFrame df1 = df.selectExpr("split(value, ',')[0] as name", "split(value, ',')[1] as degree","split(value, ',')[2] as salary");
    df1.printSchema();
    df1.show(); 
   }
}
```
Output
```
root
 |-- value: string (nullable = true)

+--------------------+
|               value|
+--------------------+
|    dev, engg, 10000|
|karthik, engg, 20000|
+--------------------+

root
 |-- name: string (nullable = true)
 |-- degree: string (nullable = true)
 |-- salary: string (nullable = true)

+-------+------+------+
|   name|degree|salary|
+-------+------+------+
|    dev|  engg| 10000|
|karthik|  engg| 20000|
+-------+------+------+
```

```scala

val data = List("dev, engg, 10000", "karthik, engg, 20000")
val intialRdd = sparkContext.parallelize(data)
val splittedRDD = intialRdd.map(current => {
  val array = current.split(",")
  (array(0), array(1), array(2))
})
import sqlContext.implicits._
val dataframe = splittedRDD.toDF("name", "degree", "salary")
dataframe.show()
```
Output
```
+-------+------+------+
|   name|degree|salary|
+-------+------+------+
|    dev|  engg| 10000|
|karthik|  engg| 20000|
+-------+------+------+
```