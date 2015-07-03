// 1.0 Introduction
object Section1p0{
  val s = "Hello" + " world"

  "Hello, world".getClass.getName // res0: String = java.lang.String -> Scala string is just Java string
  "Hello, world".length

  "hello".foreach(println)
  for (c <- "hello") println(c)
  s.getBytes.foreach(println)
  val result = "hello world".filter(_ != 'l')

  // drop() and take() method
  "scala".drop(2).take(2).capitalize  // res0: String = Al
}

// 1.1. Testing String Equality
object Section1p1{
  val s1 = "Hello"
  val s2 = "hello"
  val s3 = "H" + "ello"
  s1 == s2  // false
  s1 == s3  // true
  s1.toUpperCase == s2.toUpperCase  // true
  s1.equalsIgnoreCase(s2) // true

  // Node: In idiomatic Scala, you never use null values
  val s4: String = null
  val s5: String = null
  s3 == s4  // false
  s4 == s3  // false
  s1.toUpperCase == s2.toUpperCase  // null pointer exception
}

// 1.2. Creating Multiline Strings
object Section1p2{
  // create multiline strings by surrounding your text with three double quotes:
  val foo = """This is
    a multiline
    String"""

  // use stripMargin to format multiline string
  val speech = """Four score and
                 |seven years ago""".stripMargin
  val speech = """Four score and
                 #seven years ago""".stripMargin('#')
  val speech = """Four score and
                 |seven years ago
                 |our fathers""".stripMargin.replaceAll("\n", " ")

   // can include single- and double-quotes without having to escape them
   val s = """This is known as a
             |"multiline" string
             |or 'heredoc' syntax.""". stripMargin.replaceAll("\n", " ")
}

// 1.3. Splitting Strings
object Section1p3{
  "hello world".split(" ")
  "hello world".split(" ").foreach(println)

  val s = "eggs, milk, butter, Coco Puffs"
  s.split(",").map(_.trim)    // trim off whitespace

  "hello world, this is Al".split("\\s+") // using regex

}

// 1.4. Substituting Variables into Strings
object Section1p4{
  /*
   *
   */
   val name = "Fred"
   val age = 33
   val weight = 200.00

   // “s string interpolator,” which lets you embed variables inside a string (s is a method)
   println(s"$name is $age years old, and weighs $weight pounds.")

   // “Any arbitrary expression can be embedded in ${}.”
   println(s"Age next year: ${age + 1}")
   println(s"You are 33 years old: ${age == 33}")

   // use curly braces when printing object fields
   case class Student(name: String, score: Int)
   val hannah = Student("Hannah", 95)
   println(s"${hannah.name} has a score of ${hannah.score}")


   // The f string interpolator (printf style formatting)
   println(f"$name is $age years old, and weighs $weight%.2f pounds.")
   println(f"$name is $age years old, and weighs $weight%.0f pounds.")
   var out = f"$name, you weigh $weight%.0f pounds."

   // The raw interpolator (“performs no escaping of literals within the string.”: ex. print '\n' as is)
  out = raw"foo\nbar"


   // The format() method
   var s = "%s is %d years old".format(name, age)
   override def toString: String = "%s %s, age %d".format(firstName, lastName, age)


}



// 1.5 Processing a String one char at a time
object Section1p5{
  /*
   *  1. map or for/yield approaches are used to transform one collection into another
   *  2. foreach method is typically used to operate on each element without returning a result
   */

  // equivalent
  val upper1 = "hello, world".map(c => c.toUpper)
  val upper2 = "hello, world".map(_.toUpper)
  val upper3 = for (c <- "hello, world") yield c.toUpper  // for...yield loop

  // equivalent
  val upper4 = "hello, world".filter(_ != 'l').map(_.toUpper)
  val upper5 = for {
    c <- "hello, world"
    if c != 'l'
  } yield c.toUpper

  // equivalent
  "hello".foreach(println)
  for (c <- "hello") println(c)

  // passing algorithm to map method: (map treat string as a sequence of char elems)
  "HELLO".map(c => (c.toByte+32).toChar)
  "HELLO".map{ c =>
    (c.toByte+32).toChar
  }
  def toLower(c: Char): Char = (c.toByte+32).toChar     // as a method
  val toLower_func = (c: Char) => (c.toByte+32).toChar  // as a function
  "HELLO".map(toLower)
  "HELLO".map(p=>toLower(p))
  "HELLO".map(toLower(_))
  for (c <- "HELLO") yield toLower(c)
}



// 1.6. Finding Patterns in Strings
object Section1p6{

  // use Regex using .r method
  val numPattern = "[0-9]+".r   // Create a Regex object by invoking the .r method on a String,
  val address = "123 Main Street Suite 101"
  val match0 = numPattern.findFirstIn(address)  // -> match0: Option[String] = Some(123)
  val matches = numPattern.findAllIn(address)   // -> matches: scala.util.matching.Regex.MatchIterator = non-empty iterator
  matches.foreach(println)
    // numPattern.findAllIn(address).toArray | toList | toSeq | toVector
    //    - Seq: trait (== Java's interface)
    //    - List: immutable
    //    - Array: mutable
    //    - Vector: a better choice than List

  // use Regex using: import scala.util.matching.Regex
  import scala.util.matching.Regex
  val numPattern1 = new Regex("[0-9]+")
  val address1 = "123 Main Street Suite 101"
  val match1 = numPattern.findFirstIn(address)
}


// 1.7. Replacing Patterns in Strings
object Section1p7{
  val address = "123 Main Street".replaceAll("[0-9]", "x")    // -> address: java.lang.String = xxx Main Street
  val result = "123".replaceFirst("[0-9]", "x")   // -> result: java.lang.String = x23

  val regex = "[0-9]".r
  val newAddress = regex.replaceAllIn("123 Main Street", "x")   // -> newAddress: String = xxx Main Street

  val regex1 = "H".r
  val result1 = regex.replaceFirstIn("Hello world", "J")    // -> result: String = Jello world
}


// 1.8. Extracting Parts of a String That Match Patterns
object Section1p8{
  val pattern = "([0-9]+) ([A-Za-z]+)".r
  val pattern(count, fruit) = "100 Bananas" // -> count: String = 100 -> fruit: String = Bananas


  // an example:
  val MoviesZipRE = "movies (\\d{5})".r
  val MoviesNearCityStateRE = "movies near ([a-z]+), ([a-z]{2})".r
  var textUserTyped = "movies 80301"
  textUserTyped match {
    case MoviesZipRE(zip) => println(s"searching $zip")
    case MoviesNearCityStateRE(city, state) => println(s"searching $city, $state")
    case _ => println("did not match a regex")
  }
}


// 1.9. Accessing a Character in a String
object Section1p9 {
  "hello".charAt(0)   // using java's charAt() -> res0: Char = h
  "hello"(0)          // using scala's Array notation -> res1: Char = h
}


// 1.10. Add Your Own Methods to the String Class
object Section1p10 {
  //  define an implicit class, and then define methods within that class to implement the behavior you want.
/*
 *  1. The compiler sees a string literal “HAL.”
 *  2. The compiler sees that you’re attempting to invoke a method named increment on the String.
 *  3. Because the compiler can’t find that method on the String class,
 *        it begins looking around for implicit conversion methods that are in scope and accepts a String argument.
 *  4. This leads the compiler to the StringImprovements class, where it finds the increment method.
 */

  // Put the implicit class in an object
  object StringUtils {
    // this implicite class add the enclosed method as member methods to the type in the constructur: in this case, String
    implicit class StringImprovements(val s: String) {
      def increment = s.map(c => (c + 1).toChar)
      def decrement = s.map(c => (c - 1).toChar)
      def hideAll: String = s.replaceAll(".", "*")
      def plusOne = s.toInt + 1
      def asBoolean = s match {
        case "0" | "zero" | "" | " " => false
        case _ => true
      }
    }
  }
  import StringUtils._
  println("HAL".increment)


  // Put the implicit class in a package object
/*  package object utils {
    implicit class StringImprovements(val s: String) {
      def increment = s.map(c => (c + 1).toChar)
    }
  }
  import utils._
  object MainDriver extends App {
    println("HAL".increment)
  }
*/

}
