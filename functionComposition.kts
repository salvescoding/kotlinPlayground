import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class Employee(val name: String, age: Int, salary: Int) : PropertyChangeAware() {
    private val observer =  {
            prop: KProperty<*>, oldValue: Int, newValue: Int ->
        println("Person: ${name}")
        changeSupport.firePropertyChange(prop.name, oldValue, newValue)
    }

    var age: Int by Delegates.observable(age, observer)
    var salary: Int by Delegates.observable(salary, observer)
}


open class PropertyChangeAware {
    protected val changeSupport = PropertyChangeSupport(this)

    fun addPropertyChangeListener(listener: PropertyChangeListener) {
        changeSupport.addPropertyChangeListener(listener)
    }

    fun removePropertyChangeListener(listener: PropertyChangeListener) {
        changeSupport.removePropertyChangeListener(listener)
    }
}


val employee = Employee("Sergio", 36, 25000)
employee.addPropertyChangeListener(
    PropertyChangeListener { event ->
        println("Property ${event.propertyName} changed " +
                "from ${event.oldValue}, to ${event.newValue}"
        )
    }
)

employee.salary = 33000
employee.age = 37

typealias Func<A, B> = (A) -> B
infix fun <A, B, C> Func<B, C>.after(f: Func<A, B>): Func<A, C> = { x: A -> this(f(x)) }

val getEmployeeSalaryOf: Func<Employee, Int> = {  it.salary }
val calculateNetSalaryPerMonth: Func<Int, Double> = { salary ->  (salary - salary * 0.22) / 12  }
val printFormatSalary : Func<Double, Unit> = { println("Net Salary: $it ") }

printFormatSalary((calculateNetSalaryPerMonth after getEmployeeSalaryOf)(employee))



