package joyofkotlin

import joyofkotlin.DataHandling.List.Companion.drop
import joyofkotlin.DataHandling.List.Companion.dropWhile
import java.lang.IllegalStateException


@Suppress("UNCHECKED_CAST")
sealed class List<A> {

    abstract fun isEmpty(): Boolean

    fun addToStart(a: A) : List<A> = Cons(a, this)

    fun setNewHead(a: A) :List<A> = when(this) {
        is Nil -> throw IllegalStateException("Cannot set head on empty list")
        is Cons -> Cons(a, this.tail)
    }

    fun dropWhile(predicate: (A) -> Boolean): List<A> = dropWhile(this, predicate)

    fun drop(n: Int): List<A> = drop(n, this)

    fun init(): List<A> = reverse().drop(1).reverse()

    fun reverse(): List<A> = reverse(invoke(), this)

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

        private tailrec fun <A> drop(n: Int, list: List<A>): List<A> {
            return if (n <= 0) list else when (list) {
                is Cons -> drop(n - 1, list.tail)
                is Nil -> list
            }
        }

        private tailrec fun <A> dropWhile(list: List<A>, predicate: (A) -> Boolean): List<A> = when(list) {
                is Nil -> list
                is Cons -> if (predicate(list.head)) dropWhile(list.tail, predicate) else list
        }

        private tailrec fun <A> reverse(acc: List<A>,list: List<A>) : List<A> =
            when (list) {
                is Nil -> acc
                is Cons -> reverse(acc.addToStart(list.head), list.tail)
            }




    }
}

val funkyList = List(1,3,4,5)
println("Original List: $funkyList")

println("Dropping first x elements from the list with creating new elements: ")
println(funkyList.drop(2))

println("Set new head on the original list without creating new list")
println(funkyList.setNewHead(2))


println("Adding new element to start of the list without creating new list")
println(funkyList.addToStart(1))

//println("Printing orignal list")
//println(funkyList)

println("Drop while condition is met")
println(funkyList.dropWhile { it % 3 != 0})


println("Dropping the last element of the list")
println(funkyList.init())
