```scala
val numPattern = "[0-9]+".r
val address = "123 Main Street Suite 101"
```
##### Looking for the first match
```scala
val match1 = numPattern.findFirstIn(address)
// match1: Option[String] = Some(123)
// (Notice that this method returns an Option[String])
```
##### Looking for multiple matches
```scala
val matches = numPattern.findAllIn(address)
// matches: scala.util.matching.Regex.MatchIterator = non-empty iterator
```
As you can see from that output, findAllIn returns an iterator, which lets you loop over the results:
```scala
matches.foreach(println)
// 123
// 101
```

If findAllIn doesn’t find any results, an empty iterator is returned, so you can still write your code just like that—you don’t need to check to see if the result is null.

If you’d rather have the results as an Array, add the toArray method after the findAllIn call:
```scala
val matches = numPattern.findAllIn(address).toArray
// matches: Array[String] = Array(123, 101)
```
If there are no matches, this approach yields an empty Array. Other methods like toList, toSeq, and toVector are also available.

Using the .r method on a String is the easiest way to create a Regex object. Another approach is to import the Regex class, create a Regex instance, and then use the instance in the same way:

```scala
import scala.util.matching.Regex
val numPattern = new Regex("[0-9]+")
val match1 = numPattern.findFirstIn(address)
```
##### Handling the Option returned by findFirstIn
As mentioned in the Solution, the findFirstIn method finds the first match in the String and returns an Option[String]:

```scala
val match1 = numPattern.findFirstIn(address)
// match1: Option[String] = Some(123)
```
The Option/Some/None pattern is discussed in detail in Recipe 20.6 of the Scala Cookbook, but the simple way to think about an Option is that it’s a container that holds either zero or one values. In the case of findFirstIn, if it succeeds, it returns the string “123” as a Some(123), as shown in this example. However, if it fails to find the pattern in the string it’s searching, it will return a None, as shown here:
```scala
val address = "No address given"
val match1 = numPattern.findFirstIn(address)
// match1: Option[String] = None
```
To summarize, a method defined to return an Option[String] will either return:
+ A Some[String], or
+ A None

The normal way to work with an Option is to use one of these approaches:
+ Use the Option in a match expression
+ Use the Option in a foreach loop
+ Call getOrElse on the value

Recipe 20.6 of the Scala Cookbook describes those approaches in detail, but they’re demonstrated here for your convenience.

###### Using a match expression
A match expression provides a very readable solution to the problem, and is a preferred Scala idiom:
```scala
match1 match {
    case Some(s) => println(s"Found: $s")
    case None =>
}
```
###### Using foreach
Because an Option is a collection of zero or one elements, an experienced Scala developer will also use a foreach loop in this situation:
```scala

numPattern.findFirstIn(address).foreach { e =>
    // perform the next step in your algorithm,
    // operating on the value 'e'
}
```
###### Using getOrElse
With the getOrElse approach, you attempt to “get” the result, while also specifying a default value that should be used if the method failed:
```scala
val result = numPattern.findFirstIn(address).getOrElse("no match")
result: String = 123
```
See Recipe 20.6 for more information.

