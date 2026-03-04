package dev.csse.jcisne23.gurucalc.ui

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import dev.csse.jcisne23.gurucalc.ui.theme.BlueButton
import dev.csse.jcisne23.gurucalc.ui.theme.GreyButton
import dev.csse.jcisne23.gurucalc.ui.theme.OrangeButton
import java.util.EmptyStackException
import java.util.Stack
import kotlin.math.pow


data class HistoryItem(
    val expression: TextFieldValue,
    val result: String
)

class CalcViewModel : ViewModel() {
    var input = mutableStateOf(TextFieldValue(""))
    private set
    var output = mutableStateOf("")
    var history = mutableStateListOf<HistoryItem>()

    val options = mapOf(
        "Length" to listOf("Meter", "Kilometer", "Centimeter", "Feet", "Inches", "Mile"),
        "$$$" to listOf("United States Dollar", "Euro", "Pound", "Yen"),
        "Temp" to listOf("Celsius", "Fahrenheit", "Kelvin"),
        "Weight" to listOf("Kilogram", "Gram", "Milligram", "Pound", "Ounce")
    )

    private fun precedence(op: Char): Int {
        return when(op) {
            '+', '-' -> 1
            '*', '/' -> 2
            '^' -> 3
            else -> 0
        }
    }

    private fun applyOperation(nums: Stack<Double>, op: Char) {
        if (nums.size < 2) return

        val b = nums.pop()
        val a = nums.pop()

        when (op) {
            '+' -> nums.push(a + b)
            '-' -> nums.push(a - b)
            '*' -> nums.push(a * b)
            '/' -> nums.push(a / b)
            '^' -> nums.push(a.pow(b))
        }
    }

    private fun hasBalancedParentheses(s: String): Boolean {
        var count = 0
        for (c in s) {
            if (c == '(') count++
            if (c == ')') count--
            if (count < 0) return false
        }
        return count == 0
    }

    private fun findParantheses(s: String): IntArray {
        if (!hasBalancedParentheses(s)) {
            output.value = "Error"
            return intArrayOf()
        }
        var count_1 = 0
        var count_2 = 0
        for (i in s.indices) {
            if (s[i] == '(') {
                count_1 = i
            }
            if (s[i] == ')') {
                count_2 = i
            }
        }
        return intArrayOf(count_1, count_2)
    }

//    This follows an algorithm I found online:
//    https://labuladong.online/en/algo/data-structure/implement-calculator/
    fun calculate() {
    val nums = Stack<Double>()
    val ops = Stack<Char>()
    val s = input.value.text.replace("÷", "/")
                        .replace("x", "*")
                        .replace("²", "^2")
    Log.d("CALC","INPUT RAW: ${s}")
    if (!hasBalancedParentheses(s)) {
        output.value = "Error"
        return
    }

    var i = 0
    while (i < s.length) {
        val c = s[i]
        if (c.isDigit() || c == '.') {
            val sb = StringBuilder()
            var dotCount = 0

            while (i < s.length && (s[i].isDigit() || s[i] == '.')) {
                if (s[i] == '.') {
                    dotCount++
                    if (dotCount > 1) {
                        output.value = "Error"
                        return
                    }

                }
                sb.append(s[i])
                i++
            }

            nums.push(sb.toString().toDouble())
            continue
        }
        when (c) {
            '(' -> {
                ops.push(c)
            }
            ')' -> {
                while (ops.isNotEmpty() && ops.peek() != '(') {
                    applyOperation(nums, ops.pop())
                }
                if (ops.isNotEmpty()) {
                    ops.pop() // remove '('
                }
            }
            '+', '-', '*', '/', '^' -> {
                while (ops.isNotEmpty() &&
                    ops.peek() != '(' &&
                    precedence(ops.peek()) >= precedence(c)
                ) {
                    applyOperation(nums, ops.pop())
                }
                ops.push(c)
            }
        }
        i++
    }
    Log.d("CALC","NUMS: ${nums}")
    Log.d("CALC", "OPS: ${ops}")

    // Final resolve
    while (ops.isNotEmpty()) {
        applyOperation(nums, ops.pop())
    }
        try {
            val result = nums.pop().toString()
            output.value = result
        }
        catch (err: EmptyStackException) {
            Log.d("CALC", err.toString())
        }
    }

    fun insert(text: String) {
        val current = input.value
        val cursor = current.selection.start

        val newText = current.text.substring(0, cursor) +
                text +
                current.text.substring(cursor)

        input.value = TextFieldValue(
            text = newText,
            selection = TextRange(cursor + text.length)
        )
    }

    fun increment(text: String) {
        insert(text)
        calculate()
    }

    fun evaluate() {
        calculate()
        if (input.value.text.isNotBlank() && output.value.isNotBlank()) {
            history.add(
                HistoryItem(
                    expression = input.value,
                    result = output.value
                )
            )
        }
        reset()
    }

    fun reset() {
        input.value = TextFieldValue(
            text = "",
            selection = TextRange(0)
        )
        output.value = ""
    }

    fun clearHistory() {
        history.clear()
    }

    fun SpecOperation(text: String) {
        if (text == "π") {
            increment("3.14157")
        }
        else if (text == "√a") {
            increment(text[0].toString())
        }
        else if (text == "a²") {
            val s = input.value.text
            if (s.isNotEmpty()) {
                var num = s[s.length - 1]
                if (num == ')') {
                    val array = findParantheses(s)

                }
                else {
                    val newText = s.dropLast(1)

                    input.value = TextFieldValue(
                        text = newText,
                        selection = TextRange(input.value.text.length - 1)
                    )
                }

                insert("(${num})²")
                calculate()
            }
            else {
                insert("()²")
            }
        }
        else {
            increment(text)
        }
    }

    fun deleteAtCursor() {
        val current = input.value
        val cursor = current.selection.start

        if (cursor == 0) return

        val newText = current.text.removeRange(cursor - 1, cursor)

        input.value = TextFieldValue(
            text = newText,
            selection = TextRange(cursor - 1)
        )
        calculate()
    }

    fun moveCursorLeft() {
        val current = input.value
        val newPos = (current.selection.start - 1)
            .coerceAtLeast(0)

        input.value = current.copy(
            selection = TextRange(newPos)
        )
    }

    fun moveCursorRight() {
        val current = input.value
        val newPos = (current.selection.start + 1)
            .coerceAtMost(current.text.length)

        input.value = current.copy(
            selection = TextRange(newPos)
        )
    }

    @Composable
    fun NumButton(
        text : String,
        modifier: Modifier = Modifier
    ) {
        Button(
            modifier = modifier.height(64.dp),
            shape = RoundedCornerShape(20.dp),
            onClick = {increment(text)},
                    colors = ButtonDefaults.buttonColors(
                    containerColor = GreyButton,
            contentColor = Color.Black
        )
        ) {
            Text(text = text,
                fontSize = 30.sp)
        }
    }

    @Composable
    fun OppButton(
        text: String,
        modifier: Modifier = Modifier
    ) {
        Button(
            modifier = modifier.height(64.dp),
            shape = RoundedCornerShape(20.dp),
            onClick = {
                if (text != "↵") {
                    increment(text)
                }
                else {
                    evaluate()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = OrangeButton,
                contentColor = Color.White
            )
        ) {
            Text(
                text = text,
                fontSize = 30.sp
            )
        }
    }

    fun sizes(text: String): TextUnit {
        if (text == "log" || text == "cos" || text == "sin") {
            return 20.sp
        }
        return 24.sp
    }

    @Composable
    fun SpecButton(
        text: String,
        modifier: Modifier = Modifier
    ) {
        Button(
            modifier = modifier
                .height(64.dp),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(20.dp),
            onClick = {SpecOperation(text)},
            colors = ButtonDefaults.buttonColors(
                containerColor = BlueButton,
                contentColor = Color.Black
            )
        ) {
            Text(
                text = text,
                maxLines = 1,
                fontSize = sizes(text),
                softWrap = false
            )
        }
    }

    @Composable
    fun convertOptions(s: String): List<String> {

        var options = listOf("Meter", "Kilometer", "Centimeter", "Feet", "Inches", "Mile")
        if (s == "Length") {
            return options
        }
        else {
            options = listOf("Peanut")
            return options
        }
    }

}