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
  // Solution:
  //    self defined accessor:
  //        - use _name as parameter name in primary constructor to avoid colliding the accessor method name: "name"
  //        - use private modifier to prevent the default getter/setter from being generated
  //          -> (because we want to only use self-defined getter and setter)
  //    self defined mutator:
  //        - use name_ as mutator name, conforming the convention
  //    Others:
  //        - cannot get rid of var modifier to preven default getter/setter being generated
  //          -> because _name will be default to val, which makes us unable to create our own setter for it
  class Person(private var _name: String) {
    def name = _name // accessor
    // Note: calling the getter method name returns the variable "_name"
    def name_=(aName: String) { _name = aName } // mutator
    // Note: calling the setter method name_= sets the variable "_name"
    // ex. for the example above:
    //    the setter method name: name_=
    //    the setter method parameter: (aName:String)
    //    the setter definition:  {_name = aName}
  }
  val p = new Person("Jonathan")
  p.name = "Jony" // setter
  println(p.name) // getter
}


// 4.7. Preventing Getter and Setter Methods from Being Generated
object Section4p7{
  // Solution:
  //    using the private access modifier
  class Stock{
    var delayedPrice:Double = _    // getter and setter methods are generated
    private var currentPrice:Double = _   // keep this field hidden from other classes
    // any instance of a Stock class can access a private field of any other Stock instance.

    // object private field:  -> private[this]
    private[this] var price: Double = _
    // error: this method won't compile because price is now object-private
    //    def isHigher(that: Stock): Boolean = this.price > that.price
  }
}


// 4.8. Assigning a Field to a Block or Function and Lazy Evaluation
object Section4p8{
  /*
   * assignment of the code block to the text field and the println statement are both in the body of the Foo class,
   *  -> they are in the class’s constructor,
   *  -> will be executed when a new instance of the class is created (sometimes we dont want this so we use "lazy" modifier)
   */
  class Foo{
    val text = {  // assigneng code block to a field  (using return)
      var lines=""
      lines = io.Source.fromFile("/etc/passwd").getLines.mkString
      lines
    }
    println(text)
  }

  // lazy evaluation, useful when:
  //    -> the field might not be accessed in the normal processing of your algorithms,
  //    -> running the algorithm will take a long time, and you want to defer that to a later time
  class FooLazy{
    // lazy evaluation:
    lazy val text_lazy = io.Source.fromFile("/etc/passwd").getLines.foreach(println)
  }
  val f = new FooLazy // will not get text_lazy to be printed
  f.text_lazy   // will get text_lazy to be printed

  class FooNotLazy{
    val text = io.Source.fromFile("/etc/passwd").getLines.foreach(println)
  }
  val q = new FooNotLazy  // will get text to be printed
}



// 4.9. Setting Uninitialized var Field Types
object Section4p9{
  // Solution:
  //  -> In general, define the field as an Option (ex. None:Option[<Type>])
  //  -> For certain types, such as String and numeric fields, you can specify default initial values.
  case class Address(city:String, state:String, zip:String)
    //  -> Case class constructor parameters are val by default,
    //    -> so dont need to specify modifier and getter will be automatically generated
  case class Person(val userName: String, var passWord:String){   // you can override the default val too
    var age=0
    var firstName = ""
    var lastName =""
    var address = None:Option[Address]
  }
  // to initialize None:Option initialized variable:
  val p = Person("alvinalexander", "secret")  // case class dont need new to be instantiated
  p.address = Some(Address("Talkeetna", "AK", "99676"))
  // to access Option typed variable:
  p.address.foreach{ a=>
    println(a.city)
    println(a.state)
    println(a.zip)
  }
}


// 4.10. Handling Constructor Parameters When Extending a Class
object Section4p10{
  // Problem
  //    You want to extend a base class, and need to work with the constructor parameters declared in the base class and new parameters in the subclass.
  // Solution:
  //    Declare base class as usual with val or var constructor parameters
  //    Declare sub class:
  //      -> leave the val or var declaration off of the fields that are common to both classes.
  //      -> define new constructor parameters in the subclass as val or var fields, as usual.
  case class Address(city:String, state:String)
  class Person(var name: String, var address:Option[Address]){
    override def toString = if(address==None) name else s"$name @ $address"
  }
  class Employee(name:String, address:Option[Address], var age:Int) extends Person(name, address){
    // rest of code
  }

  // creating new Employee
  val p = new Employee("Duo", Some(Address("San Jose", "CA")), 20)
  println(p.toString)
  println(p.address.get.state)
}

// 4.11. Calling a Superclass Constructor
object Section4p11{
  // Problem:
  //    You want to control the superclass constructor that’s called when you create constructors in a subclass.
  // Solution:
  //    See comments below
  case class Address(city:String, state:String)
  case class Role(role:String)
  class Person(var name:String, var address:Address){
    def this(name:String){ // no way for Employee auxiliary constructors to call this constructor
      this(name, null)
      address = null
    }
    override def toString = if(address==null) name else s"$name @ $address"
  }
  // subclass can choose which super class constructor to call from its primary constructor
  //    -> by extending super/aux constructor of the super class
  class Employee(name:String, var role:Role, address:Address) extends Person(name, address){
    // subclass cannot choose which super class constuctor to call form its auxiliary constructor
    //    all subclass' auxiliary constructors will call the same superclass constructor that’s called from the subclass’s primary constructor.
    def this(name:String){
      this(name, null, null)  //
    }
    def this(name:String, role:Role){
      this(name, role, null)
    }
    def this(name:String, address:Address){
      this(name, null, address)
    }
  }
}


// 4.12. When to Use an Abstract Class
object Section4p12{
  // Problem:
  //    Scala has traits, and a trait is more flexible than an abstract class, so you wonder, “When should I use an abstract class?”
  // Solution:
  //    You want to create a base class that requires constructor arguments
  //      -> because traits don’t allow constructor parameters
  //    The code will be called from Java code.

  // about abstract class:
  //    To declare that a method is abstract, just leave the body of the method undefined:
  //      -> def speak
  //    When a class extends an abstract class, it must
  //      -> implement all its abstract methods
  //      -> otherwise, it must declare itself as an abstract class
  //    can only extend one abstract class (but can extend may traits)
  abstract class Database{
    def save()
    def update()
    def delete()
  }
  abstract class BaseController(db:Database){
    def save() {db.save}
    def update() {db.update}
    def delete() {db.delete}

    def connect()   // abstract
    def getStatus:String    // an abstract method that returns a String
    def setServerName(serverName:String)     // an abstract method that takes a parameter
  }
}


// 4.13. Defining Properties in an Abstract Base Class (or Trait)
object Section4p13{
  // Solution:
  //    declare both val and var fields in an abstract class (or trait)
  //      -> they can be abstract or have concrete implementations
  abstract class Pet(name:String){
    // When you define an abstract field in an abstract class or trait,
    //    the Scala compiler does not create a field in the resulting code;
    //    it only generates the methods that correspond to the val or var field. (getter/setter)
    // developers define a def that takes no parameters in the abstract base class rather than defining a val.
    //    -> They may then redefine it to val in the concrete class, if desired
    val greeting:String
    def greeting2: String
    var age:Int
    def sayHello() { println(greeting) }
    override def toString = s"I say $greeting, I am $age"
  }
  class Dog (name: String) extends Pet (name) {
    // when you provide concrete values for abstract fields in your concrete classes, you must again define your fields to be val or var
    //     -> Because the fields don’t actually exist in the abstract base class (or trait)
    //     -> override keyword is not necessary

    val greeting = "Woof"
    val greeting2 = "Woof2"
    var age = 2
  }
  class Cat (name: String) extends Pet (name) {
    // when you provide concrete values for abstract fields in your concrete classes, you must again define your fields to be val or var
    val greeting = "Meow"   // abstract fields again need to be specified as val or var
    val greeting2 = "Meow2"
    var age = 5
  }

  // Concrete val fields in abstract classes
  //    -> in the example below, the greeting variable is created in both classes (Animal2, Dog2)
  abstract class Animal2 {
    // When defining a concrete val field in an abstract class,
    //    -> you can provide an initial value
    //    -> then override that value in concrete subclasses:
    val greeting = "Hello"    // provide an initial value
      // To prevent a concrete val field in an abstract base class from being overridden in a subclass, declare the field as a final val
      //    -> final val greeting = "Hello"
    def sayHello() { println(greeting) }
    def run
  }
  class Dog2 extends Animal2 {
    override val greeting = "Woof"    // need to override if the field is of type val in abstract class
    def run { println("Dog is running") }
  }

  // Concrete var fields in abstract classes
  abstract class Animal3 {
    var greeting = "Hello"
    var age = 0
    override def toString = s"I say $greeting, and I'm $age years old."
  }
  class Dog3 extends Animal3 {
    // Because the fields are declared and initialized in the abstract Animal base class,
    //    -> there’s no need to redeclare the fields as val or var in the concrete Dog subclass.
    greeting = "Woof"     // do not need to override if the field is of type var in the abstract class
    age = 2
  }
}


// 4.14. Generating Boilerplate Code with Case classes
//  Solution:
//    when case class is declared, Scala compiler will automatically generate many Boilerpalte codes for this class, including:
//      -> apply/unapply, getter/setter, toString, equals, copy
object Section4p14{
  // define case class
  case class Person(name:String, relation:String)   // name and relation are 'val' by default
  case class PersonII(name:String, var relation:String)   // can also override the default val by modify it with var

  // defining a case class can result a lot of Boilerplate code to be generated
  // 1. default apply method -> dont need to use "new" to create a new instance
    val emily = Person("Emily", "Niece")
    val emilyII = PersonII("Emily", "Niece")
  // 2. default unapply method -> used to extract information in match expressions
    emily match {
      case Person(n,r) => println(n,r)  // n and r are automatically extracted from the matched pattern and stored in n and r
    }
  // 3. default accessor and mutator
  //    -> accessor methods are auto generated for all params,
  //    -> mutator methods are only generated for params declared as var
    println(emily.name)
    emilyII.relation = "Sister" // relation has setter because it is modified with var
  // 4. default toString method
    println(emily)
  // 5. default equals and hashCode methods -> used to compare instances
    val hannah = Person("Hannah", "Niece")
    println(emily==hannah)    // return false
  // 6. default copy method -> used to colone an object
    val emilyTemp = Person("emilyTemp", "SisterTmp")    // emilyTemp: Person = Person(emilyTemp,SisterTmp)
    val emilyCpy = emilyTemp.copy(name="emilyTmp")           // emilyCpy: Person = Person(emilyTmp,SisterTmp)
        // seletive copy
}


// 4.15. Defining an equals Method (Object Equality)
//    -> with equals method defined, you can use == to compare object equality instead of calling the method itself
object Section4p15{
  // an example
  class Person (name:String, age:Int){
    def canEqual(a:Any) = a.isInstanceOf[Person]
    override def equals(that:Any):Boolean = that match{
      //case that:Person => that.canEqual(this) && this.hashCode == that.hashCode
      case bla:Person => bla.canEqual(this) && this.hashCode==bla.hashCode
      case _ => false
    }
    override def hashCode:Int = {
      val prime = 31
      var result =1
      result = prime * result + age
      result = prime * result + (if (name==null) 0 else name.hashCode)
      return result
    }
  }
}

// 4.16. Creating Inner Classes
// Problem:
//    You want to create a class as an inner class to help keep the class out of your public API, or to otherwise encapsulate your code.
//  Solution:
//    Declare one class inside another class
object Section4p16{
  // in Scala, such inner classes are bound to the outer object.”
  // can have object inside class, class inside object, class inside class, object inside object
  class OuterClass{
    object InnerObject{
      val x = 1
    }
  }
  println(new OuterClass().InnerObject.x)

  // can have class inside object
  object OuterObject{
    class InnerClass{
      val x=1
    }
  }
  println(new OuterObject.InnerClass().x)

  class OuterClassII{
    class InnerClassII{
      val x=1
    }
  }
  val objII = new OuterClassII()
  println(new objII.InnerClassII().x)
  // this does not work: println(new OuterClassII().InnerClassII().x)

  object OuterObjectII{
    object InnerObjectII{
      val x=1
    }
  }
  println(OuterObjectII.InnerObjectII.x)
}
