Compare two String instances with the == operator
```scala
val s1 = "hello"
val s3 = "hello"
val s3 = "h" + "ello"

s1 == s2 //true
s1 == s3 //true
s1.equalsIgnoreCase(s2) //true
```
A pleasant benefit of the == method is that it doesn’t throw a NullPointerException
on a basic test if a String is null
```scala
//  don’t have to check for null values when comparing strings
val s4: String = null

s3 == s4 //false

s4 == s3 //false
```