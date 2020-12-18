#Reading configurations in Scala
There are multiple ways to read the configuration files in Scala but here are two of my most preferred approaches depending on the structure of the configurations:
#Reading configurations from JSON-type .conf files
Use this approach when you have to specify multiple interrelated configurations (wherein some of them might be related to each other). All you need to do is- bucket these configurations under different headers.
Consider the following illustration:-
##application.conf
```json
com.ram.batch {
  spark {
    app-name = "my-app"
    master = "local"
    log-level = "INFO"
  }
  mysql {
    url = "jdbc:mysql://localhost/ram" // Ignore PORT if its 3306
    username = "root"
    password = "mysql"
  }
}
```
In the above file, you bucket the configurations related to spark/mysql under the respective headers to improve the readability. You can also have nested structures with any depth using approach.
So, lets see how to read these configurations:
##1. Add the following dependency to your pom.xml
```xml
<dependency>
    <groupId>com.typesafe</groupId>
    <artifactId>config</artifactId>
    <version>1.2.0</version>  // You can change this version
</dependency>
```
##2. Use the following lines of code to read the config parameters:
```scala
// Assuming that application.conf is in the root folder of your application
// ConfigFactory looks for the name application.conf by default so you need not give this name
val config = ConfigFactory.load("application.conf").getConfig("com.ram.batch") 
val sparkConfig = config.getConfig("spark")
val mysqlConfig = config.getConfig("mysql")
val appName = sparkConfig.getString(BatchConstants.GET_APP_NAME)
println(appName) // prints my-app
```
#Reading configurations from .properties file
Use this approach when you have a set of unrelated configurations and you need to bundle them in a single file(this file may be environment specific i.e. stage/dev/prod)
Consider the following illustration:-
##application.properties
```properties
hbase_table_name=mytable
hbase_txn_family=txn
hbase.zookeeper.quorum=localhost
zookeeper.znode.parent=/hbase-secure
hbase.zookeeper.property.clientPort=2181
```
In the above file, you specify the configurations simply as a key-value map i.e. as a set of properties.
Use the following snippet to read this type of properties:
```scala
// Assuming that application.properties is in the root folder of your application
val url = getClass.getResource("application.properties")
val properties: Properties = new Properties()

if (url != null) {
  val source = Source.fromURL(url)
  properties.load(source.bufferedReader())
}
else {
 logger.error("properties file cannot be loaded at path " +path)
 throw new FileNotFoundException("Properties file cannot be loaded);
}
   
val table = properties.getProperty("hbase_table_name")
val zquorum = properties.getProperty("localhost")
val port = properties.getProperty("2181")

```