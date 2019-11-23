package joyofkotlin

val randomNumbers = listOf(3,25,12,43)

/**
 * Functions as a value
 */
val square: (Int) -> Int = { it * it }
val triple: (Int) -> Int = { it * 3}

/**
 * Function composition that accepts two functions as
 * arguments(Receive an Argument Type A and returns the same Type)
 * The result of the function is another Function that Receives an A type and returns the same
 * Inside the function we apply f on the result of g(Int)
 */
fun <T, U, V> compose(f: (U) -> V, g: (T) -> U) : (T) -> V = { f(g(it)) }

val composedFunction = compose(square, triple)

// Apply function composition to a list of numbe
println("The result of the composed function applied to a list of Ints")
println(randomNumbers.map(composedFunction))
println("Composed function applied to a single element")
println(composedFunction(23))

