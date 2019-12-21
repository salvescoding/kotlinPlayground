package joyofkotlin

import java.math.BigInteger
import java.util.*

/**
 * Using corecursive function to sum numbers
 */

fun <T> List<T>.tail() = this.drop(1)
fun <T> List<T>.head() = this[0]

fun inc(n: Int) = n + 1
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
    tailrec
    fun fib(acc: BigInteger, val2: BigInteger, x: BigInteger) : BigInteger {
        println("fibonnaci numbers:  $val2")
        return when {
          (x == BigInteger.ZERO) -> BigInteger.ONE
          (x == BigInteger.ONE) -> acc + val2
          else -> fib(val2, acc + val2, x - BigInteger.ONE)
        }
    }
    return fib(BigInteger.ZERO, BigInteger.ONE, BigInteger.valueOf(n.toLong()))
}

fibonacci(20)


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



fun <T, U> foldLeft(list: List<T>, z: U, f: (U, T) -> U): U {
    tailrec fun foldLeft(list: List<T>, acc: U): U =
        if (list.isEmpty())
            acc
        else
            foldLeft(list.tail(), f(acc, list.head()))
    return foldLeft(list, z)
}

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
    tailrec fun foldRight_(list: List<T>,  start: U) : U =
        if (list.isEmpty()) start else foldRight_(list.take(list.size - 1), f(start, list.last()))
    return foldRight_(list, start)
}


fun stringFoldRight(list: List<Char>) = foldRight(list, "", String::plus).capitalize()
println("Fold right string of array ['s','e','r','g','i','o']")
println(stringFoldRight(listOf('s','e','r','g','i','o')))

