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

  def ~=(x: Double, y: Double, precision: Double) = {
    if ((x - y).abs < precision) true else false
  }
  ~=(0.3, 0.1+0.2, 0.01)  // res33: Boolean = true
  ~=(0.3, 0.1+0.2, 0)     // res34: Boolean = false

}
