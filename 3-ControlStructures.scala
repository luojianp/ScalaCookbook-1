// 3.0 Introduction
object Section3p0{
  val nieces = List("emily", "hannah", "mercedes", "porsche")
  for (n <- nieces) yield n.capitalize

  // a Scala match expression can look like a Java switch statement
  // but  you can match any object, extract information from matched objects etc.
}


// 3.1. Looping with for and foreach
object Section3p1{
  val a = Array("apple", "banana", "orange")
  for (e <- a) {
    // imagine this requires multiple lines
    val s = e.toUpperCase
    println(s)
  }


  // Returning values from a for loop
  //  to build a new collection from the input collection: use for...yield
  val newArray = for (e <- a) yield {
    // imagine this requires multiple lines
    val s = e.toUpperCase
    s
  }

  // for loop counters
  for (i <- 0 until a.length) {
    println(s"$i is ${a(i)}")
  }

  for ((e, count) <- a.zipWithIndex) {
    println(s"$count is $e")
  }

  val b=Array(1,2,3)
  a.zip(b)  // -> res39: Array[(String, Int)] = Array((apple,1), (banana,2), (orange,3))


  // Generators and Guards
  // Ranges created with the <- symbol in for loops are referred to as generators,
  for (i <- 1 to 3) println(i)    // generate range(1,2,3) with "1 to 3"
  //    In computer programming, a guard is
  //      a boolean expression that must evaluate to true if the program execution is to continue
  for (i <- 1 to 10 if i < 4) println(i)    // Guard: if statements in for


  // Looping over a Map
  val names = Map("fname" -> "Robert", "lname" -> "Goren")
  for ((k,v) <- names) println(s"key: $k, value: $v")

  // other ways to iterate over a collection
  a.foreach(println)
  a.foreach(e => println(e.toUpperCase))
  a.foreach { e =>
    val s = e.toUpperCase
    println(s)
  }
}


// 3.2. Using for Loops with Multiple Counters
//  ex. iterating over a multi-dimentional array
object Section3p2{

  for (i <- 1 to 2; j <- 1 to 2) println(s"i = $i, j = $j")   // one way of doing this
  for {   // preferred way of doing this
    i <- 1 to 2
    j <- 1 to 2
  } println(s"i = $i, j = $j")

  // example:
  val array = Array.ofDim[Int](2,2)
  array(0)(0) = 0
  array(0)(1) = 1
  array(1)(0) = 2
  array(1)(1) = 3
  for {
    i <- 0 to 1
    j <- 0 to 1
  } println(s"($i)($j) = ${array(i)(j)}")
  /*  output
   (0)(0) = 0
   (0)(1) = 1
   (1)(0) = 2
   (1)(1) = 3
  */
}


// 3.3. Using a for Loop with Embedded if Statements (Guards)
object Section3p3{
  // These if statements are referred to as filters, filter expressions, or guards,
  for {
    i <- 1 to 10
    if i % 2 == 0
  } println(i)
  // can have more than one statements under the for loop
  for {
    i <- 1 to 10
    if i % 2 == 0
  } {
    print("just use {} to have more than one statements: ")
    println(i)
  }
}


// 3.4. Creating a for Comprehension (for/yield Combination)
object Section3p4{
  // Use a yield statement with a for loop and your algorithm to create a new collection from an existing collection.
  val names = Array("chris", "ed", "maurice")
  val capNames = for (e <- names) yield e.capitalize
  val lengths = for (e <- names) yield {
    // imagine that this required multiple lines of code
    e.length
  }

  // Writing a basic for/yield expression without a guard is just like calling the map method on a collection
  val fruits = "apple" :: "banana" :: "orange" :: Nil
  val out = for (e <- fruits) yield e.toUpperCase
  val out2 = fruits.map(_.toUpperCase)
}

// 3.5 Implementing break and continue
object Section3p5{
  // break and breakable aren’t actually keywords; they’re methods in scala.util.control.Breaks.
  import util.control.Breaks._    // need to import this to use bread or continue

  // break example : for loop inside breakable
  breakable {
    for (i <- 1 to 10) {
      println(i)
      if (i > 4) break // break out of the breakable, which IS/CONTAINS the for loop
    }
  }

  // continue example : breakable inside for loop
  val searchMe = "peter piper picked a peck of pickled peppers"
  var numPs = 0
  for (i <- 0 until searchMe.length) {
    breakable {
      if (searchMe.charAt(i) != 'p') {
        break // break out of the 'breakable', continue the outside loop
      } else {
        numPs += 1
      }
    }
  }

  // other ways to implement a break/continue-like operation
  //  1. using gurads
  //  2. using return
}

// 3.6. Using the if Construct Like a Ternary Operator
object Section3p6{
  var a,b,i = 5
  val absValue = if (a < 0) -a else a
  println(if (i == 0) "a" else "b")
  def abs(x: Int) = if (x >= 0) x else -x
  def max(a: Int, b: Int) = if (a > b) a else b
  val c = if (a > b) a else b
}

// 3.7. Using a Match Expression Like a switch Statement
object Section3p7{

  // some examples:
  // i is an integer
  var i = 1
  i match {   // can use match to execute a function
    case 1 => println("January")
    case 2 => println("February")
    // ...
    case 12 => println("December")
    // catch the default with a variable so you can print it
    case default => println("Unexpected case: " + default.toString)
  }

  val month = i match { // can use match to return an object
    case 1 => "January"
    case 2 => "February"
    case 3 => "March"
    // ...
    case 12 => "December"
    case _ => "Invalid month" // the default, catch-all
  }

  // handling the default case:
  //  1. catch it with the _ wildcard:
  //    case _ => println("Got a default match")
  //  2. if you are interested in what fell down to the default match: assign a variable name to it
  //    case default => println(default)    // dont have to used "default" as variable name: case oops => println(oops)
  //  3. You can generate a MatchError if you don’t handle the default case


  //  don’t really need a switch statement if you have a data structure that maps month numbers to month names
  val monthNumberToName = Map(
    1 -> "January",
    2  -> "February",
    3  -> "March",
    4 -> "April",
    // ...
    12 -> "December")
  val monthName = monthNumberToName(4)
  println(monthName) // prints "April"
}


// 3.8. Matching Multiple Conditions with One Case Statement
object Section3p8{
  // examples:
  val i = 5
  i match {
    case 1 | 3 | 5 | 7 | 9 => println("odd")
    case 2 | 4 | 6 | 8 | 10 => println("even")
  }

  val cmd = "stop"
  cmd match {
    case "start" | "go" => println("starting")
    case "stop" | "quit" | "exit" => println("stopping")
    case _ => println("doing nothing")
  }
}


// 3.9. Assigning the Result of a Match Expression to a Variable
//    -> Scala can return value from match{} statement (can also return value from if statement)
object Section3p9{
  // To assign a variable to the result of a match expression, insert the variable assignment before the expression
  //    -> commonly used to create short methods or functions
  var someNumber = 0
  val evenOrOdd = someNumber match {
    case 1 | 3 | 5 | 7 | 9 => println("odd")
    case 2 | 4 | 6 | 8 | 10 => println("even")
  }

  def isTrue(a: Any) = a match {
    case 0 | "" => false
    case _ => true
  }
}

// 3.10. Accessing the Value of the Default Case in a Match Expression
object Section3p10{
  // Instead of using the _ wildcard character, assign a variable name to the default case:
  //    1. any valid variable name will suffice (dont have to be "default")
  //    2. important to provide a default match. Failure to do so can cause a MatchError
  var i=0
  i match {
    case 0 => println("1")
    case 1 => println("2")
    case default => println("You gave me: " + default)
  }
}


// 3.11. Using Pattern Matching in Match Expressions
//  Problem:
//    You need to match one or more patterns in a match expression,
//    and the pattern may be a constant pattern, variable pattern, constructor pattern, sequence pattern, tuple pattern, or type pattern.
object Section3p11{
  // Constant patterns
  case 0 => "zero"
  case true => "true"
  // Variable patterns
  case _ => s"Hmm, you gave me something ..."
  case foo => s"Hmm, you gave me a $foo"
  // Constructor patterns
  case Person(first, "Alexander") => s"found an Alexander, first name = $first"
  case Dog("Suka") => "found a dog named Suka"
  // Sequence patterns
  case List(0, _, _) => "a three-element list with 0 as the first element"
  case List(1, _*) => "a list beginning with 1, having any number of elements"
  case Vector(1, _*) => "a vector beginning with 1 and having any number ..."
  // Tuple patterns
  case (a, b, c) => s"3-elem tuple, with values $a, $b, and $c"
  case (a, b, c, _) => s"4-elem tuple: got $a, $b, and $c"
  // Type patterns
  case str: String => s"you gave me this string: $str"

  // adding variables to pattern -> variableName @ pattern
  case list @ List(1, _*) => s"$list"
  case x @ List(1, _*) => s"$x" // works; return the list
  case x @ Some(_) => s"$x" // works, returns "Some(foo)"
  case p @ Person(first, "Doe") => s"$p" // works, returns "Person(John,Doe)"

  // using Some and non
  case Some(i) => println(i)
  case None => println("That wasn't an Int.")
}


// 3.12. Using Case Classes in Match Expressions
//  Problem:
//    You want to match different case classes (or case objects) in a match expression,
//    such as when receiving messages in an actor.
object Section3p12{
  trait Animal
  case class Dog(name: String) extends Animal
  case class Cat(name: String) extends Animal
  case object Woodpecker extends Animal
  object CaseClassTest extends App {
    def determineType(x: Animal): String = x match {
      case Dog(moniker) => "Got a Dog, name = " + moniker
      case _:Cat => "Got a Cat (ignoring the name)"
      case Woodpecker => "That was a Woodpecker"
      case _ => "That was something else"
    }
    println(determineType(new Dog("Rocky")))     // Got a Dog, name = Rocky
    println(determineType(new Cat("Rusty the Cat")))  // Got a Cat (ignoring the name)
    println(determineType(Woodpecker))  //  That was a Woodpecker
  }
}


// 3.13. Adding if Expressions (Guards) to Case Statements Problem
//  Problem:
//    You want to add qualifying logic to a case statement in a match expression,
//    such as allowing a range of numbers, or matching a pattern, but only if that pattern matches some additional criteria.
object Section3p13{
  i match {
    case a if 0 to 9 contains a => println("0-9 range: " + a)
    case b if 10 to 19 contains b => println("10-19 range: " + b)
    case c if 20 to 29 contains c => println("20-29 range: " + c)
    case _ => println("Hmmm...")
  }
  num match {
    case x if x == 1 => println("one, a lonely number")
    case x if (x == 2 || x == 3) => println(x)
    case _ => println("some other value")
  }
  def speak(p: Person) = p match {
    case Person(name) if name == "Fred" => println("Yubba dubba doo")
    case Person(name) if name == "Bam Bam" => println("Bam bam!")
    case _ => println("Watch the Flintstones!")
  }
  def speakII(p: Person) = p match {  // an equivalent of the above speak() match method
    case Person(name) =>
      if (name == "Fred") println("Yubba dubba doo")
      else if (name == "Bam Bam") println("Bam bam!")
    case _ => println("Watch the Flintstones!")
  }
}


// 3.14. Using a Match Expression Instead of isInstanceOf
// Problem:
//   You want to write a block of code to match one type, or multiple different types.
object Section3p14{
  // to determine if the object is an instance of a Person:
  def isPerson(x:Any):Boolean = x match{
    case p:Person => true
    case _ => false
  }
  // given an object that extends a known supertype, and determine its exact subtype
  trait SentientBeing
  trait Animal extends SentientBeing
  case class Dog(name: String) extends Animal
  case class Person(name:String, age:Int)extends SentientBeing
  def printInfo(x:SentientBeing) = x match{
    case Person(name, age) => println(s"this is a person: $name, $age")
    case Dog(name) => println("this is a dog: $name")
    case _ => println("this is nothing")
  }
}


// 3.15. Working with a List in a Match Expression (Recursion)
//  Problem:
//    You know that a List data structure is a little different than other collection data struc‐ tures.
//    It’s built from cons cells and ends in a Nil element.
//    You want to use this to your advantage when working with a match expression, such as when writing a recursive function.
object Section3p15{
  // Ways to create a list:
  val x = List(1, 2, 3)
  val y = 1 :: 2 :: 3 :: Nil

  def listToString(list:List[String]):String = list match{
    case list::rest => s+" "+listToString(rest)
    // list points to the current element,
    //    list:rest match the sublist from the current element to end of list
    //    currPtr::rest matches the rest of the list from the currPtr
    case Nil => ""
  }

  // more examples: calculate the sum of the list of integers
  def sum(list: List[Int]): Int = list match{
    case Nil => 1
    case n::rest => n + sum(rest) // the n here matches anything other than Nil (default case)
  }

  //When using this recipe, be sure to handle the Nil case, or MatchError
}


// 3.16. Matching One or More Exceptions with try/catch
//  Problem:
//    You want to catch one or more exceptions in a try/catch block.
object Section3p16{
  // catch certain types of exceptions
  try {
    openAndReadAFile(filename)
  } catch {
    case e: FileNotFoundException => e.printStackTrace
    case e: IOException => e.printStackTrace
  }

  // to catch all exceptions
  try{
    openAndReadAFile(filename)
  }catch{
    case t:Throwable => t.printlStackTrace()
  }
}


// 3.17. Declaring a Variable Before Using It in a try/catch/finally Block
// Problem:
//    You want to use an object in a try block, and need to access it in the finally portion of the block,
//    such as when you need to call a close method on an object.
object Section3p17{
  import java.io._
  object CopyBytes extends App{
    // declaring Option fields that aren’t initially populated:
    var in = None:Option[FileInputStream]
    var out = None:Option[FileOutputStream]
    try{
      in = Some(new FileInputStream("/some/path"))
      out = Some(new FileOutputStream("/some/path"))
      var c=0
      while({c=in.get.read; c!=-1}){
        out.get.write(c)
      }
    }catch{
      case e: IOException => e.printlStackTrace
    }finally{
      println("Entered Filally: ")
      if(in.isDefined)  in.get.close
      if(out.isDefined) out.get.close
    }
  }
}


// 3.18. Creating Your Own Control Structures
//  Problem:
//    You want to define your own control structures to improve the Scala language,
//    simplify your own code, or create a DSL for others to use.
object Section3p18{

  // 1st attempt
  def whilst(testCondition: => Boolean)(codeBlock: => Unit) {
    while (testCondition) {
      codeBlock
    }
  }

  import scala.annotation.tailrec
  object Whilst {
      // 2nd attempt
      @tailrec
      def whilst(testCondition: => Boolean)(codeBlock: => Unit) {
        if (testCondition) {
          codeBlock
        whilst(testCondition)(codeBlock)
      }
    }
  }


  // two 'if' condition tests
  def doubleif(test1: => Boolean)(test2: => Boolean)(codeBlock: => Unit) {
    if (test1 && test2) {
      codeBlock
    }
  }
  // to call doubleif()() -> () and {} does not matter in this case!!!
  doubleif(age > 18)(numAccidents == 0) { println("Discount!") }
  doubleif(20 > 18)(0 == 0) ( println("Discount!") )
  doubleif{20 > 18}{0 == 0} { println("Discount!") }
}
