# My-Calc
This project is part a second program create a calculator

## expersion evaluator class

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


## Mainactivity


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

## activity_main.xml

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <EditText
        android:id="@+id/input"
        android:layout_width="370dp"
        android:layout_height="375dp"
        android:layout_marginStart="20dp"
        android:layout_marginEnd="20dp"
        android:ems="10"
        android:focusable="false"
        android:hint="0"
        android:inputType="textMultiLine"
        android:textAlignment="viewEnd"
        android:textSize="34sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/btn7"
        android:layout_width="0sp"
        android:layout_height="wrap_content"
        android:layout_marginStart="20dp"
        android:layout_marginEnd="5dp"
        android:text="7"
        app:layout_constraintEnd_toStartOf="@id/btn8"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/input" />

    <Button
        android:id="@+id/btn8"
        android:layout_width="0sp"
        android:layout_height="wrap_content"
        android:layout_marginStart="5dp"
        android:layout_marginEnd="5dp"
        android:text="8"
        app:layout_constraintEnd_toStartOf="@+id/btn9"
        app:layout_constraintStart_toEndOf="@+id/btn7"
        app:layout_constraintTop_toBottomOf="@+id/input" />

    <Button
        android:id="@+id/btn9"
        android:layout_width="0sp"
        android:layout_height="wrap_content"
        android:layout_marginStart="5dp"
        android:layout_marginEnd="5dp"
        android:text="9"
        app:layout_constraintEnd_toStartOf="@id/btnDiv"
        app:layout_constraintStart_toEndOf="@id/btn8"
        app:layout_constraintTop_toBottomOf="@+id/input" />

    <Button
        android:id="@+id/btnDiv"
        android:layout_width="0sp"
        android:layout_height="wrap_content"
        android:layout_marginStart="5dp"
        android:layout_marginEnd="20dp"
        android:text="/"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@id/btn9"
        app:layout_constraintTop_toBottomOf="@+id/input" />

    <Button
        android:id="@+id/btn4"
        android:layout_width="0sp"
        android:layout_height="wrap_content"
        android:layout_marginStart="20dp"
        android:layout_marginEnd="5dp"
        android:text="4"
        app:layout_constraintEnd_toStartOf="@id/btn5"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/btn7" />

    <Button
        android:id="@+id/btn5"
        android:layout_width="0sp"
        android:layout_height="wrap_content"
        android:layout_marginStart="5dp"
        android:layout_marginEnd="5dp"
        android:text="5"
        app:layout_constraintEnd_toStartOf="@+id/btn6"
        app:layout_constraintStart_toEndOf="@+id/btn4"
        app:layout_constraintTop_toBottomOf="@+id/btn8" />

    <Button
        android:id="@+id/btn6"
        android:layout_width="0sp"
        android:layout_height="wrap_content"
        android:layout_marginStart="5dp"
        android:layout_marginEnd="5dp"
        android:text="6"
        app:layout_constraintEnd_toStartOf="@id/btnTimes"
        app:layout_constraintStart_toEndOf="@id/btn5"
        app:layout_constraintTop_toBottomOf="@+id/btn9" />

    <Button
        android:id="@+id/btnTimes"
        android:layout_width="0sp"
        android:layout_height="wrap_content"
        android:layout_marginStart="5dp"
        android:layout_marginEnd="20dp"
        android:text="x"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@id/btn6"
        app:layout_constraintTop_toBottomOf="@+id/btnDiv" />


    <Button
        android:id="@+id/btn1"
        android:layout_width="0sp"
        android:layout_height="wrap_content"
        android:layout_marginStart="20dp"
        android:layout_marginEnd="5dp"
        android:text="1"
        app:layout_constraintEnd_toStartOf="@id/btn2"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/btn4" />

    <Button
        android:id="@+id/btn2"
        android:layout_width="0sp"
        android:layout_height="wrap_content"
        android:layout_marginStart="5dp"
        android:layout_marginEnd="5dp"
        android:text="2"
        app:layout_constraintEnd_toStartOf="@+id/btn3"
        app:layout_constraintStart_toEndOf="@+id/btn1"
        app:layout_constraintTop_toBottomOf="@+id/btn5" />

    <Button
        android:id="@+id/btn3"
        android:layout_width="0sp"
        android:layout_height="wrap_content"
        android:layout_marginStart="5dp"
        android:layout_marginEnd="5dp"
        android:text="3"
        app:layout_constraintEnd_toStartOf="@id/btnSub"
        app:layout_constraintStart_toEndOf="@id/btn2"
        app:layout_constraintTop_toBottomOf="@+id/btn6" />

    <Button
        android:id="@+id/btnSub"
        android:layout_width="0sp"
        android:layout_height="wrap_content"
        android:layout_marginStart="5dp"
        android:layout_marginEnd="20dp"
        android:text="-"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@id/btn3"
        app:layout_constraintTop_toBottomOf="@+id/btnTimes" />


    <Button
        android:id="@+id/btnDot"
        android:layout_width="0sp"
        android:layout_height="wrap_content"
        android:layout_marginStart="20dp"
        android:layout_marginEnd="5dp"
        android:text="."
        app:layout_constraintEnd_toStartOf="@id/btnZero"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/btn1" />

    <Button
        android:id="@+id/btnZero"
        android:layout_width="0sp"
        android:layout_height="wrap_content"
        android:layout_marginStart="5dp"
        android:layout_marginEnd="5dp"
        android:text="0"
        app:layout_constraintEnd_toStartOf="@+id/btnClear"
        app:layout_constraintStart_toEndOf="@+id/btnDot"
        app:layout_constraintTop_toBottomOf="@+id/btn2" />


    <Button
        android:id="@+id/btnAdd"
        android:layout_width="0sp"
        android:layout_height="wrap_content"
        android:layout_marginStart="5dp"
        android:layout_marginEnd="20dp"
        android:text="+"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@id/btnClear"
        app:layout_constraintTop_toBottomOf="@+id/btnSub" />


    <Button
        android:id="@+id/btnClear"
        android:layout_width="0sp"
        android:layout_height="wrap_content"
        android:layout_marginStart="5dp"
        android:layout_marginEnd="5dp"
        android:text="c"
        app:layout_constraintEnd_toStartOf="@id/btnAdd"
        app:layout_constraintStart_toEndOf="@id/btnZero"
        app:layout_constraintTop_toBottomOf="@+id/btn3"/>


    <Button
        android:id="@+id/btnOpenBracket"
        android:layout_width="0sp"
        android:layout_height="wrap_content"
        android:layout_marginStart="5dp"
        android:layout_marginEnd="5dp"
        android:text="("
        app:layout_constraintHorizontal_weight="1"
        app:layout_constraintEnd_toStartOf="@id/btnCloseBracket"
        app:layout_constraintStart_toEndOf="@id/btnEquals"
        app:layout_constraintTop_toBottomOf="@+id/btnClear" />


    <Button
        android:id="@+id/btnCloseBracket"
        android:layout_width="0sp"
        android:layout_height="wrap_content"
        android:layout_marginStart="5dp"
        android:layout_marginEnd="20dp"
        android:text=")"
        app:layout_constraintHorizontal_weight="1"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@id/btnOpenBracket"
        app:layout_constraintTop_toBottomOf="@+id/btnAdd" />


    <Button
        android:id="@+id/btnEquals"
        android:layout_width="0sp"
        android:layout_height="wrap_content"
        android:layout_marginStart="20dp"
        android:layout_marginEnd="5dp"
        android:text="="
        app:layout_constraintHorizontal_weight="2"
        app:layout_constraintEnd_toStartOf="@id/btnOpenBracket"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/btnDot" />
</androidx.constraintlayout.widget.ConstraintLayout>
