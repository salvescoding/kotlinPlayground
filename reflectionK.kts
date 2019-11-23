import kotlin.reflect.KClass
import kotlin.reflect.jvm.isAccessible

/**
 * KClass belongs to the Reflection API
 */

class Person(private val name: String, private val age: Int ) {
    private fun printNiceFormat() : String = "Person $name with age $age"
}

val person = Person("Sergio", 36)
val kClass: KClass<Person> = person.javaClass.kotlin

println("KClass name")
println(kClass.simpleName)


println("All members of class Person")
println(kClass.members.forEach(::println))

val bla = kClass.members.filter { !it.isAccessible }.filter { it.name == "printNiceFormat" }
println(bla.first().call(""))

println("Supertypes of class Person")
kClass.supertypes.forEach { println(it.toString()) }


/**
 *  KFunction from the Reflection API
 */

fun printValue(x: Int) = println(x)

val kFunction = ::printValue
println(kFunction.name)


println("KFunction invoking the the method")
kFunction.invoke(44)
