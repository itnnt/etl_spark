The Scala List map function "transforms each element of a collection based on a function."

```scala
val x = List(1,2,3)         // x: List[Int] = List(1, 2, 3)
val y = x.map(a => a * 2)   // y: List[Int] = List(2, 4, 6)
val z = x.map(_ * 2)        // z: List[Int] = List(2, 4, 6)
val names = List("Fred", "Joe", "Bob") // names: List[java.lang.String] = List(Fred, Joe, Bob)
val lower = names.map(_.toLowerCase)   // lower: List[java.lang.String] = List(fred, joe, bob)
val upper = names.map(_.toUpperCase)   // upper: List[java.lang.String] = List(FRED, JOE, BOB)

val li = names.map(name => <li>{name}</li>) // li: List[scala.xml.Elem] = List(<li>Fred</li>, <li>Joe</li>, <li>Bob</li>)
```