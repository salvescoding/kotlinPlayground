// Common example to deserialize a Json string into an object using delegation
// to the map in this case

val jsonString = mapOf<String, String>(
    "name" to "Sergio",
    "age" to "37",
    "job" to "Software Engineer"
)

val realJson  =""" [
    |{
    |   "name": "Bla",
    |   "age": "36"
    |}
    |]""".trimMargin()

class Person {
    private val _attributes = hashMapOf<String, String>()
    val name : String by _attributes
    val age : String by _attributes
    val job : String by _attributes

    fun setAttributes(attrName: String, value: String) {
        _attributes[attrName] = value
    }

}

val person = Person()
// Iterate through the "JSON" map and create the person with all the attributes necessary
for ((attrName, value) in jsonString) {
    person.setAttributes(attrName, value)
}


println("Print person name:")
println(person.name)
println("Print person age :")
println(person.age)
println("Print person job:")
println(person.job)
