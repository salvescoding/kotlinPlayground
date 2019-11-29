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

val composedFuntionThree = composeVal(square)(triple)

println("Function composition with first triple the value 3 and then square it")
println(composedFuntionThree(3))

println("Same function composition applied to a list of numbers 3,25,12,43")
randomNumbers.map(composedFuntionThree).forEach(::println)


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

val composedFunctionFour = higherCompose<Int, Int, Int>()(square)(triple)

println("Printing HOFP with composed function as a value 14")
println(composedFunctionFour(14))



