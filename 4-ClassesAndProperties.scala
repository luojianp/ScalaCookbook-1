// 4.1. Creating a Primary Constructor
object Section4p1{
  class Section4p1_Person(var firstName:String, var lastName:String){
    /*
     * The primary constructor of a Scala class is a combination of:
     *    The constructor parameters
     *    Methods that are called in the body of the class
     *    Statements and expressions that are executed in the body of the class
     *    (Anything defined within the body of the class other than method dec‐ larations is a part of the primary class constructor)
     *    ->  will be called when the primary constructor is called
     * auxiliary constructors:
     *    must always call a previously defined constructor in the same class,
     *    auxiliary constructors will also execute the same code.
     */

    // constructor arguments:
    //    if var:  Scala generates both accessor and mutator methods for them
    //    if val:  Scala only generate accessor methods for them  (compariable to final in Java)
    println("the constructor begins")

    // some class fields
    private val Home = System.getProperty("user.home")
    var age = 0

    //  method declaration:
    override def toString = s"$firstName $lastName is $age years old"
    def printHome() {println(s"Home = $Home")}
    def printFullName() {println(this)}   // uses toString

    // method call in constructor:
    //  When you call a method in the body of the class, that method call is also part of the constructor.
    printHome
    printFullName
    println("still in the constructor")
  }
  object Section4p1_Person{
      val p = new Section4p1_Person("Duo", "Yao")
      println(p.lastName)   // getting fields using accessor
      println(p.age)
      p.firstName = "Scott" // setting fields using mutator (firstName has mutator because it is declared as var, not val)
  }
}


// 4.2. Controlling the Visibility of Constructor Fields
/*
 *  var -> generates both getter and setter
 *  val -> generates only a getter
 *  no var or val modifier  -> doesn’t generate a getter or setter (gets conservative)
 *  private modified var and val  -> getter and setter will not be generated
 */
object Section4p2{
  // var field
  class Person(var name: String)
  val p = new Person("Duo Yao")
  println(p.name)  // access getter
  p.name = "Alex" // access setter

  // val field
  class PersonII(val name: String)
  val pII = new PersonII("Duo Yao")
  println(pII.name)  // access getter
  // pII.name = "Alex" // generates error because setter are not available for val parameter

  // fields without val or var
  class PersonIII (name:String)
  val pIII = new PersonIII("Duo Yao")
  // println(pIII.name)   // gives error because no setter generated
  // pIII.name = "Alex"

  // Adding private to val or var
  class PersonIV(private var name: String) { def getName() {println(name)} }
  val pIV = new PersonIV("Alvin Alexander")
  // p.name  // variable "name" cannot be accessed
  pIV.getName   // prints Alvin ...


  // Case Class
  //    Case class constructor parameters are val by default
  case class PersonV(name: String)
  val pV = new PersonV("Duo")
  println(pV.name)  // can still access parameter "name" even it has no modifier
}


// 4.3. Defining Auxiliary Constructors
object Section4p3{
  // Define the auxiliary constructors as methods in the class with the name this
  //  1. Auxiliary constructors are defined by creating methods named this.
  //  2. Each auxiliary constructor must begin with a call to a previously defined constructor.
  //      -> not necessarily the primary constructor  (parameter list)
  //  3. Each constructor must have a different signature.
  //  4. One constructor calls another constructor with the name this.

  class Pizza(var crustSize: Int, var crustType:String){

    def this(crustSize:Int){  // auxiliary constructor 1 (parameter must have NO modifier)
      this(crustSize, Pizza.DEFAULT_CRUST_TYPE)
    }
    def this(crustType: String) {
      this(Pizza.DEFAULT_CRUST_SIZE)
      this.crustType = Pizza.DEFAULT_CRUST_TYPE
    }
    def this(){ // auxiliary constructor 2 (parameter must have NO modifier, or no parameters)
      this(Pizza.DEFAULT_CRUST_SIZE, Pizza.DEFAULT_CRUST_TYPE)
    }
    override def toString = s"A $crustSize inch pizza with a $crustType crust"
  }
  object Pizza{   // companion object
    val DEFAULT_CRUST_SIZE = 12
    val DEFAULT_CRUST_TYPE = "THIN"
  }
  /*
   *  Companion object is simply an object that’s
   *    -> defined in the same file as a class,
   *    -> where the object and class have the same name.
   *  Purpose of companion object:
   *    -> any method declared in a companion object will appear to be a static method on the object
   *    -> etc.
   */
  // to create Pizza instance
  val p1 = new Pizza(Pizza.DEFAULT_CRUST_SIZE, Pizza.DEFAULT_CRUST_TYPE)
  val p2 = new Pizza(Pizza.DEFAULT_CRUST_SIZE)
  val p3 = new Pizza(Pizza.DEFAULT_CRUST_TYPE)
  val p4 = new Pizza

  // Parameters are not necessarily declared in the primary constructor, can also do this
  //    this will also generate getter/setter for the parameters
  class PizzaII () {
    var crustSize = 0
    var crustType = ""
    def this(crustSize: Int) {
      this()
      this.crustSize = crustSize
    }
    def this(crustType: String) {
      this()
      this.crustType = crustType
    }
    // more constructors here ...
    override def toString = s"A $crustSize inch pizza with a $crustType crust"
  }
  val p = new PizzaII(20)
  p.crustSize=10    // access getter and setter
  println(p.crustSize)

  // Generating auxiliary "constructors" for case classes
  //  -> they’re not really constructors: they’re apply methods in the companion object of the class.
  //  -> if you want to add new “constructors” to your case class, you write new apply methods.
  case class Person(var name: String, var age: Int)
  object Person{
    def apply() = new Person("<no-name>", 0)
    def apply(name:String) = new Person(name, 0)
  }
  object CaseClassTest extends App {
    // DO NOT NEED TO USE "new" keyword
    val a = Person() // corresponds to apply()
    val b = Person("Pam") // corresponds to apply(name: String)
    val c = Person("William Shatner", 82)
    println(a)
    println(b)
    println(c)
    // verify the setter methods work
    a.name = "Leonard Nimoy"
    a.age = 82
    println(a)
    /*  output:
      Person(<no name>,0)
      Person(Pam,0)
      Person(William Shatner,82)
      Person(Leonard Nimoy,82)
     */
  }
}


// 4.4. Defining a Private Primary Constructor
//    -> usage: ex. enforcing a Singleton pattern.
object Section4p4{
  // how to do it:
  //    -> insert the private keyword in between the class name and any parameters the constructor accepts:
  class Person private(name:String){/*...*/}
  // val p = new Person("Mercedes")    // ->calling this will give error

  // creating a private class constructor may not be necessary at all
  //  -> use utiliti class instead
  object FileUtils{
    def readFile(fileName: String){/**/}
    def writeToFile(fileName: String, content:String){/**/}
  }
  val contents = FileUtils.readFile("input.txt")
  FileUtils.writeToFile("output.txt", "new file contents")
  // So in this case, there’s no need for a private class constructor; just don’t define a class.
}


// 4.5. Providing Default Values for Constructor Parameters
//    -> this feature that can eliminate the need for auxiliary constructors
object Section4p5{
  class Socket (val timeout: Int = 10000)
  val s = new Socket
  println(s.timeout)    // gives 10000
  // using named parameters when calling a constructor
  val t = new Socket(timeout=5000)
  println(t.timeout)  // gives 5000

  class SocketII(val timeout: Int = 1000, val linger: Int = 2000) {
    override def toString = s"timeout: $timeout, linger: $linger"
  }
  println(new SocketII)   // timeout: 1000, linger: 2000
  println(new SocketII(3000))   // timeout: 3000, linger: 2000
  println(new SocketII(3000, 4000))   // timeout: 3000, linger: 4000

  // Using named parameters : do not need to obey the parameter sequency if using named parameters
  println(new SocketII(timeout=3000, linger=4000))
  println(new SocketII(linger=4000, timeout=3000))
  println(new SocketII(timeout=3000))
  println(new SocketII(linger=4000))
}



// 4.6. Overriding Default Accessors and Mutators
object Section4p6{
  // use _name to avoid colliding the name of the accessor method
  // use private modifier to prevent the default getter/setter from being generated
  //      (because we want to only use self-defined getter and setter)
  // cannot get rid of var modifier to preven default getter/setter being generated
  //    -> because _name will be default to val, which makes us unable to create our own setter for it
  class Person(private var _name: String) {
    def name = _name // accessor
    def name_=(aName: String) { _name = aName } // mutator
    // by converntion, the setter need to have an _ after the setter name
  }
  val p = new Person("Jonathan")
  p.name = "Jony" // setter
  println(p.name) // getter
}
