// Introduction
//  Scala encourages Expression Oriented Programming (EOP)
//      -> every expression yields a vallue
object Section9p0{
    // if statement return a value
    val a:Option[Int] = None
    val b:Option[Int] = None
    val greater = if (a.get > b.get) a.get else b.get

    // try/catch statement return a value
    val aString = "hello world"
    val result = try {
        aString.toInt
    } catch {
        case _ => 0
    }
}


// 9.1. Using Function Literals (Anonymous Functions)
// Problem:
//  You want to use an anonymous function (function literal) so you can
//      - pass it into a method that takes a function
//      - or to assign it to a variable.
object Section9p1{
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
    val x = List.range(1,10)    // List(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val evens = x.filter((i:Int) => i%2==0)  // "(i:Int) => i%2==0" is the function Literal
        // val evens = x.filter(i => i % 2 == 0)
    val evensII = x.filter(_%2==0) // simplified form of the above statement

    x.foreach((i:Int) => println(i))    // print each element in the list x
        // x.foreach(println(_))    // simplified
}

// 9.2. Using Functions as Variables
// Problem:
//  You want to pass a function around like a variable, just like you pass String, Int, and other variables
object Section9p2{
    // recall a function literal   -> (i:Int) => {i*2}
    // myDouble is now an instance of a function: <funciton value> (compare to an instance of Int, String etc.)
    val myDouble = (i:Int)=>{i*2}   // defines a function literal passing it to a variable
    myDouble(10)    // invoking myDouble as if calling a method,  returns 20
    List.range(1,5).map(myDouble)   // pass myDouble to any method/function, returns List(2, 4, 6, 8)

    // specify return type of function literal
    //      - {} are only required if function body grows to more than one expression
    val f = (i: Int) => { i % 2 == 0 }  // implicite return type
    val fII:Int=>Boolean = i=>{ i % 2 == 0 }   // explicite return type
    val fAdd = (x:Int,y:Int) => x+y     //implicite return type
    val fAddII:(Int, Int)=>Int = (x, y) => x+y  // explicite return type

    // using method as an anonymous function
    //      - can define a method and pass it like an instance variable
    def modMethod(i:Int):Boolean = {i%2==0}
    val modFunction = (i: Int) => i % 2 == 0    // comparing to an equivalent function
    List.range(1,5).filter(modMethod)   // -> List(2, 4)
    List.range(1,5).filter(modFunction) // -> List(2, 4)

    // assigning an existing function/method to a function variable
    //      val c = scala.math.cos -> gives missing arguments error
    val c = scala.math.cos _
    val c1 = scala.math.cos(_)
    c(0)    // to call
    val p = scala.math.pow(_, _)
    p(scala.math.E, 2)  // e**2

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
object Section9p3{
    // Example:
    // define a method including a signature of the method to be passed in
    //      f:() -> a function that takes no parameters
    //      =>Unit -> this method returns nothing
    def executeFunction(f:()=>Unit){
        f()
    }
    // define a function that match this signature
    val sayHello = () => { println("Hello") }
    // pass the function as parameter
    executeFunction(sayHello)

    // Example: parameter function that takes String and returns Int
    def executeFunctionII(f:String => Int){

    }
    def executeFunctionIII(f:(String) => Int){

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
object Section9p4{
    // define a function that takes a String and returns an Int
    def executeFunction(f:(String) => Int) {/*...*/}
    // define a function that takes two ints and return a Boolean
    def executeFunction(f:(Int, Int) => Boolean) {/*...*/}
    // takes String, Int, Double and return a sequence of String
    def exec(f:(String, Int, Double) => Seq[String]) {/*...*/}
    // takes Int and return nothing
    def exec(f:(Int) => Unit) {/*...*/}

    // passing function along with other parameters
    def executeXTimes(f:()=>Unit, n:Int){
        for (i<-1 to n) f()     // note the () after f is necessary
    }
    def sayHelloMethod() {println("Hello")}
    val sayHelloFunc = ()=>println("Hello")

    // another Example
    def executeAndPrint(f:(Int, Int)=>Int, m:Int, n:Int){
        val data = f(m,n)
        println(data)
    }
    val sum = (x:Int, y:Int)=>x+y
    val multiply = (x:Int,y:Int)=>x*y
    executeAndPrint(sum, 1,2)   // prints 3
    executeAndPrint(multiply, 1,2)  // prints 2

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
