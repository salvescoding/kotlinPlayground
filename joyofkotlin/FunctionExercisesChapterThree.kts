package joyofkotlin

val randomNumbers = listOf(3,25,12,43)

/**
 * Functions as a value
 */
val square: (Int) -> Int = { it * it }
val triple: (Int) -> Int = { it * 3}

val even: (Int) -> Boolean = { it % 2 == 0 }
val booleanMapper: (Boolean) -> Int = { if(it) 1 else 0 }

/**
 * Function composition that accepts two functions as
 * arguments(Receive an Argument Type A and returns the same Type)
 * The result of the function is another Function that Receives an A type and returns the same
 * Inside the function we apply f on the result of g(Int)
 */
fun <T, U, V> compose(f: (U) -> V, g: (T) -> U) : (T) -> V = { f(g(it)) }

val composedFunction = compose(square, triple)

val composedFunctionTwo = compose(booleanMapper, even)

// Apply function composition to a list of numbers
println("The result of the composed function applied to a list of Ints")
println(randomNumbers.map(composedFunction))
println("Composed function applied to a single element")
println(composedFunction(23))

println("The result of function composed mapping list of numbers to 0(False) and 1(True) even numbers")
println(randomNumbers.map(composedFunctionTwo))


/**
 * Curried function with function as a value
 */

val add: (Int) -> (Int) -> Int = { a -> { b -> a + b} }

println("Printing a curried function that adds two numbers 23 + 4")
println(add(23)(4))


/**
 * Function as values
 */

val composeVal: ((Int) -> Int) -> ((Int) -> Int) -> (Int) -> Int =
    { x -> { y -> { z -> x(y(z)) } } }

// The same as the composeVal function but defined different the signature
val composeValNew = {
    x: (Int) -> Int -> {
        y: (Int) -> Int -> {
            z: Int -> x(y(z))
        }
    }
}
val composedFuntionThree = composeVal(square)

randomNumbers.map(composedFuntionThree(triple))


println("Function composition with first triple the value 3 and then square it")
println(composedFuntionThree(triple))

println("Same function composition applied to a list of numbers 3,25,12,43")
randomNumbers.map(composedFuntionThree(triple)).forEach(::println)


/**
 * Function composition as values instead of functions
 * Genery HOFP polymorphic
 */
fun <T, U, V> higherCompose() =
    { f: (U) -> V ->
        { g: (T) -> U ->
            { x: T -> f(g(x)) }
        }
    }
println("Printing composed High order function for methods square and triple")

val composedFunctionFour = higherCompose<Int, Int, Int>()(square)

println("Printing HOFP with composed function as a value 14")
println(composedFunctionFour(triple)(14))


/**
 * The reverse of the the previous HOF where we apply g(f(x))
 */

fun <T, U, V> higherAndThen() =
    { f: (T) -> U ->
        { g: (U) -> V ->
            { x: T -> g(f(x)) }
        }
    }

val composedApplyFirstFunctionFirst = higherAndThen<Int, Int, Int>()(square)(triple)

println("Printing high order function that applies first function first on the argument passed, 14 in this case")
println(composedApplyFirstFunctionFirst(14))


/**
 * Passing anonymous functions ( lambdas ) to HOF
 */
val cosValue: Double = compose({ x: Double -> Math.PI / 2 - x }, Math::sin)(2.0)

/**
 * Partially applied curried functions
 */

// Apply the function partially to the first argument
fun <A, B, C> partialA(a: A, f: (A) -> (B) -> C): (B) -> C =  f(a)

val applyPartiallyFirstArgument = partialA<Int, Double, String>(3) { a -> { b -> "${a.toDouble()} * $b" } }
println(applyPartiallyFirstArgument(2.0))

// Apply the function partially to the second argument

fun <A, B, C> partialB(b: B, f: (A) -> (B) -> C): (A) -> C = { a: A  -> f(a)(b)  }

val applyPartiallySecondArgument = partialB<Int, Double, Double>(10.0) { a -> { b -> a.times(b) } }
println(applyPartiallySecondArgument(5))


// Curried function that takes 4 parameters and returns a string

fun <A, B, C, D> curried(): (A) -> (B) -> (C) -> (D) -> String =
    { a ->
        { b ->
            { c ->
                { d ->
                    "$a $b $c $d"
                }
            }
        }
    }

val curriedString = curried<Int, Int, Int, Int>()
val curriedName = curried<String, String, String, String>()
println(curriedString(1)(2)(3)(4))
println(curriedName("Sergio")("Andre")("Alves")("Bu"))

fun <A, B, C> curriedAToC(f: (A, B) -> C): (A) -> (B) -> C =
    { a -> {
        b -> f(a, b)
    } }

val curriedAC = curriedAToC<Int, Int, Int>() { a, b -> a * b }
println(curriedAC(10)(10))

println("Sum by double list of random numbers with apply partially second argument")
println(randomNumbers.sumByDouble(applyPartiallySecondArgument))

// swapping arguments
fun <T, U, V> swapArgs(f: (T) -> (U) -> V): (U) -> (T) -> (V) =
    { u -> { t -> f(t)(u) } }



val sumTwo: (Int) -> Int = { a -> a  + 2 }
val multply: (Int) -> Int = { a -> a * 10}

val composedSumMultiply = composeValNew(sumTwo)(multply)

val composeWithFun = compose(sumTwo, multply)

println(composedSumMultiply(10))




