package com.example.calculadora_kotlin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculadora_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var operationPartOne = ""
    var operationPartTwo = ""
    var operator = ""
    var result = 0.0
    var isResultDisplayed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buttomclear.setOnClickListener {
            operationPartOne = ""
            operationPartTwo = ""
            binding.textoperation.setText("")
            operator = ""
            binding.textresult.setText("")
            isResultDisplayed = false
        }

        fun operationSum() {
            result = operationPartOne.toDouble() + operationPartTwo.toDouble()
            binding.textresult.setText(result.toString())
        }

        fun operationSub() {
            result = operationPartOne.toDouble() - operationPartTwo.toDouble()
            binding.textresult.setText(result.toString())
        }

        fun operationMulti() {
            result = operationPartOne.toDouble() * operationPartTwo.toDouble()
            binding.textresult.setText(result.toString())
        }

        fun operationDiv() {

            if (operationPartOne.toDouble() == 0.0 && operationPartTwo.toDouble() == 0.0) {
                binding.textresult.setText("Indeterminação (0/0)")
                return
            }
            if (operationPartTwo.toDouble() == 0.0) {
                binding.textresult.setText("impossível dividir por 0.")
                return
            }
            result = operationPartOne.toDouble() / operationPartTwo.toDouble()
            binding.textresult.setText(result.toString())
        }

        fun setNumber(num: String) {
            if (isResultDisplayed) {
                binding.textoperation.setText("")
                operationPartOne = ""
                operationPartTwo = ""
                operator = ""
                isResultDisplayed = false
            }
            binding.textoperation.append(num)
        }

        fun setComma() {
            val current = binding.textoperation.text.toString()
            if (!current.contains(".")) {
                binding.textoperation.append(".")
            }
        }

        fun backspace() {
            val current = binding.textoperation.text.toString()
            if (current.isNotEmpty()) {
                binding.textoperation.setText(current.dropLast(1))
            }
        }

        fun setNumbers() {
            binding.buttom0.setOnClickListener { setNumber("0") }
            binding.buttom1.setOnClickListener { setNumber("1") }
            binding.buttom2.setOnClickListener { setNumber("2") }
            binding.buttom3.setOnClickListener { setNumber("3") }
            binding.buttom4.setOnClickListener { setNumber("4") }
            binding.buttom5.setOnClickListener { setNumber("5") }
            binding.buttom6.setOnClickListener { setNumber("6") }
            binding.buttom7.setOnClickListener { setNumber("7") }
            binding.buttom8.setOnClickListener { setNumber("8") }
            binding.buttom9.setOnClickListener { setNumber("9") }
        }

        fun setOperator(op: String) {
            if (isResultDisplayed) {
                operationPartOne = binding.textresult.text.toString()
                operator = op
                binding.textoperation.setText("")
                binding.textresult.setText("")
                isResultDisplayed = false
            } else {
                operator = op
                operationPartOne = binding.textoperation.text.toString()
                binding.textoperation.setText("")
            }
        }

        fun setOperatorListeners() {
            binding.buttomsum.setOnClickListener { setOperator("+") }
            binding.buttomminus.setOnClickListener { setOperator("-") }
            binding.buttommulti.setOnClickListener { setOperator("x") }
            binding.buttomdivt.setOnClickListener { setOperator("/") }
        }

        fun result() {
            operationPartTwo = binding.textoperation.text.toString()
            if (operationPartOne.isEmpty() || operationPartTwo.isEmpty() || operator.isEmpty()) {
                return
            }

            when(operator) {
                "+" -> operationSum()
                "-" -> operationSub()
                "x" -> operationMulti()
                "/" -> operationDiv()
            }

            isResultDisplayed = true

            binding.textoperation.setText("$operationPartOne $operator $operationPartTwo")
        }

        setOperatorListeners()

        fun resultListener() {
            binding.buttomresult.setOnClickListener { result() }
        }

        setNumbers()
        resultListener()

        binding.buttombackspace.setOnClickListener { backspace() }
        binding.buttomvirgula.setOnClickListener { setComma() }
    }
}