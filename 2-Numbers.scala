// 2.0 Introduction
object Section2p0 {
  // Finding the data range of certain types
  Short.MinValue
  Float.MinValue
}

// 2.1 Parsing a Number from a String
object Section2p1{
  // parse string to numeric types
  "100".toInt
  "100".toDouble  // have "to**" other types too

  // parse string to numeric types with different base: parseInt(str, radius)
  Integer.parseInt("100", 2)    // -> Int = 4
  Integer.parseInt("14", 8)   // -> res21: Int = 12

  // conversion between numeric types
  19.45.toInt   // Int = 19
  19.toDouble   // Double = 19.0

  // check if a var/val is a valid numeric type:
  val a = 1000L
  a.isValidByte
  a.isValidShort
}

// 2.3. Overriding the Default Numeric Type
object Section2p3{
  // enforce type version 1
  val a = 1d
  val b = 1f
  val c = 1000L

  // enforce type version 2
  val d:Byte = 0
  val e:Double = 0

  // use hex by preceeding 0x
  val f = 0x20    // -> f: Int = 32
  val g = 0x20L   // -> g: Long = 32
}


// 2.4. Replacements for ++ and −−  (scala does not have ++ nor --, [python does not have them either, why?])
object Section2p4{
  // scala does not have ++ nor --, use += or -= instead
  // the symbols +=, −=, *=, and /= aren’t operators, they’re methods.
}


// 2.5. Comparing Floating-Point Numbers
object Section2p5{
  // 0.3 != 0.1+0.2 : 0.1+0.2 -> Double = 0.30000000000000004
  // how to solve this problem:

  implicit class fpImprovement(fp: Double){
    def ~=(fp1: Double): Boolean={
      if((fp-fp1).abs<0.0001) true else false
    }
  }
  0.3 ~= (0.1+0.2)       // -> res37: Boolean = false
  0.3 ~= 0.1+0.2         // -> res4: Boolean = true   we can even get rid of the () which makes it look more real!

  def ~=(x: Double, y: Double, precision: Double) = {
    if ((x - y).abs < precision) true else false
  }
  ~=(0.3, 0.1+0.2, 0.01)  // res33: Boolean = true
  ~=(0.3, 0.1+0.2, 0)     // res34: Boolean = false
}


// 2.6. Handling Very Large Numbers
object Section2p6{
  // the Big* numbers support all operators for regluar number types: +|-|*|/|+= ....
  var a = BigInt(1234567890)      // -> a: scala.math.BigInt = 1234567890
  var b = BigDecimal(123456.789)  // -> b: scala.math.BigDecimal = 123456.789
}



// 2.7. Generating Random Numbers
object Section2p7{
  val r = scala.util.Random
  r.nextInt         // generate random int without bound
  r.nextInt(100)    // limit the random numbers to a maximum value:
  r.nextFloat       // returns a value between 0.0 and 1.0
  r.nextDouble      // returns a value between 0.0 and 1.0
  r.nextPrintableChar // can even generate random chars

  // You can set the seed value using an Int or Long when creating the Random object:
  val r2 = new scala.util.Random(100)
  r2.setSeed(1000L)   // can also set the seed value after a Random object has been created:
}


// 2.8. Creating a Range, List, or Array of Numbers
object Section2p8{
  // creating Range with "to" -> this includes the last element
  val r1 = 1 to 10        // create range with default step of 1  -> Range(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
  val r2 = 1 to 10 by 3   // create range with custom step    -> Range(1, 4, 7, 10)
  // use range in for loop
  for (i <- 1 to 5) println(i)

  // creating Range with until -> this does not include the last element (similar to range() in Python)
  val r3 = 1 until 10     // Range(1, 2, 3, 4, 5, 6, 7, 8, 9)
  // use with for loop
  for (i <- 1 until 5) println(i)


  // can convert range to other sequence types:
  val x = 1 to 10 toArray       // -> Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
  val x1 = (1 to 10).toList     // use () is preferred
  val y = 1 to 10 toList        // -> List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
  val y1 = (1 to 10).toArray    // use () is preferred
}


// 2.9. Formatting Numbers and Currency
object Section2p9{
  val pi = scala.math.Pi
  f"$pi%1.5f"     // -> res0: String = 3.14159
  f"$pi%1.2f"     // -> res1: String = 3.14
  f"$pi%06.2f"    // -> res2: String = 003.14


  // to add commas for Integer
  val formatter = java.text.NumberFormat.getIntegerInstance
  formatter.format(1000000)   // -> res1: String = 1,000,000

  // ........
}
