[How to get the element index when mapping an array in Scala?](https://stackoverflow.com/questions/9137644/how-to-get-the-element-index-when-mapping-an-array-in-scala)
--------------
Let's consider a simple mapping example:
```scala
val a = Array("One", "Two", "Three")
val b = a.map(s => myFn(s))
```
What I need is to use not myFn(s: String): String here, but myFn(s: String, n: Int): String, where n would be the index of s in a. In this particular case myFn would expect the second argument to be 0 for s == "One", 1 for s == "Two" and 2 for s == "Three". How can I achieve this?

Depends whether you want convenience or speed.

Slow:
```scala
a.zipWithIndex.map{ case (s,i) => myFn(s,i) }
```
Faster:
```scala
for (i <- a.indices) yield myFn(a(i),i)

{ var i = -1; a.map{ s => i += 1; myFn(s,i) } }
```
Possibly fastest:
```scala
Array.tabulate(a.length){ i => myFn(a(i),i) }
```
If not, this surely is:
```scala
val b = new Array[Whatever](a.length)
var i = 0
while (i < a.length) {
  b(i) = myFn(a(i),i)
  i += 1
}
```