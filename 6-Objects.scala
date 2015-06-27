// Introduction
//      1. in Scala, object is also a keyword
//      2. scala object can:
//          -> launch application;
//          -> create singleton;
//          -> companion object:
//                  . to create the equivalent of Java’s static members
//                  . won’t need to use the new keyword to create an instance of the class
//          -> package object(optional): hold common code in a particular package level

// 6.1. Object Casting
// Solution:
//      use: asInstanceOf[...], available on all objects
object Section6p1{
    val a = 10
    val b = a.asInstanceOf[Long]
    val c = a.asInstanceOf[Byte]

    // another example:
    /*
        val cm = new ConfigurationManager("config.xml") // instance of Recognizer
        val recognizer = cm.lookup("recognizer").asInstanceOf[Recognizer] // instance of Microphone
        val microphone = cm.lookup("microphone").asInstanceOf[Microphone]
    */
}
/* Tips: .getClass vs classOf[T]
        -> The classOf[T] method returns the runtime representation for a Scala type.
                It is analogous to the Java expression T.class.

        -> Using classOf[T] is convenient when you have a type that you want information about,
        -> while getClass is convenient for retrieving the same information from an instance of the type.
 */
// 6.2. The Scala Equivalent of Java’s .class
//  Solution:
//      Use the Scala classOf method instead of Java’s .class
object Section6p2{
    /*
        val info = new DataLine.Info(classOf[TargetDataLine], null)
        equivalent to Java's:
        info = new DataLine.Info(TargetDataLine.class, null);
    */
    val stringClass = classOf[String]
    stringClass.getMethods
}

// 6.3. Determining the Class of an Object
// Solution:
//      use getClass method on the object
object Section6p3{
    // one example:
    def printAll(numbers:Int*){
        println("Class: "+ numbers.getClass)
    }
    printAll(1,2,3) // -> class scala.collection.mutable.WrappedArray$ofInt
    printAll()     // -> class scala.collection.immutable.Nil$

    // another example
    val hello = <p>Hello, <br/>world</p>        // hello: scala.xml.Elem = <p>Hello, <br/>world</p>
    hello.child.foreach(e => println(e.getClass))
    // class scala.xml.Text
    // class scala.xml.Elem
    // class scala.xml.Text
}


// 6.4. Launching an Application with an Object
// Problem:
//      You want to start an application with a main method, or provide the entry point for a script.
// Solution:
//      -> define an object that extends the App trait
//      -> define an object with a properly defined main method
object Section6p4{
    // the following code creates a simple but complete Scala application:
    //  -> The code in the body of the object is automatically run, just as if it were inside a main method.
    //  How to run a Scala application (app/main):
    //      compile it with scalac, and then run it with scala

    // creating application by extending App trait
    object Hello extends App {
        // Note here: args returns the current command line arguments as an array.
        //      -> so the <args> here acts as an implicitely defined val which holds the command line arguments
        if (args.length == 1)
            // note: have to use ${..} here, using $args(0) will give: "Hello, [Ljava.lang.String;@27973e9b(0)"
            //     -> seems args(0) is treated as an expression, so it has to be wrapped by {}
            println(s"Hello, ${args(0)}")
        else
            println("I didn't get your name.")
    }
    // creating application by defining main method
    object Hello2 {
        def main(args: Array[String]) {
            println("Hello, world")
        }
    }
}


// 6.5. Creating Singletons with object
// Problem:
//      want to create a Singleton object to ensure that only one instance of a class exists.
// Solution:
//      Create Singleton objects in Scala with the object keyword
//          -> usually used for creating utility methods
//          -> Singleton objects also make great reusable messages when using actors
object Section6p5{
    // object singleton used as utility methods
    object CashRegister {
        def open() { println("opened") }
        def close() { println("closed") }
    }
    object Main extends App {
        // singleton's methods are called just like static methods on a Java class
        CashRegister.open
        CashRegister.close
    }

    // object singleton used as rsuable messages:
    //      If you have a number of actors that can all receive start and stop messages, you can create Singletons like this:
    case object StartMessage
    case object StopMessage
    // use those objects as messages that can be sent to actors:
    /*
        inputValve ! StopMessage
        outputValve ! StopMessage
    */

    // In addition to creating objects in this manner,
    //      you can give the appearance that a class has both static and nonstatic methods
    //          by using an approach known as a “companion object.”
}


// 6.6. Creating Static Members with Companion Objects
//   Problem:   Scala does not have a static keyword.
//   Solution:  declare them in companion object of that class
object Section6p6{
    // Note: companion object must:
    //      - have the SAME NAME as the class
    //      - in the SAME FILE as the class
    // pizza class
    class Pizza(var crustType:String){
        override def toString = s"crust type is $crustType"
    }
    // companion object
    object Pizza{
        val CRUST_TYPE_THIN = "thin"
        val CRUST_TYPE_THICK = "thick"
        def getFoo = "Foo"
    }
    // access "static" members in companion class
    println(Pizza.CRUST_TYPE_THICK + Pizza.CRUST_TYPE_THIN + Pizza.getFoo)
    val myPizza = new Pizza(Pizza.CRUST_TYPE_THICK)

    // class and its companion object can access each other's private members:
    class Foo{
        private val secret = 2
        def getCompanionObjPrivMember = Foo.obj
    }
    object Foo{
        private val obj = "Foo's object"
        def accessClassPrivateMember(myFoo:Foo) = myFoo.secret
    }
    println(new Foo().getCompanionObjPrivMember)
    println(Foo.accessClassPrivateMember(new Foo()))
}


// 6.7. Putting Common Code in Package Objects
//   Problem:
//      to make functions, fields, and other code available at a package level, without requiring a class or object.
//   Solution:
//      - Put the code you want to make available to all classes within a package in a package object.
//      - put your code in a file named package.scala in the directory where you want your code to be available
object Section6p7{
    // Example:
    /*
    1. I want the package code to be available in the com.alvinalexander.myapp.model package
    2. I put the file package.scala in the com/alvinalexander/myapp/model source code directory:
            +-- com
                +-- alvinalexander
                    +-- myapp
                        +-- model
                            +-- package.scala
    3. in package.scala source code
        - start with the usual name of the package:
                package com.alvinalexander.myapp.model
        - take the name of the last package level (model) off that statement, leaving with:
                package com.alvinalexander.myapp
        - use that name (model) as the name of your package object:
                package object model { ...}
    4. summing up, for the package.scala living under "com/alvinalexander/myapp/model", the first few lines of this fils should be:
        package com.alvinalexander.myapp
        package object model {
            ...
        }
     */

     // what to put in package.scala
     //     - methods and functions that are common to the package: constants, enumerations, and implicit conversions etc.
}


// 6.8. Creating Object Instances Without Using the new Keyword
// Solution: (2 ways to do it)
//      - Create a companion object for your class,and define an apply method in the companion object with the desired constructor signature.
//      - Define your class as a case class.
object Section6p8{
    // mehtod 1: companion object + apply method
    //      -> Define an apply method in the object that takes the desired parameters
    //      -> This method is essentially the constructor of your class
    // Note:
    //      The apply method in a companion object is treated specially by the Scala compiler:
    //          val p = Person("Fred Flinstone")  -converted-to-> val p = Person.apply("Fred Flinstone") by Scala compiler
    class Person{
        var name: String = _
        var age: Int = _
    }
    object Person{
        // a one-arg constructor
        def apply(name:String):Person = {
            var p = new Person
            p.name = name
            p
        }
        // a two-arg constructor
        def apply(name:String, age:Int):Person = {
            var p = new Person
            p.name = name
            p.age = age
            p
        }
    }
    // calling:
    val dawn = Person("Dawn")
    val fred = Person("Fred", 30)
    val a = Array(Person("Dan"), Person("Elijah"))


    // method 2: Declare your class as a case class
    //      -> this works because the case class generates an apply method in a companion object for you
    //      -> case class creates much more code for you than just the apply method
    case class PersonII (var name: String, var age: Int)  // want accessor and mutator methods for the name and age fields
    object PersonII{    // multiple constructors (auxilary)
        def apply() = new PersonII("<no-name>", 0)
        def apply(name:String) = new PersonII(name, 0)
    }
    val aII = PersonII()
    val bII = PersonII("Al")
    val cII = PersonII("William Shatner", 82)
    println(aII.name + "---" + aII.age)
}


// 6.9. Implement the Factory Method in Scala with apply
//  Note:
//      The idea of the factory method is to make sure that concrete instances can only be created through the factory;
//          therefore, the class constructors should be hidden from all other classes
object Section6p9{
    trait Animal{
        def speak()
    }
    object Animal{
        private class Dog extends Animal{
            override def speak(){
                println("woof")
            }
        }
        private class Cat extends Animal{
            override def speak(){
                println("meow")
            }
        }
        // the factory method
        def apply(s:String):Animal = {
            if (s=="dog") new Dog else new Cat
        }
    }
    // to call:
    val cat = Animal("cat") // returns a Cat
    cat.speak
    val dog = Animal("dog") // returns a Dog
    dog.speak
}
