```scala
val prefix = "ABC"
val renamedColumns = df.columns.map(c=> df(c).as(s"$prefix$c"))
val dfNew = df.select(renamedColumns: _*)
```


The third line is an example of Scala's syntactic sugar. Essentially, Scala has ways to shorten just exactly what you are typing, and you have discovered the dreaded :_*.

There are two portions to this small bit - the : and the _* serve two different purposes. The : is typically for ascription, which tells the compiler "this is the type that I need to use for this method". The _* however, is your type - in Scala this is the type varargs. Varargs is a type that has an arbitrary number of values (good resource here). It allows you to pass a method a list that you do not know the number of elements in.

In your example, you are creating a variable called renamedColumns from the columns of your original dataframe, with the new string appendage. Although you may know just how many columns are in your df, Scala does not. When you create dfNew, you are running a select statement on that and passing in your new column names, of which there could be an arbitrary number. Essentially, you do not know how many columns you may have, so you pass in your varargs to allow the number to be arbitrary, thus determined by the compiler.

