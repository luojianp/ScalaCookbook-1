// Introduction
/* Some important concepts when working with Scala Collections:
    1. predicate:
        -> predicate is simply a method, function, or anonymous function that takes one or more parameters and returns a Boolean value
            ex. def isEven (i: Int) = if (i % 2 == 0) true else false
    2. anonymous function:
        -> Function Literals  (powerful when combined with filter method on a collection)
            ex. (i: Int) => i % 2 == 0  (long form)
                _ % 2 == 0              (short form)
            ex. equivalent                                  |   for {
                val events = list.filter(_ % 2 == 0)        |        e <- list
                                                            |        if e % 2 == 0
                                                            |   } yield e
    3. implied loop:
        -> the built-in loop in filter, foreach, map, reduceLeft, and many more collection methods
*/



// 10.1. Understanding the Collections Hierarchy
package Section10p1{
    object ScalaCollectionHierarchy{
    /*
        Traversable (traverse repeatedly)
            -> Iterable (traverse only once)
                -> Seq
                    -> Indexed Seq (default Vector())
                        ex. Vector(), Array(), Range(), String, StringBuilder, ArrayBuffer
                        ex. val x = IndexedSeq(1,2,3) -> Vector(1, 2, 3)
                    -> Linear Seq (default List())
                        ex. List(), LinkedList(), MutableList(), Stack(), Queue(), Stream()
                        ex. val seq = scala.collection.immutable.LinearSeq(1,2,3) -> List(1, 2, 3)
                        Note:
                            - List() is essentially a linked list
                            - Linear Seq has head, tail, isEmpty etc. methods to traverse the sequence
                    -> Buffer
                        ex. ArrayBuffer, ListBuffer
                -> Set  (no duplicated elements)
                    ex. BitSet(), HashSet(), ListSet(), SortedSet(), TreeSet()
                    ex. val set = Set(1, 2, 3)      // immutable set
                        val s = collection.mutable.Set(1, 2, 3)     // mutable set
                -> Map (a set of key-value pairs, unique keys)
                    ex. HashMap(), WeakHashMap(), SortedMap(), TreeMap(), LinkedHashMap(), ListMap()
                    ex. val m = Map(1 -> "a", 2 -> "b")     // immutable map
                        val m = collection.mutable.Map(1 -> "a", 2 -> "b")  // mutable map
                    Note:
                        - mutable map is not in scope by default, so you must import or specify its path to use it
    */
    }
}

// 10.2. Choosing a Collection Class
package Section10p2{
    object ChoosingASequence{
        /*  The "to-go" option:
                                |   immutable   |   Mutable
            Indexed             |   Vector      |   ArrayBuffer
            Linear(LinkedList)  |   List        |   ListBuffer
        */
    }
    object ChoosingAMap{
        /*

        */
    }
    object ChoosingASet{
        /*

        */
    }
}

// 10.3. Choosing a Collection Method to Solve a Problem
package Section10p3{
    object CommonMethodOnAllCollections{
        // c collect f
        //      - Builds a new collection by applying a partial function to all elements of the collection on which the function is defined.
        Vector(1,2,3,4,5,6,7,8,9,0) collect {
            case i if i>3 => i;
            case i if i<=3 => Vector("Zero", "One", "Two", "Three")(i)
        }  // ->  Vector(One, Two, Three, 4, 5, 6, 7, 8, 9, Zero)
        // c map f
        //      - Creates a new collection by applying the function to all the elements of the collection.
        Vector(1,2,3,4,5).map(_*2)      // -> Vector(2, 4, 6, 8, 10)
        // c foreach f
        //      - Applies the function f to all elements of the collection.
        Vector(1,2,3).foreach(i=>print(i*2+" "))        // - 2 4 6
        // c filter p
        //      - Returns all elements from the collection for which the predicate is true.
        Vector(1,2,3,4,5,6,7,8,9,0) filter(_%2==0)      // -> Vector(2, 4, 6, 8, 0)
        // c filterNot p
        //      - Returns all elements from the collection for which the predicate is false
        Vector(1,2,3,4,5,6,7,8,9,0) filterNot(_%2==0)      // -> Vector(1, 3, 5, 7, 9)
        // c reverse
        //      - Returns a collection with the elements in reverse order. (Not available on Traversable, but common to most collections, from GenSeqLike.)
        Vector(1,2,3,4,5).reverse       // -> Vector(5, 4, 3, 2, 1)
        // c sortWith f
        //      - Returns a version of the collection sorted by the comparison function f.
        Vector(7,3,5,2,8).sortWith(_ < _)     // -> Vector(2, 3, 5, 7, 8)


        // c count p
        //      - Counts the number of elements in the collection for which the predicate is satisfied.
        Vector(1,2,3,4,5).count(_%2==0)     // -> return 2
        // c size
        //      - Returns the size of the collection.
        Vector(2,2,3,4,5).size      // -> 5
        // c max
        //      - Returns the largest element from the collection.
        Vector(1,2,3,4,5).max           // -> 5
        // c min
        //      - Returns the smallest element from the collection.
        Vector(1,2,3,4,5).min           // -> 1
        // c sum
        //      - Returns the sum of all elements in the collection.
        Vector(1,2,3,4,5).sum           // -> 15
        // c product
        //      - Returns the multiple of all elements in the collection.
        Vector(1,2,3,4,5,6).product     // -> 720


        // c1 diff c2
        //      - Returns the difference of the elements in c1 and c2.
        Vector(1,2,3,4,5,6,7,8,9,0) diff Vector(1,2,3,4,5)     // -> Vector(6, 7, 8, 9, 0)
        Vector(1,2,3,4,5,6,7,8,9,0) diff List(1,2,3,4,5)       // -> Vector(6, 7, 8, 9, 0)
        // c1 intersect c2
        //      - On collections that support it, it returns the intersection of the two collections (the elements common to both collections).
        Vector(1,2,3,4,5) intersect Vector(3,4,5,6,7)       // -> Vector(3, 4, 5)
            // Vector(1,2,3,4,5).intersect(Vector(3,4,5,6,7))   same
            // Vector(1,2,3,4,5) intersect(Vector(3,4,5,6,7))   same
        // c1 union c2
        //      - Returns the union (all elements) of two collections.
        Vector(1,2,3,4,5) union Vector(3,4,5,6,7)       // -> Vector(1, 2, 3, 4, 5, 3, 4, 5, 6, 7)


        // c take n
        //      - Returns the first n elements of the collection.
        List(1,2,3,4,5,6).take(3)       // -> List(1, 2, 3)
        // c drop n
        //      - Returns all elements in the collection except the first n elements.
        List(1,2,3,4,5,6) drop 3     // -> List(4, 5, 6)

        // c takeWhile p
        //      - Returns elements from the collection while the predicate is true. Stops when the predicate becomes false.
        List(1,2,3,4,5,6) takeWhile(_ < 4)        // -> List(1, 2, 3)
        // c dropWhile p
        //      - Returns a collection that contains the “longest prefix of elements that satisfy the predicate.”
        //          (stops dropping as long as condition is no longer met)
        List(1,2,3,4,5,6) dropWhile(_ < 4)           // -> List(4, 5, 6)


        // c find p
        //      - Returns the first element that matches the predicate as Some[A]. Returns None if no match is found.
        Vector(1,2,3,4,5,6,7,8,9,0) find(_%2==0)       // -> Some(2)
        Vector(1,2,3,4,5,6,7,8,9,0) find(_>10)         // -> None

        // c exists p
        //      - Returns true if the predicate is true for at least one element in the collection.
        Vector(1,2,3,4,5,6,7,8,9,0) exists(1<=_)       // -> true
        Vector(1,2,3,4,5,6,7,8,9,0) exists (10<=_)     // -> false
        // c forAll p
        //      - Returns true if the predicate is true for all elements, false otherwise.
        List(1,2,3).forall(x => x < 3)          // -> false

        // c isEmpty
        //      - Returns true if the collection is empty, false otherwise.
        Vector().isEmpty        // -> true
        // c nonEmpty
        //      - Returns true if the collection is not empty.
        Vector().nonEmpty       // -> false

        // c hasDefiniteSize
        //      - Tests whether the collection has a finite size. (Returns false for a Stream or Iterator for example)
        Vector(1,2,3,4,5,6).hasDefiniteSize     // -> true

        // c flatten
        //      - Converts a collection of collections (such as a list of lists) to a single collection (single list)
        List(List(1,2), List('a','b'), List(true, false)) flatten       // -> List(1, 2, a, b, true, false)
        // c flatMap f
        //      - Returns a new collection by applying a function to all elements of the collection c (like map),
        //          and then flattening the elements of the resulting collections.
        List("I", "am", "an", "apple") flatMap(_.toUpperCase)       // List(I, A, M, A, N, A, P, P, L, E)
        // c unzip
        //      - The opposite of zip, breaks a collection into two collections by dividing each element into two pieces, as in breaking up a collection of Tuple2 elements.
        List(("one", "won"), ("two", "too")).unzip     // -> (List(1, 3),List(2, 4))
        // c1 zip c2
        //       - Creates a collection of pairs by matching the element 0 of c1 with element 0 of c2, element 1 of c1 with element 1 of c2, etc.
        List("one", "two") zip List("won", "too")       // ->  List((one,won), (two,too))
        // c zipWithIndex
        //      - Zips the collection with its indices.
        List("one", "one", "two", "two").zipWithIndex       // -> List((one,0), (one,1), (two,2), (two,3))



        // c foldLeft(z)(op)
        //      - Applies the operation to successive elements, going from left to right, starting at element z.
        Vector(1,2,3,4,5,6,7,8,9,0).foldLeft(0)(_+_)       // -> 45
        Vector(1,2,3,4,5,6,7,8,9,0).foldLeft(3)(_+_)       // -> 48
        // c reduceLeft op
        //      - The same as foldLeft, but begins at the first element of the collection.
        Vector(1,2,3,4,5,6,7,8,9,0).reduceLeft(_+_)        // -> 45
        // c foldRight(z)(op)
        //      - Applies the operation to successive elements, going from right to left, starting at element z.
        Vector(1,2,3,4,5,6,7,8,9,0).foldRight(0)(_+_)       // -> 45
        Vector(1,2,3,4,5,6,7,8,9,0).foldRight(3)(_+_)       // -> 48
        // c reduceRight op
        //      - The same as foldRight, but begins at the last element of the collection.
        Vector(1,2,3,4,5,6,7,8,9,0).reduceRight(_+_)       // -> 45


        // c groupBy f
        //      - Partitions the collection into a Map of collections according to the function.
        Vector(1,2,3,4,5,6).groupBy(_%3)        // -> Map(2 -> Vector(2, 5), 1 -> Vector(1, 4), 0 -> Vector(3, 6))
        // c partition p
        //      - Returns two collections according to the predicate algorithm.
        Vector(1,2,3,4,5,6).partition(_%3==0)   // -> (Vector(3, 6),Vector(1, 2, 4, 5))
        // c slice(from, to)
        //      - Returns the interval of elements beginning at element from and ending at element to.
        Vector(1,2,3,4,5).slice(1,3)        // -> Vector(2, 3)
        // c span p
        //      - Returns a collection of two collections; the first created by c.takeWhile(p), and the second created by c.dropWhile(p).
        Vector(1,2,3,4,5,6).span(_ < 4)     // -> (Vector(1, 2, 3),Vector(4, 5, 6))
        // c splitAt n
        //      - Returns a collection of two collections by splitting the collection c at element n.
        Vector(1,2,3,4,5,6).splitAt(4)      // -> (Vector(1, 2, 3, 4),Vector(5, 6))


        // c head
        //      - Returns the first element of the collection. Throws a NoSuchElementException if the collection is empty.
        Vector(1,2,3,4,5,6).head        // -> 1
        // c last
        //      - Returns the last element from the collection. Throws a NoSuchElementException if the collection is empty.
        Vector(1,2,3,4,5,6).last        // -> 6
        // c headOption
        //      - Returns the first element of the collection as Some[A] if the element exists, or None if the collection is empty.
        Vector(1,2,3,4,5,6).headOption        // -> Some(1)

        // c lastOption
        //      - Returns the last element of the collection as Some[A] if the element exists, or None if the collection is empty.
        Vector(1,2,3,4,5,6).lastOption        // -> Some(6)
        // c init
        //      - Selects all elements from the collection except the last one. Throws an UnsupportedOperationException if the collection is empty.
        Vector(1,2,3,4,5,6).init        // -> Vector(1, 2, 3, 4, 5)
        // c tail
        //      - Returns all elements from the collection except the first element.
        Vector(1,2,3,4,5,6).tail        // -> Vector(2, 3, 4, 5, 6)


        // c par
        //      - Returns a parallel implementation of the collection, e.g., Array returns ParArray.
        Vector(1,2,3,4,5).par           // -> ParVector(1, 2, 3, 4, 5)
        // c view
        //      - Returns a nonstrict (lazy) view of the collection.
        Vector(1,2,3,4,5).view      // -> SeqView(...)
    }
    object FilteringMethods{


    }
    object TransformerMethods{

    }
    object GroupingMethods{

    }
    object InformationalAndMathematicalMethods{

    }
    object Others{

    }
}
