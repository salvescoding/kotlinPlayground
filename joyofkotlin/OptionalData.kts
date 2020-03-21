package joyofkotlin

fun max(list: List<Int>): Option<Int> = Option(list.max())
fun getDefault(): Int = throw RuntimeException()

sealed class Option<out A> {

    abstract fun isEmpty(): Boolean

    abstract fun <B> flatMap(f: (A) -> Option<B>): Option<B>

    fun getOrElse(default: () -> @UnsafeVariance A): A = when(this) {
        is None -> default()
        is Some -> value
    }
    fun <B> map(f: (A) -> B): Option<B> = when(this) {
        is None -> None
        is Some -> Some(f(value))
    }

    fun orElse(default: () -> Option<@UnsafeVariance A>): Option<A> =
        map { this }.getOrElse(default)

    fun filter(p: (A) -> Boolean): Option<A> = when(this) {
        is None -> None
        is Some -> if (p(value)) this else None
    }

    internal object None: Option<Nothing>() {

        override fun <B> flatMap(f: (Nothing) -> Option<B>): Option<B> = None

        override fun isEmpty() = true

        override fun toString(): String = "None"

        override fun equals(other: Any?): Boolean =
            other === None

        override fun hashCode(): Int = 0
    }

    internal data class Some<out A>(internal val value: A) : Option<A>() {

        override fun <B> flatMap(f: (A) -> Option<B>): Option<B> = f(value)

        override fun isEmpty() = false

    }

    companion object {

        operator fun <A> invoke(a: A? = null): Option<A>
                = when (a) {
            null -> None
            else -> Some(a)
        }
    }
}

println("List with numbers: ${max(listOf(3, 5, 7, 2, 1)).getOrElse(::getDefault)}")


val mapped = Option(4).map { it.toDouble() }
println(mapped)

val filter = mapped.filter { it == 2.9 }
println(filter.isEmpty())
