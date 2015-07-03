// 7.1. Packaging with the Curly Braces Style Notation (import selector clause)
// Solution:
//      Wrap one or more classes in a set of curly braces with a package name
// curly brace packaging syntax can be very convenient
//      - when you want to declare multiple classes and packages in one file.
object Section7p1{
    package com.acme.store{     // You can define multiple levels of depth at one time
        class Foo{
            override def toString = s"I am come.acme.store.Foo"
        }
    }
    // the above declaration is equivalent to:
    package come.acme.store
    class Foo{
        override def toString = s"I am come.acme.store.Foo"
    }


    // can also next packages using curly brace style
    package orderentry{
        class Foo{ override def toString = s"I am orderentry.Foo"
    }
    package customers{      // one package nested inside the other
        class Foo { override def toString = "I am customers.Foo" }
        package database{
            // this Foo is different than customers.Foo or orderentry.Foo
            class Foo { override def toString = "I am customers.database.Foo" }
        }
    }
    println(new orderentry.Foo)
    println(new customers.Foo)
    println(new customers.database.Foo)
}


// 7.2. Importing One or More Members
// Scala allows importing with following styles:
//      - place import statements anywhere (top of a class, within a class or object, within a method, or within a block of code)
//      - Import classes, packages, or objects
//      - Hide and rename members when you import them
object Section7p2{
    // import in the Java way:
    import java.io.File
    import java.io.IOException
    import java.io.FileNotFoundException
    // import in the Scala way:
    import java.io.{File, IOException, FileNotFoundException}
    // import everything from java.io package
    import java.io._

    // Placing import statement anywhere
    import java.io.File
    import java.io.PrintWriter
    class Foo {
        import javax.swing.JFrame   // only visible in this class
        // ...
    }
    class Bar {
        import scala.util.Random    // only visible in this class
        // ...
        def doBar = {
            import scala.util.Random
            println("")
      }
    }
}


// 7.3. Renaming Members on Import
// Problem:
//      You want to rename members when you import them to help avoid namespace collisions or confusion.
// Solution:
//      using renaming clause. (ex. "=> JavaList" in import java.util.{ArrayList => JavaList})
object Section7p3{
    // importing and renaming with alias
    import java.util.{ArrayList => JavaList}
    import java.util.{Date => JDate, HashMap => JHashMap}
    // Then, within your code, refer to the class by the alias you’ve given it:
    val list = new JavaList[String]     // calling
    // the original (real) name of the class can’t be used in your code
        // error: this won't compile because HashMap was renamed during the import process
        //  val map = new HashMap[String, String]

    // you can even rename class members during importing:
    import System.out.{println => p}
    p("hello")
}


// 7.4. Hiding a Class During the Import Process
// Problem:
//      You want to hide one or more classes while importing other members from the same package.
//          -> typically due to naming conflicts with some scala's class

object Section7p4{
    // Ex. hides the Random class, while importing everything else from the java.util package:
    import java.util.{Random => _, _}

    // the above code breaks down to two parts:
    import java.util.{Random => _}  // hides the Random class
    import java.util._      // import everything else in the package
    // Note:
    //      -> the _ import wildcard must be in the last position
    //      -> import java.util.{_, Random => _} gives errors: "Wildcard import must be in last position"

    // another example:
    import java.util.{List => _, Map => _, Set => _, _}
}


// 7.5. Using Static Imports
// Problem:
//      - You want to import members in a way similar to the Java static import approach,
//          so you can refer to the member names directly, without having to prefix them with their class name.
// Solution:
//      import the class members: ex. import the <package-path>.<class>._
object Section7p5{
    // ex. import all members of the Java Math class:
    import java.lang.Math._
    val a = sin(0)
    val a = cos(PI)

    // ex. another example
    //  package com.alvinalexander.dates;
    public enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
    }
    import com.alvinalexander.dates.Day._
    // somewhere after the import statement
    if (date == SUNDAY || date == SATURDAY) println("It's the weekend.")
    // without static import, have to preceed SUNDAY/SATURDAY with Day, making the code less readable
    if (date == Day.SUNDAY || date == Day.SATURDAY) {
        println("It's the weekend.")
    }
}


// 7.6. Using Import Statements Anywhere
// Problem:
//      You want to use an import statement anywhere
//         -> generally to limit the scope of the import, to make the code more clear, or to organize your code.
// Solution:
//      You can place an import statement almost anywhere inside a program
object Section7p6{
    // importing at top of class definition (Java way)
    import scala.util.Random
    class ImportTests {
        def printRandom {
            val r = new Random
        }
    }

    // import inside the class
    //      -> This limits the scope of the import to the code in the class that comes after the import statement.
    class ImportTests {
        import scala.util.Random
        def printRandom {
            val r = new Random
        }
    }

    // place an import statement inside a block
    //      -> limiting the scope of the import to only the code that follows the statement, inside that block
    def printRandom {
        {   // a code block
            import scala.util.Random
            val r1 = new Random // this is fine
        }
        val r2 = new Random // error: not found: type Random
    }

    // NOTE: import statement must go before the referencing point
    class ImportTests {
        def printRandom {
            val r = new Random
        }
        import scala.util.Random    // this doesn't work because the import is after the attempted reference
    }

    // example of import in package
    package orderentry {
        import foo._
        // more code here ...
        //  -> can access members of foo
        //  -> can’t access members of bar or baz.
    }
    package customers {
        import bar._
        // more code here ...
        //  -> can access members of bar
        //  -> can’t access members of foo or baz
        package database {
            import baz._
            // more code here ...
            //  -> can access members in bar and baz.
        }
    }

    // example of import in class
    package foo
    // available to all classes defined below
    import java.io.File
    import java.io.PrintWriter
    class Foo {
        // only available inside this class
        import javax.swing.JFrame
        // ...
    }
    class Bar {
        // only available inside this class
        import scala.util.Random
        // ...
    }

}
