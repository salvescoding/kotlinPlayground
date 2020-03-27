package joyofkotlin

import java.util.*

fun or(a: Lazy<Boolean>, b: Lazy<Boolean>): Boolean = if (a()) true else b()


class Lazy<out A>(function: () -> A): () -> A {

    private val value: A by lazy(function)

    override operator fun invoke(): A = value

    companion object {
        val lift2: ((String) -> (String) -> String) -> (Lazy<String>) ->
            (Lazy<String>) -> Lazy<String> =
            { f ->
                { lz1 ->
                    { lz2 ->
                        Lazy { f(lz1())(lz2()) }
                    }
                }
            }
    }
}


val greetings: Lazy<String> = Lazy {
    println("Evaluating greetings")
    "Hello"
}
val name: Lazy<String> = Lazy {
    println("computing name")
    "Mickey"
}

val name2: Lazy<String> = Lazy {
    println("Evaluating name")
    "Donald"
}

val defaultMessage = Lazy {
    println("Evaluating default message")
    "No greetings when time is odd"
}


fun constructMessage(greetings: Lazy<String>, name: Lazy<String>) : Lazy<String> = Lazy { "${greetings()}, ${name()}" }
val lazConstructMessage: (Lazy<String>) -> (Lazy<String>) -> Lazy<String> = { a -> { b -> Lazy { "${a()}, ${b()}" } } }

val message = constructMessage(greetings, name)
val message2 = lazConstructMessage(greetings)(name2)
val condition = Random(System.currentTimeMillis()).nextInt() % 2 == 0

println(if (condition) message() else defaultMessage())
println(if (condition) message() else defaultMessage())
println(if (condition) message2() else defaultMessage())

