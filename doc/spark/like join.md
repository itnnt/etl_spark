```scala

val df1 = Seq("stackoverflow.com/questions/.*$","^*.cnn.com$", "nn.com/*/politics").toDF("location")
val df2 = Seq("stackoverflow.com/questions/47272330").toDF("url")

df2.join(df1, expr("url rlike location")).show
+--------------------+--------------------+
|                 url|            location|
+--------------------+--------------------+
|stackoverflow.com...|stackoverflow.com...|
+--------------------+--------------------+

```