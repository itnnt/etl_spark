name := "ETL_SPARK"
//name := "etl-20200906"
version := "0.1"
idePackagePrefix := Some("vincere.io")

enablePlugins(PackPlugin)
packMain := Map("randstad" -> "client.api.Randstad")
packResourceDir += (baseDirectory.value / "pack" -> "bin")



//scalaVersion := "2.13.4"
//scalaVersion := "2.11.12"
//libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.7"
//libraryDependencies += "org.apache.spark" %% "spark-mllib" % "2.4.7"
scalaVersion := "2.12.10"
libraryDependencies += "org.apache.spark" %% "spark-core" % "3.0.1"
libraryDependencies += "org.apache.spark" %% "spark-mllib" % "3.0.1"
// https://mvnrepository.com/artifact/org.apache.spark/spark-hive
libraryDependencies += "org.apache.spark" %% "spark-hive" % "3.0.1" % "provided"

// https://mvnrepository.com/artifact/com.crealytics/spark-excel
libraryDependencies += "com.crealytics" %% "spark-excel" % "0.13.5"
// read config file
// https://mvnrepository.com/artifact/com.typesafe/config
libraryDependencies += "com.typesafe" % "config" % "1.4.0"

// csv libraries -------------------------------------------------------------------------------------------------------
// https://mvnrepository.com/artifact/org.apache.commons/commons-csv
libraryDependencies += "org.apache.commons" % "commons-csv" % "1.8"

// http clients --------------------------------------------------------------------------------------------------------
// https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
libraryDependencies += "com.squareup.okhttp3" % "okhttp" % "4.9.0"

// database connection drivers -----------------------------------------------------------------------------------------
// https://mvnrepository.com/artifact/net.sourceforge.jtds/jtds
libraryDependencies += "net.sourceforge.jtds" % "jtds" % "1.3.1"
// https://mvnrepository.com/artifact/com.microsoft.sqlserver/mssql-jdbc
libraryDependencies += "com.microsoft.sqlserver" % "mssql-jdbc" % "8.4.1.jre8"
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.16"
// https://mvnrepository.com/artifact/org.mongodb.spark/mongo-spark-connector
libraryDependencies += "org.mongodb.spark" %% "mongo-spark-connector" % "2.4.1"
// https://mvnrepository.com/artifact/org.mongodb/mongo-java-driver
libraryDependencies += "org.mongodb" % "mongo-java-driver" % "3.11.2"
// https://mvnrepository.com/artifact/org.mongodb/bson
libraryDependencies += "org.mongodb" % "bson" % "3.11.2"

// unit test -----------------------------------------------------------------------------------------------------------
// https://mvnrepository.com/artifact/org.scalatest/scalatest
//libraryDependencies += "org.scalatest" %% "scalatest" % "3.3.0-SNAP2" % Test
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test"
// https://mvnrepository.com/artifact/junit/junit
libraryDependencies += "junit" % "junit" % "4.13" % Test


// json tools ----------------------------------------------------------------------------------------------------------
// https://mvnrepository.com/artifact/com.google.code.gson/gson
libraryDependencies += "com.google.code.gson" % "gson" % "2.8.6"

// JSoup Java HTML Parser ----------------------------------------------------------------------------------------------
// https://mvnrepository.com/artifact/org.jsoup/jsoup
libraryDependencies += "org.jsoup" % "jsoup" % "1.13.1"

// s3 tools ------------------------------------------------------------------------------------------------------------
// https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk
libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.11.871"

// flexible XML framework for Java -------------------------------------------------------------------------------------
// https://mvnrepository.com/artifact/org.dom4j/dom4j
libraryDependencies += "org.dom4j" % "dom4j" % "2.1.3"

// email tool ----------------------------------------------------------------------------------------------------------
// https://mvnrepository.com/artifact/com.sun.mail/javax.mail
libraryDependencies += "com.sun.mail" % "javax.mail" % "1.6.2"
// https://mvnrepository.com/artifact/com.auxilii.msgparser/msgparser
libraryDependencies += "com.auxilii.msgparser" % "msgparser" % "1.1.15"
//-------------------||
// google api        ||
//-------------------||
libraryDependencies += "com.google.api-client" % "google-api-client" % "1.30.11"
libraryDependencies += "com.google.oauth-client" % "google-oauth-client-jetty" % "1.31.2"
libraryDependencies += "com.google.apis" % "google-api-services-drive" % "v3-rev197-1.25.0"
libraryDependencies += "com.google.api-client" % "google-api-client-jackson2" % "1.30.11"
libraryDependencies += "com.google.oauth-client" % "google-oauth-client" % "1.31.2"
libraryDependencies += "com.google.http-client" % "google-http-client-jackson" % "1.29.2"
libraryDependencies += "com.google.http-client" % "google-http-client-jackson2" % "1.38.0"
libraryDependencies += "com.google.auth" % "google-auth-library-oauth2-http" % "0.22.0"
//-------------------||
// uri               ||
//-------------------||
libraryDependencies += "io.lemonlabs" %% "scala-uri" % "3.0.0"
libraryDependencies += "org.apache.axis" % "axis" % "1.4"
//-------------------||
// talend components ||
//-------------------||
resolvers += "Maven Central Server" at "https://artifacts-oss.talend.com/nexus/content/repositories/TalendOpenSourceRelease"
libraryDependencies += "org.talend.components" % "components-api" % "0.28.14"
libraryDependencies += "org.talend.components" % "components-salesforce-definition" % "0.28.14"
libraryDependencies += "org.talend.components" % "salesforce" % "1.9.0"
libraryDependencies += "org.talend.components" % "components-salesforce-runtime" % "0.28.14"
libraryDependencies += "org.talend.libraries" % "talend-codegen-utils" % "0.28.0"
//-------------------||
// SPARK SALESFORCE  ||
//-------------------||
//https://github.com/springml/spark-salesforce
libraryDependencies += "com.springml" % "spark-salesforce_2.11" % "1.1.3"

// https://mvnrepository.com/artifact/com.force.api/force-wsc
libraryDependencies += "com.force.api" % "force-wsc" % "51.0.0"







