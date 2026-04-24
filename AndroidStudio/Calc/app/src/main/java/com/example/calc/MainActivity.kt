package com.example.calc

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView
    private var lastNumeric: Boolean = false
    private var stateError: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvDisplay = findViewById(R.id.tvDisplay)

        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        for (id in numberButtons) {
            findViewById<Button>(id).setOnClickListener {
                onDigit(it as Button)
            }
        }

        findViewById<Button>(R.id.btnDot).setOnClickListener {
            onDecimalPoint()
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            onClear()
        }

        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            onDelete()
        }

        val operatorButtons = listOf(
            R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide
        )

        for (id in operatorButtons) {
            findViewById<Button>(id).setOnClickListener {
                onOperator(it as Button)
            }
        }

        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            onEqual()
        }
    }

    private fun onDigit(button: Button) {
        if (stateError) {
            tvDisplay.text = button.text
            stateError = false
        } else {
            if (tvDisplay.text.toString() == "0") {
                tvDisplay.text = button.text
            } else {
                tvDisplay.append(button.text)
            }
        }
        lastNumeric = true
    }

    private fun onDecimalPoint() {
        if (lastNumeric && !stateError && !lastDot) {
            tvDisplay.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    private fun onOperator(button: Button) {
        if (lastNumeric && !stateError) {
            tvDisplay.append(button.text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun onClear() {
        tvDisplay.text = "0"
        lastNumeric = false
        stateError = false
        lastDot = false
    }

    private fun onDelete() {
        val text = tvDisplay.text.toString()
        if (text.isNotEmpty() && text != "0") {
            if (text.length == 1) {
                tvDisplay.text = "0"
            } else {
                tvDisplay.text = text.substring(0, text.length - 1)
            }
            
            val newText = tvDisplay.text.toString()
            lastNumeric = newText.last().isDigit()
            lastDot = newText.contains(".") && !newText.split("+", "-", "*", "/").last().contains(".")
            // Simplified dot check: check if the last part of the expression has a dot
            val parts = newText.split("+", "-", "*", "/")
            lastDot = parts.last().contains(".")
        }
    }

    private fun onEqual() {
        if (lastNumeric && !stateError) {
            val text = tvDisplay.text.toString()
            try {
                val result = evaluate(text)
                tvDisplay.text = result.toString()
                lastDot = tvDisplay.text.contains(".")
            } catch (e: Exception) {
                tvDisplay.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }

    private fun evaluate(expression: String): Double {
        // This is a very basic evaluator for a single operation.
        // For a full expression, we'd need a more complex parser.
        // Let's support simple A operator B for now as a "basic" calculator.
        
        val operators = listOf("+", "*", "/", "-")
        var operator = ""
        for (op in operators) {
            if (expression.contains(op)) {
                // Handle minus sign at start
                if (op == "-" && expression.startsWith("-")) {
                    val sub = expression.substring(1)
                    if (!sub.contains("-") && !sub.contains("+") && !sub.contains("*") && !sub.contains("/")) {
                        return expression.toDouble()
                    }
                }
                operator = op
                break
            }
        }

        if (operator.isEmpty()) return expression.toDouble()

        val split = expression.split(operator)
        if (split.size < 2) return expression.toDouble()
        
        val arg1 = split[0].toDouble()
        val arg2 = split[1].toDouble()

        return when (operator) {
            "+" -> arg1 + arg2
            "-" -> arg1 - arg2
            "*" -> arg1 * arg2
            "/" -> arg1 / arg2
            else -> 0.0
        }
    }
}