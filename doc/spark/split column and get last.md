```scala

  // split column
      fileUploaded.withColumn("temp", split(col("key_1"), "\\/")).select(
        col("*") +: (0 until 3).map(i => col("temp").getItem(i).as(s"col$i")): _*
      ).show(false)

      // split column and reverse and get last
      fileUploaded.withColumn("temp", reverse(split(col("key_1"), "\\/"))).select(
        col("*") +: (0 until 1).map(i => col("temp").getItem(i).as(s"col$i")): _*
      ).show(false)

      // split column and get last
      val get_last = udf((xs: Seq[String]) => Try(xs.last).toOption)
      fileUploaded.withColumn("file_name" , get_last(split(col("key_1"), "-"))).show(false)

```