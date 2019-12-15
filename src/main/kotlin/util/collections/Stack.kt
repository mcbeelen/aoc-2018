package util.collections

import java.util.*

class Stack<E> : Collection<E> {

    private val elements: MutableList<E> = mutableListOf()

    override fun contains(element: E) = elements.contains(element)

    override fun containsAll(elements: Collection<E>) = elements.containsAll(elements)

    override fun iterator() = elements.iterator()

    override fun isEmpty() = elements.isEmpty()

    override val size = elements.count()

    fun push(item: E) = elements.add(item)

    fun pushAll(elements: List<E>) {
        elements.forEach { push(it) }
    }

    fun pop() : E {
        if (elements.isEmpty()) {
            throw EmptyStackException()
        }
        val poppedElement = elements.last()
        elements.removeAt(elements.size -1)
        return poppedElement
    }

    fun peek() : E? = elements.lastOrNull()

    override fun toString(): String = "Size ${size}"

}
