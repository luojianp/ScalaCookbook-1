// Introduction
//      - trait is like Java's interface
//      - syntax: class <class-name> extends <trait1> with <trait2> with ...
//      - trait can have implemented methods
//      - can mix more than one trait into a class
//      - trait can control what classes it can be mixed into


// 8.1. Using a Trait as an Interface
// Solutin:
//      using trait like a Java interface (declare methods in trait and implement them in the extending class)
// Tips:
//      If a class extends one trait ( use the extends keyword )
//          class Foo extends Trait { ...
//      If a class extends a class and one or more traits ( use extends for the first trait and with to mix-in the other traits )
//          class Foo extends BaseClass with Trait1 with Trait2 { ...
//      If a class extends multiple traits ( always use extends before the class name, and use with before the trait names )
//          class Foo extends Trait1 with Trait2 with Trait3 with Trait4 { ...
//
object Section8p1{

    abstract class Animal{
        def speak()
    }

    trait WaggingTail{
        // traite can have implemented method with or without parameter
        def startTail() { println("tail started") }
        def stopTail() { println("tail stopped") }
    }

    trait FourLeggedAnimal{
        // traite can have unimplemented method with or without parameter
        def walk()
        def run(speed:Double)
    }

    // one trait can extend another trait
    trait Canine extends WaggingTail with FourLeggedAnimal{
        // ...
    }

    // example of extending class and trait
    class Dog extends Animal with WaggingTail with FourLeggedAnimal{
        // need to implement all unimplemented method, otherwise Dog need to be declared as abstract
        //      startTail() and stopTail() do not need to be implemeted because they are already implemented
        def speak() { println("dog is speaking") }
        def walk() { println("dog is walking") }
        def run(speed:Double) { println("dog is running") }
        override def startTail { println("dog tail started") }  // can override an already implemented trait method
        // do not have to impelemented the stopTail() method again because it is already implemented
    }
    // must be declared abstract because it does not implement all of unimplemented methods
    //      ex. in this case, speak() is not implemented
    abstract class DogAbs extends Animal with WaggingTail with FourLeggedAnimal{
        def walk() { println("dog is walking") }
        def run() { println("dog is running") }
    }
}


// 8.2. Using Abstract and Concrete Fields in Traits
object Sectin8p2{
    // concrete field: a field with an initial value
    // abstract field: a field with no initial value

    trait PizzaTrait{
        var numToppings: Int    // abstract
        var size = 14           // concrete
        val maxNumToppings = 10     // concrete
    }

    class Pizza extends PizzaTrait{
        var numToppings = 0     // 'override' not needed
        size = 16   // You don’t need to use the override keyword to override a var field in a subclass (or trait)
        override val maxNumToppings = 15    // you do need to use "override val" to override a val field:
    }
}


// 8.3. Using a Trait Like an Abstract Class
// Solution:
//      - declare or define methods in trait
//      - in the class that extends the trait, can override the defined methods ||  use them as is
object Section8p3{
    trait Pet{
        def speak() { println("Yo") } // concrete implementation
        def comeToMaster()      // abstract method
    }
    class Dog extends Pet{
        // don't need to implement 'speak' if you don't need to
        def comeToMaster { println("I am coming") }     // must define this method to avoid being an abstract class
    }
    class Cat extends Pet{
        override def speak() { println("Meow") }    // override the speak method
        def comeToMaster { println("That's not gonna happen.") }    // must define this method to avoid being an abstract class
    }

    // If a class extends a trait without implementing its abstract methods, it must be defined as abstract
    abstract class FlyingPet extends Pet{
        def flying() { println("I am flying !") }
    }
}

// 8.4. Using Traits as Simple Mixins
object Section8p4{
    trait Tail {
        def wagTail() { println("tail is wagging") }
        def stopTail() { println("tail is stopped") }
    }
    abstract class Pet (var name: String) {
        def speak() // abstract
        def ownerIsHome() { println("excited") }
        def jumpForJoy() { println("jumping for joy") }
    }
    class Dog (name: String) extends Pet (name) with Tail { // note the constructor parameter of the extended class Pet
        def speak() { println("woof") }
        override def ownerIsHome() {
            wagTail
            speak
        }
    }
    val zeus = new Dog("Zeus")
    zeus.ownerIsHome
    zeus.jumpForJoy
}


// 8.5. Limiting Which Classes Can Use a Trait by Inheritance
// Solution: using syntax:
//      trait [TraitName] extends [SuperThing]
//          - TraitName can only be mixed into classes that extend a type named SuperThing
//          - SuperThing may be a trait, class, or abstract class:
object Section8p5{
    class StarfleetComponent
    trait StarfleetWarpCore extends StarfleetComponent
    class Starship extends StarfleetComponent with StarfleetWarpCore


    class StarfleetComponentII
    trait StarfleetWarpCoreII extends StarfleetComponentII
    class RomulanStuff
    // won't compile, Warbird and StarfleetWarpCoreII don’t share the same superclass:
    //      class Warbird extends RomulanStuff with StarfleetWarpCoreII

    // another example:
    abstract class Employee
    class CorporateEmployee extends Employee
    class StoreEmployee extends Employee

    trait DeliversFood extends StoreEmployee
    class DeliveryPerson extends StoreEmployee with DeliversFood    // this is allowed
    // class Receptionist extends CorporateEmployee with DeliversFood  // won't compile
}

// 8.6. Marking Traits So They Can Only Be Used by Subclasses of a Certain Type
// Solution:
//      - by defining the "Self Type" of this trait as the Super Class
object Section8p6{
    // Syntax:
    class BaseClass
    trait MyTrait {
        // -> MyTrait can only be mixed into a class that is a subclass of BaseType,
        this: BaseClass =>   // the self type of this trait
        // declaration goes here
    }

    // one example
    trait StarfleetWarpCore {
        this: Starship =>
        // more code here ...
    }
    class Starship
    class Enterprise extends Starship with StarfleetWarpCore

    class RomulanShip
    // class Warbird extends RomulanShip with StarfleetWarpCore    // this won't compile

    // another example
    trait WarpCore {
        this: StarshipII with WarpCoreEjector with FireExtinguisher =>
        // declaration, definition goes here
    }
    class StarshipII
    trait WarpCoreEjector
    trait FireExtinguisher
    // this works because EnterpriseII extends all required classes/traits
    class EnterpriseII extends StarshipII with WarpCore with WarpCoreEjector with FireExtinguisher

     // won't compile, because EnterpriseIII does not extend all the required super class/trait required by WarpCore
     // class EnterpriseIII extends StarshipII with WarpCore with WarpCoreEjector
}


// 8.7. Ensuring a Trait Can Only Be Added to a Type That Has a Specific Method
// Solution:
//      - Use a variation of the self-type syntax
//          to declare that any class that attempts to mix in the trait must implement the method you specify.
object Section8p7{
    trait WarpCore{
        this: {     // variation of self-type syntax
            def ejectWarpCore(password:String):Boolean
            def startWarpCore():Unit
        } =>
        // declarations and, or implementations
    }
    class StarShip

    class Enterprise extends StarShip with WarpCore{
        def ejectWarpCore(password:String):Boolean = {
            if (password == "password") { println("ejecting core"); true } else false
        }
        def startWarpCore() { println("core started") }
    }
}


// 8.8. Adding a Trait to an Object Instance
object Section8p8{
    class DavidBanner
    trait Angry {
        println("You won't like me ...")
    }
    val hulk = new DavidBanner with Angry   // instance with trait
}


// 8.9. Extending a Java Interface Like a Trait
// Solution:
//      In your Scala application,
//      use the extends and with keywords to implement your Java interfaces, just as though they were Scala traits.

object Section8p9{
    // difference between Java Interface and Scala Trait:
    //      - Java interfaces don’t implement behavior
    //          you’ll need to implement Java interface's methods
    //          or declare the Scala extending class as abstract.
    //      - Scala trait can
    //          either implement or not implement methods
    //          either initiate or not initiate fields  (variables)

    /*
    // java
    public interface Animal {
        public void speak();
    }
    public interface Wagging {
        public void wag();
    }
    public interface Running {
        public void run();
    }
    // scala
    class Dog extends Animal with Wagging with Running {
        def speak { println("Woof") }
        def wag { println("Tail is wagging!") }
        def run { println("I'm running!") }
    }
    */
}
