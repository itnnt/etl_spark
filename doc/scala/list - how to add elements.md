How do I add elements to a Scala List?

This is actually a trick question, because you can't add elements to a Scala List; it's an immutable data structure, like a Java String.

###### Prepending elements to Scala Lists

One thing you can do when working with a Scala List is to **create a new List from an existing List**. This sort of thing is done often in functional programming, and the general approach looks like this:
```scala
val p1 = List("Kim")    // p1: List[String] = List(Kim)
val p2 = "Julia" :: p1  // p2: List[String] = List(Julia, Kim)
val p3 = "Judi" :: p2   // p3: List[String] = List(Judi, Julia, Kim)
```
###### Use a ListBuffer when you want a “List” you can modify
If you want to use a Scala sequence that has many characteristics of a List and is also mutable — i.e., you can add and remove elements in it — the correct approach is to use the Scala ListBuffer class instead, like this:
```scala
import scala.collection.mutable.ListBuffer

var fruits = new ListBuffer[String]()
fruits += "Apple"
fruits += "Banana"
fruits += "Orange"
```
Then convert it to a List if/when you need to:
```scala
val fruitsList = fruits.toList
```














