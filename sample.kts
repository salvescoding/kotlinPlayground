import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import kotlin.properties.Delegates
import kotlin.reflect.KProperty


println("Hello Kotlin")

val extraNumbers: IntArray = intArrayOf(1, 3, 5, 1)

// Raw strings, you no longer need to use \ escape characters
fun greet(name: String, msg: String = "Enjoy"){
println("""This is a message for $name
   |You have to keep it going, this is cool
   |$msg Kotlin :)
""".trimMargin())
}

// Varags -> Can receive one or muktiple Type values and and transform it into an array
fun max(vararg numbers: Int) = numbers.reduce{ max, e -> if(max > e) max else e }
println("Calls the function max with a list of numbers plus an array of int using varags")
println(    max(2,4,*extraNumbers ,23,1,4,9))

// Function printFullName is an expression that takes to arguments and returns a String using string interpolation
fun printFullName(first: String, last: String) : String = "$first $last"

// Iterates through a range and prints the numbers
fun printNumbers() { for(x in 1..10) { println(x) } }

// For loop with index
for(x in extraNumbers.indices) { print("index: $x has value: ${extraNumbers[x]}") }



// When (Old Switch in Java) functionality
fun process(input: Any) {
    when(input) {
        1 -> println("You got $input")
        7, 8 -> println("You got 7 or 8")
        in 14..19 -> println("teen")
        is String -> println("You are a String :) with length ${input.length}")
        else -> println("Whatever")
    }
}



class Robot {
    val right
        get() = "Right"
    val left
        get() = "Left"

    infix fun turns(direction: String) {
        println("Turning $direction")
    }

}

val robot: Robot = Robot()

robot.run {
    turns(right)
    turns(left)
}

with(robot) {
    turns(right)
    turns(left)
}

inline fun operate(func: Robot.() -> Unit) {
    println("Called operate function")
    func(robot)
    println("After lambda function executes")
}

operate {
    println("Inside lambda function")
    robot turns right
    robot turns left
}

data class TestValue(val x: Int, val y: Int)

val current = TestValue(5, 5)
val refCurrent = TestValue(5, 5)

println("Are value object($current and $refCurrent) the same object?: ${refCurrent === current}")
println("Are value object($current and $refCurrent) with the same value?: ${refCurrent == current}")




// Collections Iterators and streams
val numList: List<Int> = listOf(1,2,3,4,5)
// Lambda that is assigned to a variable (multiple every element by 2)
val doubleTheNumbers = { x: Int -> x * 2 }

val times7 = numList.map { it * 7 }
print("List that is mapped to be multiplied by 7")
times7.forEach(::print)
println()
print("List of numbers that have been doubled and are even: ")
numList.filter { it and 1 == 0 } // Best way to check if number is even :)
    .map(doubleTheNumbers)
    .forEach(::print)
println()
println("---------------------------------------------------")









