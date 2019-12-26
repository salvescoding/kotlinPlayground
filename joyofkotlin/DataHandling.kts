package joyofkotlin

import java.lang.IllegalStateException


@Suppress("UNCHECKED_CAST")
sealed class List<A> {

    abstract fun isEmpty(): Boolean

    fun cons(a: A) : List<A> = Cons(a, this)

    fun setHead(a: A) :List<A> = when(this) {
        is Nil -> throw IllegalStateException("Cannot set head on empty list")
        is Cons -> Cons(a, this.tail)
    }

    fun drop(n: Int): List<A> {
        tailrec fun drop(n: Int, list: List<A>): List<A> {
            return if (n <= 0) list else when (list) {
                is Cons -> drop(n - 1, list.tail)
                is Nil -> list
            }
        }
        return drop(n, this)
    }

    private object Nil : List<Nothing>() {
        override fun isEmpty() = true
        override fun toString(): String = "[NIL]"
    }

    private class Cons<A>(internal val head: A, internal val tail: List<A>) : List<A>() {

        override fun isEmpty() = false

        override fun toString(): String = "[${toString("", this)}NIL]"

        private tailrec fun toString(acc: String, list: List<A>): String =
            when (list) {
                is Nil -> acc
                is Cons -> toString("$acc${list.head}, ", list.tail)
            }

    }

    companion object {

        operator fun <A> invoke(vararg az: A): List<A> =
        az.foldRight(Nil as List<A>) { a: A, list: List<A> ->
            Cons(a, list)
        }
    }
}

val funkyList = List(1,3,4,5)

val dropped2Values = funkyList.drop(2)

println(dropped2Values)



