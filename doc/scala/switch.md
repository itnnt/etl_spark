How to use a Scala match expression like a switch statement
-----------------
```scala
val month = i match {
    case 1  => "January"
    case 2  => "February"
    case 3  => "March"
    case 4  => "April"
    case 5  => "May"
    case 6  => "June"
    case 7  => "July"
    case 8  => "August"
    case 9  => "September"
    case 10 => "October"
    case 11 => "November"
    case 12 => "December"
    case _  => "Invalid month"  // the default, catch-all
}
```
##### The @switch annotation

When writing simple match expressions like this, it’s recommended to use the @switch annotation. This annotation provides a warning at compile time if the switch can’t be compiled to a tableswitch or lookupswitch.

Compiling your match expression to a tableswitch or lookupswitch is better for performance, because it results in a branch table rather than a decision tree. When a value is given to the expression, it can jump directly to the result rather than working through the decision tree.

Here’s the official description from the @switch annotation documentation:

>“An annotation to be applied to a match expression. If present, the compiler will verify that the match has been compiled to a tableswitch or lookupswitch, and issue an error if it instead compiles into a series of conditional expressions.”

The effect of the @switch annotation is demonstrated with a simple example. First, place the following code in a file named SwitchDemo.scala:
```scala
// Version 1 - compiles to a tableswitch
import scala.annotation.switch

class SwitchDemo {
    val i = 1
    val x = (i: @switch) match {
        case 1  => "One"
        case 2  => "Two"
        case _  => "Other"
    }
}
```
Then compile the code as usual:
```
$ scalac SwitchDemo.scala
```
Compiling this class produces no warnings and creates the output file SwitchDemo.class. Next, disassemble that file with this javap command:
```
$ javap -c SwitchDemo
```
The output from this command shows a tableswitch, like this:
```
16:  tableswitch{ //1 to 2
            1: 50;
            2: 45;
            default: 40 }
```
This shows that Scala was able to optimize your match expression to a tableswitch. (This is a good thing.)

Next, make a minor change to the code, replacing the integer literal 2 with a value:
```scala
import scala.annotation.switch

// Version 2 - leads to a compiler warning
class SwitchDemo {
  val i = 1
  val Two = 2                     // added
  val x = (i: @switch) match {
    case 1  => "One"
    case Two => "Two"             // replaced the '2'
    case _  => "Other"
  }
}
```
Again, compile the code with scalac, but right away you’ll see a warning message:
```
$ scalac SwitchDemo.scala
SwitchDemo.scala:7: warning: could not emit switch for @switch annotated match
  val x = (i: @switch) match {
               ^
one warning found
```
This warning message is saying that neither a tableswitch nor lookupswitch could be generated for the match expression. You can confirm this by running the javap command on the SwitchDemo.class file that was generated. When you look at that output, you’ll see that the tableswitch shown in the previous example is now gone.

In his book, Scala In Depth (Manning), Joshua Suereth states that the following conditions must be true for Scala to apply the tableswitch optimization:

+ The matched value must be a known integer.
+ The matched expression must be “simple.” It can’t contain any type checks, if statements, or extractors.
+ The expression must also have its value available at compile time.
+ There should be more than two case statements.

For more information on how JVM switches work, see the Oracle document, Compiling Switches.

##### Discussion

As demonstrated in other recipes, you aren’t limited to matching only integers; the match expression is incredibly flexible:
```scala
def getClassAsString(x: Any): String = x match {
    case s: String => s + " is a String"
    case i: Int => "Int"
    case f: Float => "Float"
    case l: List[_] => "List"
    case p: Person => "Person"
    case _ => "Unknown"
}
```
##### Handling the default case

The examples in the Solution showed the two ways you can handle the default, “catch all” case. First, if you’re not concerned about the value of the default match, you can catch it with the _ wildcard:
```scala
case _ => println("Got a default match")
```
Conversely, if you are interested in what fell down to the default match, assign a variable name to it. You can then use that variable on the right side of the expression:
```scala
case default => println(default)
```
Using the name default often makes the most sense and leads to readable code, but you can use any legal name for the variable:
```scala
case oops => println(oops)
```
You can generate a MatchError if you don’t handle the default case. Given this match expression:
```scala
i match {
    case 0 => println("0 received")
    case 1 => println("1 is good, too")
}
```
if i is a value other than 0 or 1, the expression throws a MatchError:
```
scala.MatchError: 42 (of class java.lang.Integer)
  at .<init>(<console>:9)
  at .<clinit>(<console>)
    much more error output here ...
```
So unless you’re intentionally writing a partial function, you’ll want to handle the default case. (See Recipe 9.8, “Creating Partial Functions”, for more information on partial functions.)

Do you really need a switch statement?
Of course you don’t really need a switch statement if you have a data structure that maps month numbers to month names. In that case, just use a Map:
```scala
val monthNumberToName = Map(
    1  -> "January",
    2  -> "February",
    3  -> "March",
    4  -> "April",
    5  -> "May",
    6  -> "June",
    7  -> "July",
    8  -> "August",
    9  -> "September",
    10 -> "October",
    11 -> "November",
    12 -> "December"
)

val monthName = monthNumberToName(4)
println(monthName)  // prints "April"
```