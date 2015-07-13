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
        // A sequence is a linear collection of elements and may be indexed or linear (a linked list)

        /*  The "to-go" option:
                                |   immutable   |   Mutable
            Indexed             |   Vector      |   ArrayBuffer
            Linear(LinkedList)  |   List        |   ListBuffer
        */
    }

    object ChoosingAMap{
        // A map contains a collection of key/value pairs

        /*
         Mutable & Immutable
              HashMap         |       ListMap     |       Map

         Only Immutable
              SortedMap       |       TreeMap

         Only Mutable
              LinkedHashMap   |      WeakHashMap
        */

        // SortedMap
        //      -> keep elements in sorted order by key
        // LinkedHashMap
        //      -> store elements in insertion order
    }

    object ChoosingASet{
        // A set is a collection that contains no duplicate elements

        /*
            Mutable & Immutable
                Set          |       SortedSet     |       TreeSet     |       BitSet     |       HashSet

            Only Immutable
                 ListSet

            Only Mutable
                 LinkedHashSet
        */

        // SortedSet
        //      -> return elements in sorted order by key
        // LinkedHashSet
        //      -> store elements in insertion order
    }

    object OtherCollectionLikeTypes{
        // Stack, Queue, and Range
        // tuples, enumerations
        // Option/Some/None and Try/Success/Failure
    }
}

// 10.3. Choosing a Collection Method to Solve a Problem
package Section10p3{
    object CommonCollectionMethods{
        /*
            • c refers to a collection
            • f refers to a function
            • p refers to a predicate
            • n refers to a number
            • op refers to a simple operation (usually a simple function)
        */
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
        List(("one", "won"), ("two", "too")).unzip     // -> (List(one, two),List(won, too))
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

    object MutableCollectionMethods{
        import scala.collection.mutable._
        // c += x   |   c −= x
        //      - Adds/remove the element x to the collection c.
        ArrayBuffer(1,2,3) += 5     // -> ArrayBuffer(1, 2, 3, 5)
        ArrayBuffer(1,2,3) -= 3     // -> ArrayBuffer(1, 2)

        // c += (x,y,z)     |   c −= (x,y,z)
        //      - Adds/remove the elements x, y, and z to the collection c.
        ListBuffer(1,2,3) += (3,4,5)        // -> ListBuffer(1, 2, 3, 3, 4, 5)
        ListBuffer(1,2,3) += (1,3)        // -> ListBuffer(2)

        // c1 ++= c2    |   c1 −−= c2
        //      - Adds/remove the elements in the collection c2 to the collection c1.
        ListBuffer(1,2,3) ++= ArrayBuffer(2,3,4,5)      // -> ListBuffer(1, 2, 3, 2, 3, 4, 5)
        ListBuffer(1,2,3) --= ArrayBuffer(2)      // -> ListBuffer(1, 3)

        // c(n) = x
        //      - Assigns the value x to the element c(n).
        ArrayBuffer(1,2,3)(0) = 10      // becomes ArrayBuffer(10, 2, 3)

        // c clear
        //      - Removes all elements from the collection.
        ArrayBuffer(1,2,3,4).clear

        // c remove n   | c remove (n, len)
        //      - Removes the element at position n, or the elements beginning at position n and continuing for length len
        ArrayBuffer(1,2,3,4,5).remove(2)        // -> ArrayBuffer(1, 2, 4, 5), return 3
        ArrayBuffer(1,2,3,4,5).remove(2, 2)     // -> ArrayBuffer(1, 2, 5), do not return anything

    }

    object ImmutableCollectionMethods{
        // because : immutable collections can’t be modified
        //      -> result of each expression in the first column must be assigned to a new variable

        // c1 ++ c2
        //      - Creates a new collection by appending the elements in the collection c2 to the collection c1.
        Vector(1,2,4) ++ Vector(4,5,6)  // -> Vector(1, 2, 4, 4, 5, 6)

        // c :+ e
        //      - Returns a new collection with the element e appended to the collection c.
        Vector(1,2,4) :+ 5      // -> Vector(1, 2, 4, 5)

        // e +: c
        5 +: Vector(1,2,4)      // -> Vector(5, 1, 2, 4)

        // e :: list
        //      - Returns a List with the element e prepended to the List named list. (:: works only on List.)
        5 :: List(1,2,3)        // -> List(5, 1, 2, 3)

        // Refer to CommonCollectionMethods Section for the methods below
        // c drop n
        // c dropWhile p
        // c filter p
        // c filterNot p
        // c head
        // c tail
        // c take n
        // c takeWhile p
    }

    object MapsMethods{
        /*
            • m refers to a map
            • mm refers to a mutable map
            • k refers to a key
            • p refers to a predicate (a function that returns true or false)
            • v refers to a map value
            • c refers to a collection
        */
        object MethodsForBothMutableAndImmutableMaps{
            // m(k)
            //      - Returns the value associated with the key k.
            Map(1->'a', 2->'b', 3->'c', 4->'d')(4)      // -> return 'd'
            Map(1->'a', 2->'b', 3->'c', 4->'d')(9)      // -> Error
            // m get k
            //      - Returns the value for the key k as Some[A] if the key is found, None otherwise.
            Map(1->'a', 2->'b', 3->'c', 4->'d').get(3)      // -> Some(c)
            Map(1->'a', 2->'b', 3->'c', 4->'d').get(9)      // -> None

            // m getOrElse(k, d)
            //      - Returns the value for the key k if the key is found, otherwise returns the default value d.
            Map(1->'a', 2->'b', 3->'c', 4->'d').getOrElse(3, "Not Found")      // -> c : Any
            Map(1->'a', 2->'b', 3->'c', 4->'d').getOrElse(9, "Not Found")      // -> Not Found : Any

            // m keys
            //      - Returns the keys from the map as an Iterable.
            Map(1->'a', 2->'b', 3->'c', 4->'d').keys            // -> Set(2, 4, 1, 3)
            // m keySet
            //      - Returns the keys from the map as a Set.
            Map(1->'a', 2->'b', 3->'c', 4->'d').keySet          // -> Set(2, 4, 1, 3)
            // m keyIterator
            //      - Returns the keys from the map as an Iterator.
            Map(1->'a', 2->'b', 3->'c', 4->'d').keysIterator        // -> res251: Iterator[Int] = non-empty iterator

            // m values
            //      - Returns the values from the map as an Iterable.
            Map(1->'a', 2->'b', 3->'c', 4->'d').values          // -> HashMap(b, d, a, c)
            // m valuesIterator
            //      - Returns the values from the map as an Iterator.
            Map(1->'a', 2->'b', 3->'c', 4->'d').valuesIterator  // -> res250: Iterator[Char] = non-empty iterator

            // m contains k
            //      - Returns true if the map m contains the key k.
            Map(1->'a', 2->'b', 3->'c', 4->'d').contains(5)     // -> false
            // m isDefinedAt k
            //      - Returns true if the map contains the key k.
            Map(1->'a', 2->'b', 3->'c', 4->'d').isDefinedAt(4)      // -> true

            // m filter p   |   m filterKeys p
            //      - Returns a map whose (keys and values) | keys match the condition of the predicate p.
            Map(1->'a', 2->'b', 3->'c', 4->'d').filterKeys(_%2==0)     // -> Map(2 -> b, 4 -> d)

            // m mapValues f
            //      - Returns a new map by applying the function f to every value in the initial map.
            Map(1->'a', 2->'b', 3->'c', 4->'d').mapValues(_.toUpper)        // -> Map(2 -> B, 4 -> D, 1 -> A, 3 -> C)
        }


        object ImmutableMapMethods{
            // m-k
            //      - Returns a map with the key k (and its corresponding value) removed.
            Map(1->'a', 2->'b', 3->'c', 4->'d') - 3     // -> Map(2 -> b, 4 -> d, 1 -> a)

            // m - (k1, k2, k3)
            //      - Returns a map with the keys k1, k2, and k3 removed.
            Map(1->'a', 2->'b', 3->'c', 4->'d') - (2,3)     // -> Map(4 -> d, 1 -> a)

            // m -- c
            //      - Returns a map with the keys in the collection removed
            Map(1->'a', 2->'b', 3->'c', 4->'d') -- Vector(1,2)      // -> Map(4 -> d, 3 -> c)

            // m ++ m
            Map(1->'a', 2->'b') ++ Map(3->'c', 4->'d')      // -> Map(2 -> b, 4 -> d, 1 -> a, 3 -> c)
        }
        object MutableMapMethods{
            import scala.collection.mutable._
            // mm += (k -> v)   |   mm += (k1 -> v1, k2 -> v2)
            //      - Add the key/value pair(s) to the mutable map mm.
            Map(1->'a', 2->'b', 3->'c', 4->'d') += (5->'e')     // -> Map(2 -> b, 5 -> e, 4 -> d, 1 -> a, 3 -> c)
            Map(1->'a', 2->'b') += (3->'c', 4->'d', 5->'e')     // ->  Map(2 -> b, 5 -> e, 4 -> d, 1 -> a, 3 -> c)

            // mm -= k  |   mm -= (k1, k2, k3)
            //      - Remove map entries from the mutable map mm based on the given key(s).
            Map(1->'a', 2->'b', 3->'c', 4->'d') -= 4            // -> Map(2 -> b, 1 -> a, 3 -> c)
            Map(1->'a', 2->'b', 3->'c', 4->'d') -= (3,4)        // -> Map(2 -> b, 1 -> a)

            // mm ++= c
            //      - Add the elements in the collection c to the mutable map mm.
            Map(1->'a', 2->'b') ++= List(3->'c', 4->'d')        // ->  Map(2 -> b, 4 -> d, 1 -> a, 3 -> c)
            // mm --= c
            //      - Remove the map entries from the mutable map mm based on the keys in the collection c.
            Map(1->'a', 2->'b', 3->'c') --= List(2,3)           // -> Map(1 -> a)

        }
    }
    object FilteringMethods{
        /* Methods that can be used to filter a collection
            Include:
                collect, diff, distinct, drop, dropWhile, filter, filterNot, find, foldLeft, foldRight, head, headOption,
                init, intersect, last, lastOption, reduceLeft, reduceRight, remove, slice, tail, take, takeWhile, and union.
        */

    }
    object TransformerMethods{
        /* Transformer methods take at least one input collection to create a new output collection, typically using an algorithm you provide
            Include:
                +, ++, −, −−, diff, distinct, collect, flatMap, map, reverse, sortWith, takeWhile, zip, and zipWithIndex.
        */
    }
    object GroupingMethods{
        /*  Grouping Methods let you take an existing collection and create multiple groups from that one collection
            Include:
                groupBy, partition, sliding, span, splitAt, and unzip.
        */
    }
    object InformationalAndMathematicalMethods{
        /* These Methods: provide information about a collection
            Including:
                canEqual, contains, containsSlice, count, endsWith, exists, find, forAll, hasDefiniteSize,
                indexOf, indexOfSlice, indexWhere, isDefinedAt, isEmpty, lastIndexOf, lastIndexOfSlice, lastIndexWhere,
                max, min, nonEmpty, product, segmentLength, size, startsWith, sum

                <foldLeft, foldRight, reduceLeft, and reduceRight can also be used with a function you supply to obtain information about a collection.>
        */
    }
    object Others{
        /* Hard to categorize
            Including:
                par:        - creates a parallel collection from an existing collection;
                view:       - creates a lazy view on a collection
                flatten:    - converts a list of lists down to one list
                foreach:    - letting you iterate over the elements in a collection
                mkString:   - lets you build a String from a collection
                to*:        - convert the current collection (a List, for example) to other collection types (Array, Buffer, Vector, etc.).
        */

    }
}


// 10.4. Understanding the Performance of Collections
package Section10p4{
    // TBD
}

// 10.5. Declaring a Type When Creating a Collection Problem
package Section10p5{
    object example1{
        val x = List[Number](1, 2.0, 33D, 400L)
    }
    object example2{
        trait Animal
        trait FurryAnimal extends Animal
        case class Dog(name: String) extends Animal
        case class Cat(name: String) extends Animal
        val x = Array(Dog("Fido"), Cat("Felix"))        // -> x: Array[Product with Serializable with Animal] = Array(Dog(Fido), Cat(Felix))
        // manually assign object type
        val y = Array[Animal](Dog("Fido"), Cat("Felix"))    // -> y: Array[Animal] = Array(Dog(Fido), Cat(Felix))
    }
}


// 10.6. Understanding Mutable Variables with Immutable Collections
package Section10p6{
    // Mutable Variable vs Immutable Variable
    //      - A mutable variable (var) can be reassigned to point at new data.
    //      - An immutable variable (val) is like a final variable in Java; it can never be reassigned.

    // Mutable Collection vs Immutable Collection
    //      - The elements in a mutable collection (like ArrayBuffer) can be changed.
    //      - The elements in an immutable collection (like Vector) cannot be changed.
    //           -> but can possibly return a new immutable collection with the corresponding element added/removed/updated (ex. the ":+", "++" operators)

    object example1{
        var sisters = Vector("Melinda")
        sisters = sisters :+ "Melissa"
        sisters = sisters :+ "Marisa"
        // sisters is mutable but Vector is immutable Seq. how can this possible?
        //      -> this is because :+ operator returns a new Vector with the new element added and sisters got reassigned to this new Vector

        /*
            sisters(0) = "Molly"
            //  this does not work because sisters points to a Vector and it is an immutable Seq
            val brothers = Vector("David")
            brothers = brothers :+ "George"
            brothers = brothers :+ "Tom"
            //  this does not work either because we cannot reassign brother to the new Vector returned by :+ operator since it is val
        */
    }
}

// 10.7. Make Vector Your “Go To” Immutable Sequence
//      “When in Doubt, Use Vector.”
package Section10p7{
    object Example1{
        // create a vector and access its elements
        val v = Vector("a", "b", "c")
        v(0)

        // You can’t modify a vector (immutable), so you “add” elements to an existing vector as you assign the result to a new variable:
        val a = Vector(1, 2, 3)
        val b = a ++ Vector(4,5)        // Vector(1, 2, 3, 4, 5)
        val c = b.updated(0, 100)       // replace one element in a Vector while assigning the result to a new variable
        var x = a.take(2)
        x = a.filter(_ > 2)
    }
}

// 10.8. Make ArrayBuffer Your “Go To” Mutable Sequence
package Section10p8{
    import scala.collection.mutable.ArrayBuffer         // need to import before use, because mutable is not default available in Scala
    object Example1{
        // Create empty ArrayBuffer
        var fruits = ArrayBuffer[String]()
        var ints = ArrayBuffer[Int]()

        // manipulate ArrayBuffer
        var nums = ArrayBuffer(1,2,3,4)
        nums += 4
        nums +=(5,6)
        nums ++= ArrayBuffer(5,6,7)
        nums ++= List(9,0)

        nums --=Vector(4,5,6)
        // ...
    }
    object Example2{
        // there are many other methods to manipulate ArrayBuffer
        val a = ArrayBuffer(1, 2, 3) // ArrayBuffer(1, 2, 3)
        a.append(4)             // ArrayBuffer(1, 2, 3, 4)
        a.append(5, 6)          // ArrayBuffer(1, 2, 3, 4, 5, 6)
        a.appendAll(Seq(7,8))       // ArrayBuffer(1, 2, 3, 4, 5, 6, 7, 8)
        a.clear         // ArrayBuffer()


        val b = ArrayBuffer(9, 10)
        b.insert(0, 8)          // ArrayBuffer(8,9,10)
        b.insert(0, 6, 7)       // ArrayBuffer(6,7,8,9,10)
        b.insertAll(0, Vector(4, 5)) // ArrayBuffer(4, 5, 6, 7, 8, 9, 10)
        b.prepend(3)            // ArrayBuffer(3, 4, 5, 6, 7, 8, 9, 10)
        b.prepend(1, 2)         // ArrayBuffer(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        b.prependAll(Array(0))      // ArrayBuffer(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

        val c = ArrayBuffer.range('a', 'h') // ArrayBuffer(a, b, c, d, e, f, g)
        c.remove(0) // ArrayBuffer(b, c, d, e, f, g)
        c.remove(2, 3) // ArrayBuffer(b, c, g)

        val d = ArrayBuffer.range('a', 'h') // ArrayBuffer(a, b, c, d, e, f, g)
        d.trimStart(2) // ArrayBuffer(c, d, e, f, g)
        d.trimEnd(2) // ArrayBuffer(c, d, e)
    }
}

// 10.9. Looping over a Collection with foreach
package Section10p9{
    // foreach method applies your function to each element of the collection, but it doesn’t return a value.
    //      -> having side-effect because it does not return a value
    object Example1{
        val x = Vector(1,2,3)
        x.foreach((i:Int)=>println(i))
        x.foreach(i => println(i))      // scala can infer the type -> Int is not necessary
        x.foreach(println(_))       // can use the _ wildcard
        x.foreach(println)      // a function literal consists of one statement that takes a single argument, it can be condensed to this form
    }
    object Example2{
        // use multi-line function within forreach
        val x = Vector(1,2,3)
        x.foreach{ i =>
            var m = i*2
            println(m)
        }   // prints 2 4 6
    }

    object Example3{
        // Map can also use foreach
        val m = Map("fName"->"Duo", "lName"->"Yao")
        m.foreach(x => println(s"${x._1} -> ${x._2}"))
        m.foreach {     // a more preferred way
            case(myKey, myVal) => println(s"${myKey} -> ${myVal}")
        }
    }
}

// 10.10. Looping over a Collection with a for Loop
package Section10{
    // Notes:
    //      When using a for loop, the <- symbol can be read as “in"

    object Example1{    // general for loop
        val fruits = Traversable("apple", "banana", "orange")
        for (f<-fruits) println(f)
        for (f <- fruits) println(f.toUpperCase)
        for (f<-fruits){
            val s = f.toUpperCase
            println(s)
        }
    }
    object Example2{    // using counter inside a for loop
        val fruits = Array("apple", "banana", "orange")
        for(i <- 0 until fruits.size) println(s"$i element is ${fruits(i)}")    // using until (does not include the last element), Note, if using to, it will include the last element
        for ((frt,idx) <- fruits.zipWithIndex) println(s"$idx element is $frt")     // using zipWithIndex

        // using to and untill
        for(i <- 1 to 3) println(i)         // gives 0,1,2,3  -  good for doing something n times
        for(i <- 0 until 3) println(i)      // gives 0,1,2     - good for looping through Vector/List
    }

    object Example3{    // using for..yield : this generates a new collection, no side-effect
        val fruits = Array("apple", "banana", "orange")
        val newFruits = for (i<-fruits) yield i.toUpperCase
        val newFruits2 = for (i<-fruits) yield{
            val tmp = i.toUpperCase
            tmp
        }   // Array(APPLE, BANANA, ORANGE)

        def toUpperRev(s:String) = s.toUpperCase.reverse    // define a method and use it in for...yield
        for (i <- fruits) yield toUpperRev(i)   // -> Array(ELPPA, ANANAB, EGNARO)
        val toUpperRev2 = (i:String) => i.toUpperCase.reverse   // define a function and use it in for...yield
        for (i <- fruits) yield toUpperRev2(i)   // -> Array(ELPPA, ANANAB, EGNARO)
    }

    object Example4{    // iterating over Map
        val myMap = Map("fName"->"Duo", "lName"->"Yao")
        for ((fN, lN) <- myMap) println(s"$fN, $lN")
    }

    object Example5{    // using Guards in for loop (use if statements inside for loop)
        for {
            i <- 0 to 10
            if(i<5)
        } println(i)    // prints 0,1,2,3,4
    }
}

// 10.11. Using zipWithIndex or zip to Create Loop Counters
package Section10p11{
    object Example1{
        val days = Array("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

        days.zipWithIndex.foreach{  // use zipWithIndex in foreach loop
            case (day, idx) => println(s"$idx is $day")
        }
        days.zipWithIndex.foreach{
            d => println(s"${d._1} is ${d._2}")
        }
        for ((day, idx) <- days.zipWithIndex) {     // use zipWithIndex in for loop
            println(s"$idx is $day")
        }

        for((day, idx) <- days.zip(Stream from 1)){
            // use the zip method with a Stream to create a counter: gives you a way to control the starting value
            println(s"$idx is $day")
        }
    }
}


// 10.12. Using Iterators
package Section10p12{
    //  important part of using an iterator is
    //      - knowing that it’s exhausted after you use it.
    //      - As you access each element, you mutate the iterator, and the previous element is discarded.
    object Example1{
        val it = Iterator(1,2,3)
        it.foreach(println)     // prints 1, 2, 3
        it.foreach(println)     // prints nothing since it has exhausted
    }
}


// 10.13. Transforming One Collection to Another with for/ yield
package Section10p13{
    object Example1{
        val fruits = Vector("apple", "banana", "lime", "orange")
        for (i <- 0 until fruits.length) yield (i, fruits(i))
        for (f <- fruits) yield (f, f.length)
        val x = for (e <- fruits if e.length < 6) yield e.toUpperCase   // can add guards (if statements)
        val y = for {
            e <- fruits
            if e.length < 6
        } yield e.toUpperCase   // can add guards (if statements)
            val y_2 = fruits.map(f=>if(f.length<6) f.toUpperCase)     // same output using map
    }
    object Example2{
        case class Person (name: String)
        val friends = Vector("Mark", "Regina", "Matt")
        for (name <- friends) yield Person(name)    // -> Vector(Person(Mark), Person(Regina), Person(Matt))
    }
}


// 10.14. Transforming One Collection to Another with map
package Section10p14{
    object Example1{
        val helpers = Vector("adam", "kim", "melissa")
        val caps1 = helpers.map(e => e.capitalize)
        val caps2 = helpers.map(_.capitalize)        // -> Vector(Adam, Kim, Melissa)
        val xmls = helpers.map(name => <li>{name}</li>)     // -> Vector(<li>adam</li>, <li>kim</li>, <li>melissa</li>)

        val s = " eggs, milk, butter, Coco Puffs "
        val s_trimmed = s.split(',').map(_.trim)    // -> Array(eggs, milk, butter, Coco Puffs)
    }
    object Example2{    // define function and pass it to map
        val plusOne = (i:Char) => (i.toByte+1).toChar
        def plusOne(i:Char):Char = (i.toByte+1).toChar
        "HAL".map(plusOne)      // -> IBM
    }
    object Example3{    // map and for..yield are different when involving guards
        val fruits = List("apple", "banana", "lime", "orange", "raspberry")
        for (i <- fruits if(i.length< 6)) yield i.toUpperCase     // -> List(APPLE, LIME)
        fruits.map(i => if(i.length<6) i.toUpperCase)       // List(APPLE, (), LIME, (), ())
        fruits.filter(_.length<6).map(_.toUpperCase)        // List(APPLE, LIME) : think of an if statement as being a filter
    }
}

// 10.15. Flattening a List of Lists with flatten
package Section10p15{
    object Example1{
        val lol = List(List(1,2), List(3,4))
        val lol_flat = lol.flatten      // ->  List(1, 2, 3, 4)

        val lol1 = Array(Array(1,2), Array(2,3))
        val lol1_flat = lol1.flatten.distinct    // -> Array(1, 2, 3)

        val lol2 = Array(List(1,2), List(3,4))
        val lol2_flat = lol2.flatten    // -> Array(1, 2, 3, 4)
    }

    object Example2{
        val list = List("Hello", "world")
        val list_flatten = list.flatten     // -> List(H, e, l, l, o, w, o, r, l, d)

        val x = Vector(Some(1), None, Some(3), None)
        // flatten pulls the values out of the Some elements to create the new list, and drops the None elements:
        val x_flatten = x.flatten       // -> Vector(1, 3)
    }
}


// 10.16. Combining map and flatten with flatMap
//      -> Use flatMap in situations where you run map followed by flatten
// General rule: Whenever you think map followed by flatten, use flatMap
package Section10p16{
    object Example1{
        val bag = List("1", "2", "three", "4", "one hundred seventy five")
        def toInt(s:String): Option[Int] = {
            try {
              Some(Integer.parseInt(s.trim))
            } catch {
              case e: Exception => None
            }
        }
        val sum = bag.flatMap(toInt).sum    // -> 7
            // bag.map(toInt) -> List(Some(1), Some(2), None, Some(4), None)
            // bag.flatMap(toInt) -> List(1, 2, 4)
            //  -  flatten works very well with a list of Some and None elements
            //           It extracts the values from the Some elements while discarding the None elements:
    }
    object Example2{
        val subWord = (word:String) => List(word, word.tail, word.init)
        List("apple", "banana", "orange").map(subWord).flatten  // -> List(apple, pple, appl, banana, anana, banan, orange, range, orang)
        List("apple", "banana", "orange").flatMap(subWord)      // -> List(apple, pple, appl, banana, anana, banan, orange, range, orang)
    }
}


// 10.17. Using filter to Filter a Collection
package Section10p17{
    object Example1{
        val x = List.range(1,10)
        val evens = x.filter(_%2 == 0)    // -> List(2, 4, 6, 8)

        val fruits = Set("orange", "peach", "apple", "banana")
        val x1 = fruits.filter(_.startsWith("a"))
        val y2 = fruits.filter(_.length > 5)
    }
    object Example2{    // filter with multi-line condition
        val list = "apple" :: "banana" :: 1 :: 2 :: Nil
        val strings = list.filter{
            case s:String => true
            case _ => false
        }       // -> List(apple, banana)

        def onlyString(s:Any) = s match {
                case a:String => true
                case _ => false
        }
        list.filter(onlyString)     // -> List(apple, banana)
    }
}


// 10.18. Extracting a Sequence of Elements from a Collection
package Section10p18{
    object Example1{
        val x = (1 until 10).toArray    // Array(1, 2, 3, 4, 5, 6, 7, 8, 9)
        val x2 = (1 to 10).toArray      // Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val x1 = Array.range(1, 10)     // Array(1, 2, 3, 4, 5, 6, 7, 8, 9)

        var y = x.drop(3)       // Array(4, 5, 6, 7, 8, 9)
        y = x.dropWhile(_ < 6)  // Array(6, 7, 8, 9)
        y = x.dropRight(4)  // Array(1, 2, 3, 4, 5)
        y = x.take(3)       // Array(1, 2, 3)
        y = x.takeWhile(_ < 5)      // Array(1, 2, 3, 4)
        y = x.takeRight(3)      // Array(7, 8, 9)
        // a more generic method to slice: slice()
        y = x.slice(1,5)    // Array(2, 3, 4, 5)


        val nums = (1 to 5).toArray     // Array(1, 2, 3, 4, 5)
        nums.head           // 1
        nums.headOption     // Some(1)
        nums.init           // Array(1, 2, 3, 4)
        nums.last           // 5
        nums.lastOption     // Some(5)
        nums.tail           // Array(2, 3, 4, 5)
    }
}


// 10.19. Breaking Sequences into Subsets (groupBy, partition, etc.)
package Section10p19{
    object Example1{    // normal breaking into subset method
        val x = List(15, 10, 5, 8, 20, 12)

        var y = x.groupBy(_>10)     // -> Map(false -> List(10, 5, 8), true -> List(15, 20, 12))
        val trues = y(true)     // accessing groupBy output
        val falses = y(false)
            // trues: List[Int] = List(15, 20, 12)
            // falses: List[Int] = List(10, 5, 8)

        // creates two lists,
        //  - one containing values for which your predicate returned true
        //  - the other containing the elements that returned false.
        var y1 = x.partition(_ > 10)     // (List(15, 20, 12),List(10, 5, 8))
        val (a,b) = x.partition(_ > 10)     // accessing partition output
            // a: List[Int] = List(15, 20, 12)
            // b: List[Int] = List(10, 5, 8)

        // returns a Tuple2 based on your pred‐ icate p, consisting of “the longest prefix of this list whose elements all satisfy p, and the rest of this list.”
        var y2 = x.span(_ < 20)      // (List(15, 10, 5, 8),List(20, 12))
        var y3 = x.splitAt(2)       // (List(15, 10),List(5, 8, 20, 12))
    }
    object Example2{    // the sliding(size, step) method
        // Note:    sliding(size, step)
        //  - sliding works by passing a “sliding window” over the original sequence,
        //      - returning sequences of a length given by size.
        //      - The step parameter lets you skip over elements, as shown in the last two examples.
        //  - Just calling sliding() will return an iterator

        val x = (1 to 5).toArray
        var y = x.sliding(2).toList     // ->  List(Array(1, 2), Array(2, 3), Array(3, 4), Array(4, 5))
            // default step=1
        var y1 = x.sliding(2,2).toList  // -> List(Array(1, 2), Array(3, 4), Array(5))
            // size=2, step=2
        var y2 = x.sliding(2,3).toList  // -> List(Array(1, 2), Array(4, 5))
            // size=2, step=3
    }
    object Example3{    // the unzip() method
        // Note:
        //  - unzip() can be used to take a sequence of Tuple2 values and create two resulting lists:
        //      - one contains the first element of each tuple
        //      - another contains the second element from each tuple:
        val listOfTuple2s = List((1,2), ('a', 'b'))
        val x = listOfTuple2s.unzip     // -> (List(1, a),List(2, b))

        // the zip method:
        val women = List("Kim", "Julia")
        val men = List("Al", "Terry")
        val couples = women zip men     // -> List((Kim,Al), (Julia,Terry))
    }
}
