package com.parta.mycalc

import android.content.Context
import android.widget.Toast
import java.util.*

class ExperssionEvaluator(private val exp: String, private val context: Context) {

    fun evaluate(): Int {
        val operands: Stack<Int> = Stack() //Operand stack
        val operations: Stack<Char> = Stack() //Operator stack
        var i = 0
        while (i < exp.length) {
            var c = exp[i]
            if (Character.isDigit(c)) //check if it is number
            {
                //Entry is Digit, and it could be greater than a one-digit number
                var num = 0
                while (Character.isDigit(c)) {
                    num = num * 10 + (c - '0')
                    i++
                    c = if (i < exp.length) {
                        exp[i]
                    } else {
                        break
                    }
                }
                i--
                operands.push(num)
            } else if (c == '(') {
                operations.push(c) //push character to operators stack
            } else if (c == ')' && operations.peek() != null) {
                while (operations.peek() != '(') {
                    val op = operations.pop()
                    val b = operands.pop()
                    val a = operands.pop()
                    operands.push(performOperation(a, b, op))
                }
                operations.pop()
            } else if (isOperator(c)) {
                while (!operations.isEmpty() && precedence(c) <= precedence(operations.peek().toChar())) {
                    val op = operations.pop()
                    val b = operands.pop()
                    val a = operands.pop()
                    operands.push(performOperation(a, b, op))
                }
                operations.push(c) //push the current operator to stack
            }
            i++
        }
        while (!operations.isEmpty()) {
            val op = operations.pop()
            val b = operands.pop()
            val a = operands.pop()
            operands.push(performOperation(a, b, op))
        }
        return operands.pop()
    }

    private fun precedence(c: Char): Int {
        when (c) {
            '+', '-' -> return 1
            'x', '/' -> return 2
        }
        return -1
    }

    private fun performOperation(a: Int,b: Int,op: Char): Int {
        when (op) {
            '+' -> return a + b
            '-' -> return a - b
            'x' -> return a * b
            '/' -> return if(b == 0) {
                Toast.makeText(context, "Division by zero", Toast.LENGTH_LONG).show()
                 0
            } else {
                 a / b
            }
        }
        return 0
    }

    private fun isOperator(c: Char): Boolean {
        return c == '+' || c == '-' || c == '/' || c == 'x'
    }
}