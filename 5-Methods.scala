// Introduction
//  Scala methods:
//    1. return type does not have to be specified
//    2. {} around short method body are not required
//    3. etc...

// 5.1. Controlling Method Scope
// Problem:
//      Scala methods are public by default, and you want to control their scope in ways similar to Java.
// Solution: using scopes
//      1. object-private
//      2. private
//      3. package
//      4. package-specific
//      5. public
/*
    Access modifier     |   Description
    private[this]       |   The method is available only to the current instance of the class it’s declared in.
    private             |   The method is available to the current instance and other instances of the class it’s declared in.
    protected           |   The method is available only to instances of the current class and subclasses of the current class.
    private[model]      |   The method is available to all classes beneath the com.acme.coolapp.model package.
    private[coolapp]    |   The method is available to all classes beneath the com.acme.coolapp package.
    private[acme]       |   The method is available to all classes beneath the com.acme package.
    (no modifier)       |   The method is public
*/

object Section5p1{
    // object private scope example - most restrictive scope
    //      -> method only available to the current instance of this class
    //      -> using modifier: private[this]
    class Foo{
        private[this] def isFoo = true
        def doFoo(other:Foo){
            // println(other.isFoo)    // this line won't compile
            // because the current Foo instance can’t access the isFoo method of the other instance, because isFoo is declared as private[this]
        }
    }
    // private scope
    //      -> accessable by current class and other instance of current class
    //      -> not accessable to subclasses or their instances
    class Animal {
        private def heartBeat {}
        private def isFoo = true
        def doFoo(other:Animal){
            println(other.isFoo)    // this compiles: private method accessable to instances of the same class
        }
    }
    class Dog extends Animal{
        // breath  // wont compile, private method not accessable to subclasses
    }
    // protected scope
    //      -> protected method is available to subclasses
    //      -> vs protected in java: in java, protected methods are available inside the current package,
    //          but in Scala, its only available in current and subclasses
    class AnimalII {
        protected def breathe {}
    }
    class DogII extends AnimalII {
        breathe     // this compiles
    }
/*    package world {   // example showing protected field is not package accessable(different from Java)
        class Animal {
            protected def breathe {}
        }
        class Jungle {
            val a = new Animal
            a.breathe // error: this line won't compile
        }
    } */

    // package scope: To make a method available to all members of the current package
    //      - mark the method as being private to the current package with the private[packageName] syntax.
    //      - any class which is under the "packageName" or subpackage of "packageName" can access to the fields modified by private[packageName]
    //           -> fine grained level of access control
    /* // one example
    package com.acme.coolapp.model {
        class Foo {
            private[model] def doX {}
            private def doY {}
        }
        class Bar {
            val f = new Foo
            f.doX // compiles
            f.doY // won't compile
        }
    }
    // another example
    package com.acme.coolapp.model {
        class Foo {
            private[model] def doX {}
            private[coolapp] def doY {}
            private[acme] def doZ {} }
        }
    import com.acme.coolapp.model._
    package com.acme.coolapp.view {
        class Bar {
        val f = new Foo
        f.doX // won't compile f.doY
        f.doZ
        }
    }
    package com.acme.common {
        class Bar {
        val f = new Foo
        f.doX // won't compile
        f.doY // won't compile f.doZ
        }
    } */

    // public scope
    //      - If no access modifier is added to the method declaration, the method is public
    /*  // an example
    package com.acme.coolapp.model {
        class Foo {
            def doX {}
        }
    }
    package org.xyz.bar {
        class Bar {
            val f = new com.acme.coolapp.model.Foo
            f.doX       // can access other package's other class' public(default) method
        }
    }
    */
}


// 5.2. Calling a Method from a Superclass
object Section5p2{
    // a simple example
    class FourLeggedAnimal{
        def walk(){println("I am walking")}
        def run(){println("I am running")}
    }
    class Dog extends FourLeggedAnimal{
        def walkThenRun(){
            super.walk()
            super.run()
        }
    }
    // Controlling which trait you call a method from
    //      If your class inherits from multiple traits, and those traits implement the same method,
    //          -> you can select not only a method name, but also a trait name when invoking a method using super
    trait Human {
        def hello = "The Human Trait"
    }
    trait Mother extends Human{
        override def hello = "Mother"
    }
    trait Father extends Human{
        override def hello = "Father"
    }
    class Child extends Human with Mother{// with Father{
        def printSuper = super.hello        // prints Father (why??)
        def printMother = super[Mother].hello       // prints Mother
        //  def printFather = super[Father].hello       // -> this will not compile until add "with Father"
        def printHuman = super[Human].hello     // prints "The Human Trait"
    }
}

// 5.3. Setting Default Values for Method Parameters
// Solution:
//      Specify the default value for parameters in the method signature.
object Section5p3{
    class Connection{
        def makeConnection(timeout:Int=5000, protocol:String= "http"){
            println("timeout = %d, protocol = %s".format(timeout,protocol))
            // ... more code ...
        }
    }
    val conn = new Connection   // to call
    conn.makeConnection()   // uses the default values for both timeout and protocol:
    conn.makeConnection(timeout=2000)   // sets timeout to 2000 and leaves protocol to its default:
    conn.makeConnection(protocol="tcp")
    conn.makeConnection(protocol="udp", timeout=4000)

    // if some parameters have default value some do not
    //      -> leave the parameters that have default values to the last ()
    //          otherwise, it is not behaving properly
    def makeConnectionI(timeout: Int, protocol: String = "http") {  // good
        println("timeout = %d, protocol = %s".format(timeout, protocol))
        // more code here
    }
    def makeConnection(timeout:Int = 5000, protocol: String) {  // no good
        println("timeout = %d, protocol = %s".format(timeout, protocol))
        // more code here
    }
}


// 5.4. Using Parameter Names When Calling a Method (named parameter)
//      syntax when using named parameter: methodName(param1=value1, param2=value2, ...)
object Section5p4{
    class Pizza{
        var crustSize = 12
        var crustType = "Thin"
        def update(crustSize:Int, crustType:String){
            this.crustSize = crustSize
            this.crustType = crustType
        }
        override def toString = s"A $crustSize inch $crustType crust pizza"
    }
    val pizza = new Pizza
        // This approach has the added benefit that you can place the fields in any order
    pizza.update(crustSize = 16, crustType = "Thick")
    pizza.update(crustType = "Pan", crustSize = 14)
}

// 5.5. Defining a Method That Returns Multiple Items (Tuples)
object Section5p5{  // this is same as how Python does it
    def getStockInfo = {
        // other code here ...
        ("NFLX", 100.00, 101.00) // this is a Tuple3
    }
    val (symbol, currentPrice, bidPrice) = getStockInfo     // one way to access the tuple data
    println(symbol, currentPrice, bidPrice)
    val retData = getStockInfo      // another way to access the tuple data: using _1, _2, _3 ...
    println(retData._1, retData._2, retData._3)
}


// 5.6. Forcing Callers to Leave Parentheses off Accessor Methods
//  Solution:
//      Define your getter/accessor method without parentheses after the method name:
//  Others:
//      method having side-effects <=> method declared with ()
//      Definition:
//          a function is said to have a side effect “if, in addition to re‐ turning a value,
//              it also modifies some state or has an observable interaction with calling functions or the outside world.”
//      Side effects include things like:
//          • Writing or printing output.
//          • Reading input.
//          • Mutating the state of a variable that was given as input, changing data in a data structure, or modifying the value of a field in an object.
//          • Throwing an exception, or stopping the application when an error occurs.
//          • Calling other functions that have side effects.
object Section5p6{
    class Pizza {
        // no parentheses after crustSize
        def crustSize = 12  // by defining accessor/getter without (), the caller then have to call the getter without using ()
    }
    val p = new Pizza
    println(p.crustSize)    // this works
    // println(p.crustSize())      // this gives compilation error
}
