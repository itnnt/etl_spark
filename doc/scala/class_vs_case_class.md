[Scala Tutorial | Classes Vs Case Classes](https://www.scala-exercises.org/scala_tutorial/classes_vs_case_classes)
====
###### CREATION AND MANIPULATION
the class definition of BankAccount:
```scala
class BankAccount {
  private var balance = 0

  def deposit(amount: Int): Unit = {
    if (amount > 0) balance = balance + amount
  }

  def withdraw(amount: Int): Int =
    if (0 < amount && amount <= balance) {
      balance = balance - amount
      balance
    } else throw new Error("insufficient funds")
}
```
the case class definition of Note:
```scala
case class Note(name: String, duration: String, octave: Int)
```
Let’s create some instances of BankAccount and Note and manipulate them:
```scala
# creating a class instance requires the keyword new, 
# whereas this is not required for case classes.
val aliceAccount = new BankAccount
val c3 = Note("C", "Quarter", 3)
```
###### EQUALITY
comparing objects will compare their **identity**, but in the case of case class instances, the equality is redefined to compare the values of the aggregated information.
```scala
# the same definitions of bank accounts lead to different values, 
# whereas the same definitions of notes lead to equal values.
val aliceAccount = new BankAccount
val bobAccount = new BankAccount
# -----------------------------------------
# aliceAccount == bobAccount shouldBe FALSE
# -----------------------------------------

val c3 = Note("C", "Quarter", 3)
val cThree = Note("C", "Quarter", 3)
# -----------------------------------------
# c3 == cThree shouldBe TRUE
# -----------------------------------------
```
###### PATTERN MATCHING
By default, pattern matching does not work with regular classes. Pattern matching can be used to extract information from a case class instance:
```scala
c3 match {
  case Note(name, duration, octave) => s"The duration of c3 is $duration"
}
```
###### EXTENSIBILITY
A class can extend another class, whereas a case class can not extend another case class (because it would not be possible to correctly implement their equality).
###### CASE CLASSES ENCODING
We saw the main differences between classes and case classes.

It turns out that case classes are just a special case of classes, whose purpose is to aggregate several values into a single value.

The Scala language provides explicit support for this use case because it is very common in practice.

So, when we define a case class, the Scala compiler defines a class enhanced with some more methods and a companion object.

For instance, the following case class definition:
```scala
case class Note(name: String, duration: String, octave: Int)
```
Expands to the following class definition:
```scala
class Note(_name: String, _duration: String, _octave: Int) extends Serializable {

  // Constructor parameters are promoted to members
  val name = _name
  val duration = _duration
  val octave = _octave

  // Equality redefinition
  override def equals(other: Any): Boolean = other match {
    case that: Note =>
      (that canEqual this) &&
        name == that.name &&
        duration == that.duration &&
        octave == that.octave
    case _ => false
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Note]

  // Java hashCode redefinition according to equality
  override def hashCode(): Int = {
    val state = Seq(name, duration, octave)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  // toString redefinition to return the value of an instance instead of its memory addres
  override def toString = s"Note($name,$duration,$octave)"

  // Create a copy of a case class, with potentially modified field values
  def copy(name: String = name, duration: String = duration, octave: Int = octave): Note =
    new Note(name, duration, octave)

}

object Note {

  // Constructor that allows the omission of the `new` keyword
  def apply(name: String, duration: String, octave: Int): Note =
    new Note(name, duration, octave)

  // Extractor for pattern matching
  def unapply(note: Note): Option[(String, String, Int)] =
    if (note eq null) None
    else Some((note.name, note.duration, note.octave))

}
```
