How to export millions of records from Mysql to AWS S3?
------------------------------------------------------
At Twilio, we handle millions of calls happening across the world daily. Once the call is over it is logged into a MySQL DB. The customer has the ability to query the details of the Calls via an API. (Yes Twilio is API driven company)

One of the tasks I recently worked on is to build a system allowing the customers to export their historical calls data. This would allow them to export all of their historic call logs up until the most recent

At first glance, this seems trivial but if we go deeper and think about how a system would scale for some of our biggest customers who have been with us since the inception, this problem could be classified as building a system to scale. Our typical customer scale can range from making a few 100 calls a day to millions.

Suddenly this problem becomes a big data problem too when a large customer making 1 million calls a day requests the last 5 year worth of data. The total calls can be in the range of 1,000,000 * 5 * 365.

I had to think about how to optimize reading from the Mysql and efficiently write to S3 such that the files could be available to download.

###Possible Solutions
1. Write a cron job: that queries Mysql DB for a particular account and then writes the data to S3. This could work well for fetching smaller sets of records but to make the job work well to store a large number of records, I need to build a mechanism to retry at the event of failure, parallelizing the reads and writes for efficient download, add monitoring to measure the success of the job. I would have to write connectors or use libraries to connect with MySql and S3.
2. Using Spark Streaming: I decided to use Apache Spark for handling this problem since on my team (Voice Insights) we already use it heavily for more real-time data processing and building analytics. Apache Spark is a popular framework for building scalable real-time processing applications and is extensively used in the industry to solve big data and Machine learning problems. One of the key features of Spark is its ability to produce/consume data from various sources such as Kafka, Kinesis, S3, Mysql, files, etc. Apache Spark is also fault-tolerant and provides a framework for handling failure and retry elegantly. It uses a checkpointing mechanism to store the intermediate offsets of the tasks executed such that in the event of a task failure the task could be restarted from the last saved position. It works well to configure the job for horizontal scaling.

Assuming the readers have a basic understanding of Spark (Spark official documentation is a good place to start). I am going to dive into the code.

Lets, look at how to read from MySQL DB.
```scala
val jdbcDF = spark.read
  .format("jdbc")
  .option("url", "jdbc:mysql://localhost:port/db")
  .option("driver", "com.mysql.jdbc.Driver")
  .option("dbtable", "schema.tablename")
  .option("user", "username")
  .option("password", "password")
  .load()
```
The other way to connect with JDBC could be by proving the config as a Map.
```scala
val dbConfig = Map("username" -> "admin", 
                   "password" -> "pwd", 
                   "url" -> "http://localhost:3306")
val query = "select * FROM CallLog where CustomerId=1"

val jdbcDF = spark.read
                  .format("jdbc")      
                  .options(dbConfig)      
                  .option("dbtable", s"${query} AS tmp")      
                  .load()

// OR

val jdbcDF = spark.read
                  .format("jdbc")      
                  .options(dbConfig)      
                  .option("query", s"${query} AS tmp")      
                  .load()
```
There is a subtle difference between using query and dbtable. Both options will create a subquery in the FROM clause. The above query will be converted to

```roomsql
SELECT * FROM (SELECT * FROM CallLog where CustomerId=1) tmp WHERE 1=0
```
The big take away here is query does not support the partitionColmn while dbTable supports partitioning which allows for better throughput through parallelism.

Let’s say if we have to export the CallLog for one of our huge customers we would need to take advantage of a divide and conquer approach and need a better way to parallelize the effort.

###Partitioning the SQL query by column values
What it means is that Spark can execute multiple queries against the same table concurrently but each query runs by setting a different range value for a column(partitioned column). This can be done in Spark by setting a few more parameters. Let’s look at a few more parameters:

numPartitions option defines the maximum number of partitions that can be used for parallelism in a table for reading. This also determines the maximum number of concurrent JDBC connections.

partitionColumn must be a numeric, date, or a timestamp column from the table in question. The parameters describe how to partition the table when reading in parallel with multiple workers.

lowerBound and upperBound are just used to decide the partition stride, not for filtering the rows in the table. So all rows in the table will be partitioned and returned. This option applies only to reading.

fetchSize The JDBC fetch size, which determines how many rows to fetch per round trip. This can help performance on JDBC drivers which default to low fetch size (eg. Oracle with 10 rows). This option applies only to reading.

In the above example partitionColumn can be CallId. Let’s try to connect all the parameters.
```scala
val dbConfig = Map("username" -> "admin", 
                   "password" -> "pwd", 
                   "url" -> "http://localhost:3306",
                   "numPartitions" -> 10,
                   "paritionColumn" -> "CallId",
                   "lowerBound" -> 0, 
                   "upperBound" -> 10,000,000)
val query = "select * from CallLog where CustomerId=1"

val jdbcDF = spark.read
  .format("jdbc")      
  .options(dbConfig)      
  .option("dbtable", s"(${query}) AS tmp")      
  .load()
```
The above configuration will result in running the following parallel queries:
```roomsql
SELECT * from CallLog where CustomerId =1 AND CallId >=0 AND CallId <1,000,000
SELECT * from CallLog where CustomerId =1 AND CallId >= 1,000,000 AND CallId <2,000,000
SELECT * from CallLog where CustomerId =1 AND CallId>= 2,000,000 AND CallId <3,000,000
....
SELECT * from CallLog where CustomerId =1 AND CallId>= 10,000,000
```
The above parallelization of queries will help to read the results from the table faster.

Once Spark is able to read the data from Mysql, it is trivial to dump the data into S3.
```scala
jdbcDF.write
      .format("json")      
      .mode("append")
      .save("${s3path}")
```

Conclusion:

The above approach gave us the opportunity to use Spark for solving a classical batch job problem. We are doing a lot more with Apache Spark and this is a demonstration of one of the many use cases. I would love to hear what are you building with the Spark.