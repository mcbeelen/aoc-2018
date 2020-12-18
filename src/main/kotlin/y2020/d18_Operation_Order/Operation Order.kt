package y2020.d18_Operation_Order

import y2020.d18_Operation_Order.Operator.*
import java.lang.StringBuilder
import java.util.*

fun evaluateWithoutPrecedence(expression: String): Long {
    val parser = ExpressionParser()
    return evaluate(expression, parser)
}

fun evaluateWithPrecedence(expression: String): Long {
    val parser = ExpressionParser(true)
    return evaluate(expression, parser)
}
private fun evaluate(expression: String, parser: ExpressionParser): Long {
    expression.forEach {
        when {
            it in '0'..'9' -> parser.appendDigit(it)
            it == '+' -> parser.appendOperator(PLUS)
            it == '*' -> parser.appendOperator(TIMES)
            it == '(' -> parser.startNesting()
            it == ')' -> parser.endNesting()
        }
    }
    parser.appendEnd()

    return parser.output()!!
}

class ExpressionParser(val considerPrecedence : Boolean = false) {
    val values = Stack<Long>()
    val operators = Stack<Operator>()

    private var valueBuilder = StringBuilder()

    fun appendDigit(it: Char) {
        valueBuilder.append(it)
    }

    fun appendOperator(operator: Operator) {
        buildValue()
        if (considerPrecedence) {
            if (! operators.isEmpty() && operators.peek() == PLUS) {
                performOperation()
            }
        } else {
            if (!operators.empty()) {
                performOperation()
            }
        }
        operators.push(operator)

    }

    private fun performOperation() {
        val operator = operators.pop()
        if (operator != NOOP ) {
            values.push(operator.perform(values.pop(), values.pop()))
        }
    }

    private fun buildValue() {
        if (valueBuilder.isNotEmpty()) {
            values.push(valueBuilder.toString().toLong())
            valueBuilder = StringBuilder()
        }
    }

    fun appendEnd() {
        buildValue()
        while (operators.isNotEmpty()) {
            performOperation()
        }
    }

    fun output() = values.pop()
    fun startNesting() {
        operators.push(NOOP)
    }

    fun endNesting() {
        buildValue()
        if (considerPrecedence) {
            while (operators.isNotEmpty() && operators.peek() != NOOP) {
                performOperation()
            }
            operators.pop()
        } else {
            performOperation()
        }

    }
}

enum class Operator(
    private val precedenceIndex: Int,
    private val action: (first: Long, second: Long) -> Long) {

    PLUS(1, action = { first, second -> first + second }),
    TIMES(0, action = { first, second -> first * second }),
    NOOP(2, action = {first, second -> throw java.lang.IllegalStateException() });


    fun perform(a: Long, b: Long)= action(a, b)
}


fun main() {
    println(y2020d18input.lines().map { evaluateWithoutPrecedence(it) }.sum())
    println(y2020d18input.lines().map { evaluateWithPrecedence(it) }.sum())
}

