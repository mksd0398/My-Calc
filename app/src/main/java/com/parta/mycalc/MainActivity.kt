package com.parta.mycalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity(), View.OnClickListener {


    //flags
    var lastNumeric: Boolean = false
    // Represent that current state is in error or not
    var stateError: Boolean = false
    // If true, do not allow to add another DOT
    var lastDot: Boolean = false

    //objects
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button
    private lateinit var btn5: Button
    private lateinit var btn6: Button
    private lateinit var btn7: Button
    private lateinit var btn8: Button
    private lateinit var btn9: Button
    private lateinit var btnZero: Button
    private lateinit var btnAdd: Button
    private lateinit var btnSub: Button
    private lateinit var btnTimes: Button
    private lateinit var btnDiv: Button
    private lateinit var btnEquals: Button
    private lateinit var btnDot: Button
    private lateinit var btnClear: Button
    private lateinit var btnOpenBrackets: Button
    private lateinit var btnCloseBrackets: Button
    private lateinit var input: EditText




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1= findViewById(R.id.btn_1)
        btn1.setOnClickListener(this)
        btn2 = findViewById(R.id.btn_2)
        btn2.setOnClickListener(this)
        btn3 = findViewById(R.id.btn_3)
        btn3.setOnClickListener(this)
        btn4 = findViewById(R.id.btn_4)
        btn4.setOnClickListener(this)
        btn5 = findViewById(R.id.btn_5)
        btn5.setOnClickListener(this)
        btn6 = findViewById(R.id.btn_6)
        btn6.setOnClickListener(this)
        btn7 = findViewById(R.id.btn_7)
        btn7.setOnClickListener(this)
        btn8 = findViewById(R.id.btn_8)
        btn8.setOnClickListener(this)
        btn9 = findViewById(R.id.btn_9)
        btn9.setOnClickListener(this)
        btnZero = findViewById(R.id.btn_zero)
        btnZero.setOnClickListener(this)
        btnDot = findViewById(R.id.btn_dot)
        btnDot.setOnClickListener(this)
        btnEquals = findViewById(R.id.btn_equals)
        btnEquals.setOnClickListener(this)
        btnDiv  = findViewById(R.id.btn_div)
        btnDiv.setOnClickListener(this)
        btnAdd = findViewById(R.id.btn_add)
        btnAdd.setOnClickListener(this)
        btnSub  = findViewById(R.id.btn_sub)
        btnSub.setOnClickListener(this)
        btnTimes  = findViewById(R.id.btn_times)
        btnTimes.setOnClickListener(this)
        btnClear = findViewById(R.id.btn_clear)
        btnClear.setOnClickListener(this)
        btnOpenBrackets = findViewById(R.id.btn_open_bracket)
        btnOpenBrackets.setOnClickListener(this)
        btnCloseBrackets = findViewById(R.id.btn_close_bracket)
        btnCloseBrackets.setOnClickListener(this)

        input = findViewById(R.id.input)

    }

    override fun onClick(p0: View?) {
        when(p0){
            btnOpenBrackets ->{
                input.append("(")
            }

            btnCloseBrackets ->{
                input.append(")")
            }
            btnSub -> {
                if (lastNumeric && !stateError) {
                    input.append("-")
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
                        input.setText( "Error")
                        stateError = true
                        lastNumeric = false
                    }
                }
            }
        }
    }

    private fun onDigit(str:String){
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