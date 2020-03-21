package joyofkotlin

import java.util.*
import java.util.concurrent.ConcurrentHashMap

class Memoizer<T, U> private constructor() {

    private val cache = ConcurrentHashMap<T, U>()

    private fun doMemoize(function: (T) -> U):  (T) -> U =
        { input ->
            cache.computeIfAbsent(input) {
                function(it)
            }
        }

    companion object {
        fun <T, U> memoize(function: (T) -> U): (T) -> U =
        Memoizer<T, U>().doMemoize(function)
    }
}


fun longComputation(number: Int): Int {
    Thread.sleep(1000)
    return number
}


val startTime1 = System.currentTimeMillis()
val result1 = longComputation(43)
val time1 = System.currentTimeMillis() - startTime1
val memoizedLongComputation =
    Memoizer.memoize(::longComputation)
val startTime2 = System.currentTimeMillis()
val result2 = memoizedLongComputation(43)
val time2 = System.currentTimeMillis() - startTime2
val startTime3 = System.currentTimeMillis()
val result3 = memoizedLongComputation(43)
val time3 = System.currentTimeMillis() - startTime3

println("Call to nonmemoized function: result = $result1, time = $time1")
println("Call to nonmemoized function: result = $result2, time = $time2")
println("Call to nonmemoized function: result = $result3, time = $time3")

//data class Tuple4<T, U, V, W>(val first: T,
//                              val second: U,
//                              val third: V,
//                              val fourth: W)
//
//val ft = { (a, b, c, d): Tuple4<Int, Int, Int, Int> ->
//    longComputation(a) + longComputation(b)
//    - longComputation(c) * longComputation(d) }
//
//val ftm = Memoizer.memoize(ft)
//
//val startTime11 = System.currentTimeMillis()
//val result11 = ftm(Tuple4(40, 41, 42, 43))
//val time11 = System.currentTimeMillis() - startTime11
//val startTime21 = System.currentTimeMillis()
//val result21 = ftm(Tuple4(40, 41, 42, 43))
//val time21 = System.currentTimeMillis() - startTime21
//println("First call to memoized function: result = $result11, time = $time11")
//println("Second call to memoized function: result = $result21, time = $time21")



