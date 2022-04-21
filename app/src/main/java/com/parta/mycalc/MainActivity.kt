package com.parta.mycalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    //flags
    var lastNumeric: Boolean = false
    // Represent that current state is in error or not
    var stateError: Boolean = false
    // If true, do not allow to add another DOT
    private var lastDot: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // beauty of kotlin
        // Initialize Buttons without creating objects
        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
        btn4.setOnClickListener(this)
        btn5.setOnClickListener(this)
        btn6.setOnClickListener(this)
        btn7.setOnClickListener(this)
        btn8.setOnClickListener(this)
        btn9.setOnClickListener(this)
        btnZero.setOnClickListener(this)
        btnDot.setOnClickListener(this)
        btnEquals.setOnClickListener(this)
        btnDiv.setOnClickListener(this)
        btnAdd.setOnClickListener(this)
        btnSub.setOnClickListener(this)
        btnTimes.setOnClickListener(this)
        btnClear.setOnClickListener(this)
        btnOpenBracket.setOnClickListener(this)
        btnCloseBracket.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0){
            btnOpenBracket ->{
                input.append("(")
            }

            btnCloseBracket ->{
                input.append(")")
            }
            btnSub -> {
                if (lastNumeric && !stateError) {
                    input.append("-") // accessing input without object
                    lastNumeric = false
                    lastDot = false
                }
            }

            btnAdd -> {
                if (lastNumeric && !stateError) {
                    input.append("+")
                    lastNumeric = false
                    lastDot = false    // Reset the DOT flag
                }
            }

            btnDiv -> {
                if (lastNumeric && !stateError) {
                    input.append("/")
                    lastNumeric = false
                    lastDot = false    // Reset the DOT flag
                }
            }

            btnTimes -> {
                if (lastNumeric && !stateError) {
                    input.append("x")
                    lastNumeric = false
                    lastDot = false    // Reset the DOT flag
                }
            }

            btnClear -> {
                input.text.clear()
                lastNumeric = false
                stateError = false
                lastDot = false
            }
            btnDot -> {
                if (lastNumeric && !stateError && !lastDot) {
                    input.append(".")
                    lastNumeric = false
                    lastDot = true
                }
            }
            btnZero -> {
                onDigit("0")
            }
            btn1 -> {
                onDigit("1")
            }
            btn2 -> {
                onDigit("2")
            }
            btn3 -> {
                onDigit("3")
            }
            btn4 -> {
                onDigit("4")
            }
            btn5 -> {
                onDigit("5")
            }
            btn6 -> {
                onDigit("6")
            }
            btn7 -> {
                onDigit("7")
            }
            btn8 -> {
                onDigit("8")
            }
            btn9 -> {
                onDigit("9")
            }
            btnEquals -> {
                // If the current state is error, nothing to do.
                // If the last input is a number only, solution can be found.
                if (lastNumeric && !stateError) {
                    // Create an Expression (A class from exp4j library)
                    Log.i("MainActivity",input.text.toString())
                    val expBuilder = ExperssionEvaluator(input.text.toString(), applicationContext)
                    try {
                        input.setText(expBuilder.evaluate().toString())
                        lastDot = true // Result contains a dot
                    } catch (ex: ArithmeticException) {
                        // Display an error message
                        input.setText(getString(R.string.error))
                        stateError = true
                        lastNumeric = false
                    }
                }
            }
        }
    }

    private fun onDigit(str: String){
        if (stateError) {
            input.setText(str)
            stateError = false
        } else {
            input.append(str)
        }
        // Set the flag
        lastNumeric = true
    }


}