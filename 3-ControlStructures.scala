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
      if (i > 4) break // break out of the for loop
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
