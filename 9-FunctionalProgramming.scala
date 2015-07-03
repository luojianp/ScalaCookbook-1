// Introduction
//  Scala encourages Expression Oriented Programming (EOP)
//      -> every expression yields a vallue
package Section9p0{
    // if statement return a value
    object example1{
        val a:Option[Int] = None
        val b:Option[Int] = None
        val greater = if (a.get > b.get) a.get else b.get
    }
    object example2{
        // try/catch statement return a value
        val aString = "hello world"
        val result = try {
            aString.toInt
        } catch {
            case _ :Throwable => 0
        }
    }
}


// 9.1. Using Function Literals (Anonymous Functions)
// Problem:
//  You want to use an anonymous function (function literal) so you can
//      - pass it into a method that takes a function
//      - or to assign it to a variable.
package Section9p1{
    /*
        Notes:
            - the "=>" symbol: transformer:
                left side: the parameter to be transformed
                right side: the algorithm to transform the symbol
            - when can use the "_" wildcard
                when the parameter only appears once in your function literal:
                        ex. x.map(_=>_*2) NOT OK
                            x.map(_*2)  OK
     */
     object example1{
         val x = List.range(1,10)    // List(1, 2, 3, 4, 5, 6, 7, 8, 9)
         val evens = x.filter((i:Int) => i%2==0)  // "(i:Int) => i%2==0" is the function Literal
            // val evens = x.filter(i => i % 2 == 0)
         val evensII = x.filter(_%2==0) // simplified form of the above statement
         x.foreach((i:Int) => println(i))    // print each element in the list x
             // x.foreach(println(_))    // simplified
        // finding the equivalent form using "_":
        //      1. using the right part of the transformer "=>"
        //      2. replace the variable  with "_"
        //      3. then u get the simplified form
        // ex.
        //      (i:Int) => println(i)   <--->   println(_)
        //      (i:Int) => i%2==0       <--->   _%2==0
     }
}

// 9.2. Using Functions as Variables
// Problem:
//  You want to pass a function around like a variable, just like you pass String, Int, and other variables
package Section9p2{
    object example1{
        // recall a function literal   -> (i:Int) => {i*2}
        // myDouble is now an instance of a function: <funciton value> (compare to an instance of Int, String etc.)
        val myDouble = (i:Int)=>{i*2}   // defines a function literal passing it to a variable
        myDouble(10)    // invoking myDouble as if calling a method,  returns 20
        List.range(1,5).map(myDouble)   // pass myDouble to any method/function, returns List(2, 4, 6, 8)
    }
    object example2{
        // specify return type of function literal
        //      - {} are only required if function body grows to more than one expression
        // observation:
        //      - if func literal return type not specified: need to specify the input type of the input parameters (example 1 and 3)
        //      - if the return type is specified , do not need to specify the inputtype fo the input parameters (example 2 and 4)
        val f = (i: Int) => { i % 2 == 0 }  // implicite return type
        val fII:Int=>Boolean = i=>{ i % 2 == 0 }   // explicite return type
        val fAdd = (x:Int,y:Int) => x+y     //implicite return type
        val fAddII:(Int, Int)=>Int = (x, y) => x+y  // explicite return type
    }

    object example3{
        // using method as an anonymous function
        //      - can define a method and pass it like an instance variable
        def modMethod(i:Int):Boolean = {i%2==0}
        val modFunction = (i: Int) => i % 2 == 0    // comparing to an equivalent function
        List.range(1,5).filter(modMethod)   // -> List(2, 4)
        List.range(1,5).filter(modFunction) // -> List(2, 4)
    }

    object example4{
        // assigning an existing function/method to a function variable
        //      val c = scala.math.cos -> gives missing arguments error
        // observaton:
        //      need to use "_" to fill the parameter slot
        val c = scala.math.cos _
        val c1 = scala.math.cos(_)
        c(0)    // to call
        val p = scala.math.pow(_, _)
        p(scala.math.E, 2)  // e**2
    }

    /* Summary
         - Think of the => symbol as a transformer
                it transforms input data on its left side to some new output data
                    using the algorithm on its right side.
         - Use def to define a method; Use val to create a function.
         - When assigning a function to a variable
                a function literal is the code on the right side of the expression.
         - A function value is an object, and extends the FunctionN traits in the main scala package,
                such as Function0 for a function that takes no parameters
                FunctionN for a function that takes N parameters
    */
}

// 9.3. Defining a Method That Accepts a Simple Function Parameter
//      -> the function that’s passed in must match the function signature you define
//      -> general syntax for defining a function as a method parameter
//              parameterName: (parameterType(s)) => returnType
package Section9p3{
    object example1{
        // Example:
        // define a method including a signature of the method to be passed in
        //      f:() -> a function that takes no parameters
        //      =>Unit -> this method returns nothing

        def executeFunction(f:()=>Unit){    // type of f:  ()=>Unit
            f()
        }
        // define a function that match this signature
        val sayHello = () => { println("Hello") }
        // pass the function as parameter
        executeFunction(sayHello)
    }
    object Example2{
        // Example: parameter function that takes String and returns Int
        // note: the type of the function is the entire "String => Int"
        def executeFunctionII(f:String => Int){

        }
        def executeFunctionIII(f:(String) => Int){

        }
    }
}


// 9.4. More Complex Functions
// Problem:
//      You want to define a method that takes a function as a parameter
//          - that function may have one or more input parameters,
//          - and may also return a value
// Solution:
//      - define a method that takes a function as a parameter
//      - specify the function signature you expect to receive
//      - execute that function inside the body of the method
package Section9p4{
    object Example1{
        // define a function that takes a String and returns an Int
        def executeFunction(f:(String) => Int) {/*...*/}    // type of f:  "(String) => Int"
        // define a function that takes two ints and return a Boolean
        def executeFunction(f:(Int, Int) => Boolean) {/*...*/}
        // takes String, Int, Double and return a sequence of String
        def exec(f:(String, Int, Double) => Seq[String]) {/*...*/}  // type of f: "(String, Int, Double) => Seq[String]"
        // takes Int and return nothing
        def exec(f:(Int) => Unit) {/*...*/}
    }

    object Example2{
        // passing function along with other parameters
        def executeXTimes(f:()=>Unit, n:Int){
            for (i<-1 to n) f()     // note the () after f is necessary
        }
        def sayHelloMethod() {println("Hello")}
        val sayHelloFunc = ()=>println("Hello")
    }

    object Example3{
        // another Example
        def executeAndPrint(f:(Int, Int)=>Int, m:Int, n:Int){
            val data = f(m,n)
            println(data)
        }
        val sum = (x:Int, y:Int)=>x+y
        val multiply = (x:Int,y:Int)=>x*y
        executeAndPrint(sum, 1,2)   // prints 3
        executeAndPrint(multiply, 1,2)  // prints 2
    }

    object Example4{
        // another Example
        def exec(f:(Any, Any)=>Unit, x:Any, y:Any){
            f(x,y)
        }
        def printTwoThings_Func = (x:Any, y:Any) => {
            println(x)
            println(y)
        }
        def printTwoThings_Method(x:Any, y:Any){
            println(x)
            println(y)
        }
        case class Person(name:String)
        exec(printTwoThings_Func, Person("Duo").name, List(1,2,3))
    }
}


// 9.5. Using Closures
/*  To create a closure in Scala:
        - define a function that refers to a variable that is in the same scope as its declaration
        - this function can be later called even when this variable is no longer in the functions current scope
            ex. when the function is passed to another class, method or function
    Note: the key to use closure:  the function and the variable you want to link to Must reside in the same scope
 */
 /* When to use Closure:
        - you are passing around a funcition
        - and wish this function can refer to a variable in its declaration scope
 */
package Section9p5{
    package Section9p5.otherscope{
        class Foo{
            // a method that accepts a function parameter
            def printResult(f:(Int)=>Boolean, v:Int){
                println(f(v))
            }
            def buyStuff(f:(String)=>Unit, stuff:String){
                f(stuff)
            }
        }
    }
    object ClosureExample extends App{
        val foo = new otherscope.Foo()
        // one example
        var votingAge = 18
        def isOfVotingAge(age:Int) = age>=votingAge
        foo.printResult(isOfVotingAge, 19)      // prints True
        votingAge = 20
        foo.printResult(isOfVotingAge, 19)      // print False

        // another example
        import scala.collection.mutable.ArrayBuffer
        val basket = ArrayBuffer("apple")
        def addToBasket(stuff:String){
            basket +=stuff
            println(basket.mkString(","))
        }
        foo.buyStuff(addToBasket, "orange") // prints apple,orange
        foo.buyStuff(addToBasket, "grape")  // prints apple,orange,grape
    }
}


// 9.6. Using Partially Applied Functions
// Problem:
//      - You want to eliminate repetitively passing variables into a function
//  Note:
//      use "_:<T>" to represent the not-preloaded variable
//      refer to Section9p2/example4:  "assigning an existing function/method to a function variable":
//          -> val p = scala.math.pow(_, _)
package Section9p6{
    object Example1{
        val sum = (a:Int, b:Int, c:Int) => a+b+c
        // f is the partially applied function
        //      - partially applied function is essentially a vairable that you can pass around (function value)
        val f = sum(1,2, _:Int)
        println(f(3))   // outputs 6
    }
    // a function that adds a prefix and a suffix to an HTML snippet:
    object Example2{
        def wrap(prefix:String, html:String, suffix:String)={
            prefix+html+suffix
        }
        // at a certain point in your code, you know that you always want to add the same prefix and suffix to different HTML strings
        def wrapWithDiv = wrap("<div>", _:String, "</div>") // the partially applied functions (an variable, function value)
        wrapWithDiv("<p>Hello, world</p>")
        wrapWithDiv("<img src=\"/images/foo.png\" />")
    }
}


// 9.7. Creating a Function That Returns a Function
// Problem:
//      - You want to return a function (algorithm) from a function or method.
//  Note:
//      compare this to Section9p6 (partially applied function), they seem to achieve the same thing from two different ways
package Section9p7{
    object example1{
        // this returns another func: (toWhom:String)=>{ "<your-passed-in-arg-in-saySomething>" + " " + toWhom }
        def saySomething(prefix:String) = (toWhom:String)=>{
            prefix + " " + toWhom
        }
        val sayHello = saySomething("Hello")    // returns another func and assign it to a function value called sayHello
        println(sayHello("Tom"))
    }
    object example2{
        // this function returns a funtion too
        def greeting(language:String) = (toWhom:String)=>{
            language match{
                case "english" => "Hello, "+toWhom
                case "spanish" => "Buenos dias, "+toWhom
            }
        }
        val hello = greeting("english")     // returns a func and assign it to a function value called hello
        println(hello("Duo"))
        val buenosDias = greeting("spanish")        // returns a func and ... value called buenosDias
        println(buenosDias("Duo"))
    }
}


// 9.8. Creating Partial Functions
// Problem:
//      - you want to define a/some function(s) that will only work for a subset of possible input values,
package Section9p8{
    // signature of partial function:
    //      - trait PartialFunction[-A, +B] extends (A) => B
    //      - partial functions has inherited apply(..) and isDefinedAt(..) method
    object example1{
        // a partial function that takes an Int and returns an Int
        val divide = new PartialFunction[Int, Int]{
            def apply(x:Int) = 42/x
            def isDefinedAt(y:Int) = y!=0
        }
        divide.isDefinedAt(0)   // return false
        divide(2)       // return 21
    }

    object example2{
        // a partial function that takes an Int and returns a String
        val convertLowNumToString = new PartialFunction[Int, String]{
            val nums = Array("one","two","three","four","five")
            def apply(x:Int) = nums(x-1)
            def isDefinedAt(x:Int) = x>=1 && x<=5
        }
        convertLowNumToString.isDefinedAt(10)   // returns false
        convertLowNumToString(3)        // returns "three"
    }

    // chain PartialFunction together using orElse, andThen
    //      "orElse" and "andThen" method comes from the Scala PartialFunction trait
    object example3{
        // converts 1 to "one", etc., up to 5
        val convert1to5 = new PartialFunction[Int, String] {
            val nums = Array("one", "two", "three", "four", "five")
            def apply(i: Int) = nums(i-1)
            def isDefinedAt(i: Int) = i > 0 && i < 6
        }
         // converts 6 to "six", etc., up to 10
         val convert6to10 = new PartialFunction[Int, String] {
             val nums = Array("six", "seven", "eight", "nine", "ten")
             def apply(i: Int) = nums(i-6)
             def isDefinedAt(i: Int) = i > 5 && i < 11
        }
        val handle1to10 = convert1to5 orElse convert6to10
        handle1to10(3)
        handle1to10(9)
    }
    // the collect method
    object example4{
        // The collect method takes a partial function as input
        // <list> collect(<partial-func>): Builds a new collection by applying a partial function to all elements of this list on which the function is defined.
        //      - collect method is written to test the isDefinedAt method for each element it’s given
        //      - it doesn’t run the partial function when the input value is 0
        val divide: PartialFunction[Int, Int] = {
            case d: Int if d != 0 => 42 / d
        }
        List(0,1,2) map { divide }  // gives "scala.MatchError: 0 ...."
        List(0,1,2) collect { divide }  // List(42, 21)

        // another example
        List(42, "cat") collect { case i: Int => i + 1 }    // List(43)
    }
}
