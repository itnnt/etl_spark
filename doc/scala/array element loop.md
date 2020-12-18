loop over the elements
in an Array
```scala
for (i <- Array(1, 2, 3)) println(i)
```
But, if what you’re really trying to do is transform those elements into a new collection,
what you want is a for/yield expression or map method
```scala
//for yield
for (i <- Array(1, 2, 3)) yield i * 2
// map

Array(1, 2, 3).map(_ * 2)
```