Spark Replace NULL Values on DataFrame
-----------------
In Spark, fill() function of DataFrameNaFunctions class is used to replace NULL values on the DataFrame column with either with zero(0), empty string, space, or any constant literal values.

While working on Spark DataFrame we often need to replace null values as certain operations on null values return NullpointerException hence, we need to graciously handle nulls as the first step before processing. Also, while writing to a file, it’s always best practice to replace null values, not doing this result nulls on the output file.

As part of the cleanup, some times you may need to Drop Rows with NULL Values in Spark DataFrame and Filter Rows by checking IS NULL/NOT NULL

DataFrame API provides DataFrameNaFunctions class with fill() function to replace null values on DataFrame. This function has several overloaded signatures that take different data types as parameters.

Example data:
```aidl

root
 |-- id: integer (nullable = true)
 |-- zipcode: integer (nullable = true)
 |-- type: string (nullable = true)
 |-- city: string (nullable = true)
 |-- state: string (nullable = true)
 |-- population: integer (nullable = true)

+---+-------+--------+-------------------+-----+----------+
|id |zipcode|type    |city               |state|population|
+---+-------+--------+-------------------+-----+----------+
|1  |704    |STANDARD|null               |PR   |30100     |
|2  |704    |null    |PASEO COSTA DEL SUR|PR   |null      |
|3  |709    |null    |BDA SAN LUIS       |PR   |3700      |
|4  |76166  |UNIQUE  |CINGULAR WIRELESS  |TX   |84000     |
|5  |76177  |STANDARD|null               |TX   |null      |
+---+-------+--------+-------------------+-----+----------+
```

###Spark Replace NULL Values with Zero (0)
Spark fill(value:Long) signatures that are available in DataFrameNaFunctions is used to replace NULL values with numeric values either zero(0) or any constant value for all integer and long datatype columns of Spark DataFrame or Dataset.
```aidl
//Replace all integer and long columns
df.na.fill(0)
    .show(false)

//Replace with specific columns
df.na.fill(0,Array("population"))
  .show(false)
```
Above both statements yields the same below output. Note that it replaces only Integer columns.
```aidl
+---+-------+--------+-------------------+-----+----------+
|id |zipcode|type    |city               |state|population|
+---+-------+--------+-------------------+-----+----------+
|1  |704    |STANDARD|null               |PR   |30100     |
|2  |704    |null    |PASEO COSTA DEL SUR|PR   |0         |
|3  |709    |null    |BDA SAN LUIS       |PR   |3700      |
|4  |76166  |UNIQUE  |CINGULAR WIRELESS  |TX   |84000     |
|5  |76177  |STANDARD|null               |TX   |0         |
+---+-------+--------+-------------------+-----+----------+
```
###Spark Replace Null Values with Empty String
Spark fill(value:String) signatures are used to replace null values with an empty string or any constant values String on DataFrame or Dataset columns.

The first syntax replaces all nulls on all String columns with a given value, from our example it replaces nulls on columns type and city with an empty string.
```aidl
df.na.fill("").show(false)
```
Yields below output. This replaces all NULL values with empty/blank string
```aidl
+---+-------+--------+-------------------+-----+----------+
|id |zipcode|type    |city               |state|population|
+---+-------+--------+-------------------+-----+----------+
|1  |704    |STANDARD|                   |PR   |30100     |
|2  |704    |        |PASEO COSTA DEL SUR|PR   |null      |
|3  |709    |        |BDA SAN LUIS       |PR   |3700      |
|4  |76166  |UNIQUE  |CINGULAR WIRELESS  |TX   |84000     |
|5  |76177  |STANDARD|                   |TX   |null      |
+---+-------+--------+-------------------+-----+----------+
```
Now, let’s use the second syntax to replace the specific value on specific columns, below example replace column typewith empty string and column city with value “unknown”.
```aidl
df.na.fill("unknown",Array("city"))
    .na.fill("",Array("type"))
    .show(false)
```
Yields below output. This replaces null values with an empty string for type column and replaces with a constant value “unknown” for city column.
```aidl
+---+-------+--------+-------------------+-----+----------+
|id |zipcode|type    |city               |state|population|
+---+-------+--------+-------------------+-----+----------+
|1  |704    |STANDARD|unknown            |PR   |30100     |
|2  |704    |        |PASEO COSTA DEL SUR|PR   |null      |
|3  |709    |        |BDA SAN LUIS       |PR   |3700      |
|4  |76166  |UNIQUE  |CINGULAR WIRELESS  |TX   |84000     |
|5  |76177  |STANDARD|unknown            |TX   |null      |
+---+-------+--------+-------------------+-----+----------+
```