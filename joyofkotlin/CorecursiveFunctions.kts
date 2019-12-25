package joyofkotlin

import java.math.BigInteger
import java.util.*

/**
 * Using corecursive function to sum numbers
 */

fun <T> List<T>.tail() = this.drop(1)
fun <T> List<T>.head() = this[0]

fun <T> prepend(list: List<T>, elem: T): List<T> = foldLeft(list, listOf(elem)) { acc, head -> acc + head }

fun <T> copy(list: List<T>) : List<T> = foldLeft(list, listOf()) { listToAdd, element -> listToAdd + element }

fun  lessThan(): (Int) -> (Int) -> Boolean = { limit -> { element -> element < limit }  }

fun inc(n: Int) = n + 2
fun dec(n: Int) = n - 1

fun add(a: Int, b: Int): Int {
    tailrec fun add(sum: Int, toAdd: Int) : Int {
        return if(toAdd  < 1) sum else add(inc(sum), dec(toAdd))
    }
    return add(a, b)
}

println(add(23, 10))


/**
 * Factorial function with corecursive function as value
 */

val factorial : (Int) -> Int by lazy { { n: Int -> if (n <= 1) n else n * factorial(n - 1) } }

println("Factorial of 5")
println(factorial(5))


/**
 * Fibonacci series with recursive
 */

fun fibonacci(n: Int) : BigInteger {
    tailrec fun fib(acc: BigInteger, val2: BigInteger, x: BigInteger) : BigInteger {
        return when {
          (x == BigInteger.ZERO) -> BigInteger.ONE
          (x == BigInteger.ONE) -> acc + val2
          else -> fib(val2, acc + val2, x - BigInteger.ONE)
        }
    }
    return fib(BigInteger.ZERO, BigInteger.ONE, BigInteger.valueOf(n.toLong()))
}


fun stringWithFiboNumbers(n : Int) : String {
    tailrec fun fiboNumbers(acc: List<BigInteger>, number: Int) : String {
        return if (number < 1) acc.joinToString(", ") else
            fiboNumbers(prepend(acc, fibonacci(number)), number - 1)
    }
    return fiboNumbers(listOf(), n)
}

println(stringWithFiboNumbers(10))





/**
 * Tail recursive function of makeString()
 */


fun <T> makeString(listOf: List<T>, delim: String) : String {
    tailrec fun makeString_(acc: String, list: List<T>) : String =
        when {
            list.isEmpty() -> ""
            acc.isEmpty() -> makeString_(list[0].toString(), list.drop(1))
            else -> makeString_("$acc$delim${list[0]}", list.drop(1))
        }
    return makeString_("", listOf)
}


val resultBuildString = makeString(listOf('s','e','r','g','i','o'), ",")
println("Build your string with a list")
println(resultBuildString)




fun sum(list: List<Int>) : Int = foldLeft(list, 0, Int::plus)

println("Fold left to sum list of numbers")
println(sum(listOf(23,23,4,10)))

fun stringFoldLeft(list: List<Char>) : String = foldLeft(list, "", String::plus).capitalize()
println("Fold left to make string")
println(stringFoldLeft( listOf('s','e','r','g','i','o')))

fun makeStringTwo(list: List<Char>, delim: String ) : String =
    when {
        list.isEmpty() -> ""
        else -> foldLeft(list, "") { acc, it-> if (acc.isEmpty()) "$it" else "$acc$delim$it" }
    }

println(makeStringTwo(listOf('a','b','c'), ","))


val queue : Queue<String> = LinkedList<String>()

with(queue) {
    add("Sergio")
    add("Bla")
    add("Blo")
}
val firstInQueue = queue.remove()
println(firstInQueue)

fun <T, U> foldRight(list: List<T>, start: U, f: (U, T) -> U) : U {
    tailrec fun foldRight_(list: List<T>, acc : U) : U =
        if (list.isEmpty()) acc else foldRight_(list.take(list.size - 1), f(acc, list.last()))
    return foldRight_(list, start)
}

fun <T, U> foldLeft(list: List<T>, z: U, f: (U, T) -> U): U {
    tailrec fun foldLeft(list: List<T>, acc: U): U =
        if (list.isEmpty()) acc else
            foldLeft(list.tail(), f(acc, list.head()))
    return foldLeft(list, z)
}

fun stringFoldRight(list: List<Char>) = foldRight(list, "", String::plus).capitalize()
println("Fold right string of array ['s','e','r','g','i','o']")
println(stringFoldRight(listOf('s','e','r','g','i','o')))


fun <T> reverseList(list: List<T>) : List<T> = foldLeft(list,  listOf(), ::prepend)


println(reverseList(listOf('s','e','r','g','i','o')))

println(prepend(listOf(), 4))

// Co-recursive function
fun <T> unfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> {
    val mutableList = mutableListOf<T>()
    var elem = seed
    while (p(elem)) {
        mutableList.add(elem)
        elem = f(elem)
    }
    return mutableList
}

// Recursive fold function
fun <T> unfoldRecursive(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> =
    if (p(seed))
        prepend(unfoldRecursive(f(seed), f, p), seed)
    else
        listOf()

// Tailrec cursive unfold
fun <T> tailRecUnfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> {
   tailrec fun tailRecUnfold_(acc: List<T>, seed: T) : List<T> =
        if (p(seed)) tailRecUnfold_(acc + seed, f(seed)) else acc
    return tailRecUnfold_(listOf(), seed)
}

fun range(start: Int, end: Int) : List<Int> = tailRecUnfold(start, ::inc, lessThan()(end))

println(range(1,10))

fun recursiveRange(start: Int, end: Int) : List<Int> = if (end <= start) listOf() else
    prepend(recursiveRange(start + 1 , end), start)

println(recursiveRange(1, 20))

/**
 * Exercise 4.16 The Joy of Kotlin
 */

fun <T> iterate(seed: T, f: (T) -> T, times: Int): List<T> {
    tailrec fun iterate_(acc: List<T>, seed: T): List<T> =
        if (acc.size < times)
            iterate_(acc + seed, f(seed))
        else
            acc
    return iterate_(listOf(), seed)
}

/**
 * Map function using foldLeft for abstracting tailrecursion
 */
fun <T, U> map(list: List<T>, f: (T) -> U) : List<U> =
    foldLeft(list, listOf()) { acc, ele -> acc + f(ele) }

val newMapped = map(listOf(1,2,3,4)) { it.toDouble() }

println(newMapped)
