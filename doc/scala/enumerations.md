ENUMERATIONS
----------------
Enumerations are a useful tool for creating small groups of constants, things like the days of the week, months in a year, suits in a deck of cards, etc., situations where you have a group of related, constant values.

###### Create an enumeration for the days of a week:
```scala
sealed trait DayOfWeek
case object Sunday extends DayOfWeek
case object Monday extends DayOfWeek
case object Tuesday extends DayOfWeek
case object Wednesday extends DayOfWeek
case object Thursday extends DayOfWeek
case object Friday extends DayOfWeek
case object Saturday extends DayOfWeek
```
As shown, just declare a base trait and then extend that trait with as many case objects as needed.

###### Similarly, this is how you create an enumeration for the suits in a deck of cards:
```scala
sealed trait Suit
case object Clubs extends Suit
case object Spades extends Suit
case object Diamonds extends Suit
case object Hearts extends Suit
```
###### Pizza-related enumerations
Given that (very brief) introduction to enumerations, we can now create pizza-related enumerations like this:
```scala
sealed trait Topping
case object Cheese extends Topping
case object Pepperoni extends Topping
case object Sausage extends Topping
case object Mushrooms extends Topping
case object Onions extends Topping

sealed trait CrustSize
case object SmallCrustSize extends CrustSize
case object MediumCrustSize extends CrustSize
case object LargeCrustSize extends CrustSize

sealed trait CrustType
case object RegularCrustType extends CrustType
case object ThinCrustType extends CrustType
case object ThickCrustType extends CrustType
```
Those enumerations provide a nice way to work with pizza toppings, crust sizes, and crust types.
###### A sample Pizza class
Given those enumerations, we can define a Pizza class like this:
```scala
class Pizza (
    var crustSize: CrustSize = MediumCrustSize, 
    var crustType: CrustType = RegularCrustType
) {

    // ArrayBuffer is a mutable sequence (list)
    import scala.collection.mutable.ArrayBuffer
    val toppings = scala.collection.mutable.ArrayBuffer[Topping]()

    def addTopping(t: Topping): Unit = toppings += t
    def removeTopping(t: Topping): Unit = toppings -= t
    def removeAllToppings(): Unit = toppings.clear()
    override def toString(): String = {
            s"""
            |Crust Size: $crustSize
            |Crust Type: $crustType
            |Toppings:   $toppings
            """.stripMargin
        }
  }
// a little "driver" app
object PizzaTest extends App {
   val p = new Pizza
   p.addTopping(Cheese)
   p.addTopping(Pepperoni)
   println(p)
}
```






