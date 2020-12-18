Apache Spark standalone cluster on Windows
===
`Apache Spark is a distributed computing framework which has built-in support for batch and stream processing of big data, most of that processing happens in-memory which gives a better performance. It has built-in modules for SQL, machine learning, graph processing, etc.`
There are two different modes in which Apache Spark can be deployed, Local and Cluster mode.<br>
Local mode is mainly for testing purposes. In this mode, all the main components are created inside a single process. In cluster mode, the application runs as the sets of processes managed by the driver (**SparkContext**). The following are the main components of cluster mode.
1. Master
2. Worker
3. Resource Manager

You can visit this [link](https://spark.apache.org/docs/latest/cluster-overview.html) for more details about cluster mode.

![](https://miro.medium.com/max/820/1*EzZs4uEuO30lV51KV07_RA.png)

Currently, Apache Spark supports Standalone, Apache Mesos, YARN, and Kubernetes as resource managers. Standalone is a spark’s resource manager which is easy to set up which can be used to get things started fast.

Few key things before we start with the setup:
- Avoid having spaces in the installation folder of Hadoop or Spark.
- Always start Command Prompt with Administrator rights i.e with Run As Administrator option

######Pre-requisites
- Download JDK and add JAVA_HOME = <path_to_jdk_> as an environment variable.
- Download Hadoop and add HADOOP_HOME=<path_to_hadoop> and add %HADOOP_HOME%\bin to PATH variable
- Download Spark and add SPARK_HOME=<path_to_spark>
- Download winutils.exe and place it under %HADOOP_HOME%\bin

![](https://miro.medium.com/max/963/1*tqyR4FxSP7CaGiyLIueOEw.png)

######Set up Master Node

Go to spark installation folder, open Command Prompt as administrator and run the following command to start master node

```shell script
bin\spark-class org.apache.spark.deploy.master.Master
```

![](https://miro.medium.com/max/1375/1*rtQatSyd_2iahqB_6DdiQw.png)

######Set up Worker Node
Follow the above steps and run the following command to start a worker node
```shell script
bin\spark-class org.apache.spark.deploy.worker.Worker spark://<master_ip>:<port>
```

![](https://miro.medium.com/max/1375/1*AnjWuXWFBxAV21g3HDO4gw.png)

Your standalone cluster is up with the master and one worker node. And now you can access it from your program using master as

```shell script
spark://<master_ip>:<port>
```

These two instances can run on the same or different machines.

######Spark UI

You can access Spark UI by using the following URL

```shell script
http://<MASTER_IP>:8080
```

![](https://miro.medium.com/max/1375/1*Ky63hy5ApmtqPPmcBGluZQ.png)




