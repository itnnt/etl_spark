###### 1. The Scala List ::: method
```scala
val a = List(1,2,3)
// a: List[Int] = List(1, 2, 3)

val b = List(4,5,6)
// b: List[Int] = List(4, 5, 6)

val c = a ::: b
// c: List[Int] = List(1, 2, 3, 4, 5, 6)
```
This operation is said to have O(n) speed, where n is the number of elements in the first List.

###### 2. The Scala List concat method
```scala
val a = List(1,2,3)
// a: List[Int] = List(1, 2, 3)

val b = List(4,5,6)
// b: List[Int] = List(4, 5, 6)

val c = List.concat(a, b)
// c: List[Int] = List(1, 2, 3, 4, 5, 6)
```
###### 3. The Scala List ++ method
```scala
val a = List(1,2,3)
// a: List[Int] = List(1, 2, 3)

val b = List(4,5,6)
// b: List[Int] = List(4, 5, 6)

val c = a ++ b
// c: List[Int] = List(1, 2, 3, 4, 5, 6)
```
