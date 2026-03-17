package dev.csse.jcisne23.gurucalc.ui

import android.content.res.Configuration
import android.icu.text.DecimalFormat
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
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
import kotlin.math.sqrt


data class HistoryItem(
    val expression: TextFieldValue,
    val result: String
)

class CalcViewModel : ViewModel() {
    var input = mutableStateOf(TextFieldValue(""))
    private set
    var output = mutableStateOf("")
    var history = mutableStateListOf<HistoryItem>()

    val units = listOf("Length", "$$$", "Temp", "Weight")
    val options = mapOf(
        "Length" to listOf("Meter", "Kilometer", "Centimeter", "Foot", "Inch", "Mile"),
        "$$$" to listOf("US Dollar", "Euro", "Pound", "Yen", "Yuan"),
        "Temp" to listOf("Celsius", "Fahrenheit", "Kelvin"),
        "Weight" to listOf("Kilogram", "Gram", "Milligram", "Pound", "Ounce", "Metric Tonne")
    )
    var baseUnits = 0.0

    fun onInputChange(newValue: TextFieldValue) {
        input.value = newValue
    }

    private fun precedence(op: Char): Int {
        return when(op) {
            '+', '-' -> 1
            '*', '/' -> 2
            '^' -> 3

            else -> 0
        }
    }

    private fun applyOperation(nums: Stack<Double>, op: Char) {
        if (op == '√' && nums.size < 2) {
            nums.push(sqrt(nums.pop()))
        }
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
    private fun calculate() {
    val nums = Stack<Double>()
    val ops = Stack<Char>()
    val s = input.value.text.replace("÷", "/")
                        .replace("x", "*")
                        .replace("²", "^2")
    Log.d("CalcViewModel","INPUT RAW: ${s}")
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
            '+', '-', '*', '/', '^', '√' -> {
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
    Log.d("CalcViewModel","NUMS: ${nums}")
    Log.d("CalcViewModel", "OPS: ${ops}")

    // Final resolve
    while (ops.isNotEmpty()) {
        applyOperation(nums, ops.pop())
    }
        try {
            val df = DecimalFormat("#.###")
            val result = nums.pop()
            baseUnits = result.toDouble()
            output.value = df.format(result)
        }
        catch (err: EmptyStackException) {
            Log.d("CalcViewModel", err.toString())
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

    private fun increment(text: String) {
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
        else if (text == "a²") {
            val s = input.value.text
            if (s.isNotEmpty()) {
                val num = s[s.length - 1]
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
        else if (text == "√a") {
            val s = input.value.text
            if (s.isNotEmpty()) {
                val num = s[s.length - 1]
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
                insert("√(${num})")
                calculate()
            }
            else {
                insert("√()")
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
        val configuration = LocalConfiguration.current
        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Button(
            modifier = if (isLandscape) modifier.width(32.dp) else modifier.width(64.dp).height(64.dp),
            shape = RoundedCornerShape(20.dp),
            onClick = { increment(text) },
            colors = ButtonDefaults.buttonColors(
                containerColor = GreyButton,
                contentColor = Color.Black
            )
        ) {
            Text(
                text = text,
                fontSize = if (isLandscape) 20.sp else 30.sp
            )
        }
    }

    @Composable
    fun OppButton(
        text: String,
        modifier: Modifier = Modifier
    ) {
        val configuration = LocalConfiguration.current
        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Button(
            modifier = if (isLandscape) modifier.width(32.dp) else modifier.width(64.dp).height(64.dp),
            shape = RoundedCornerShape(20.dp),
            onClick = {
                if (text != "↵")
                    increment(text)
                else
                    evaluate()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = OrangeButton,
                contentColor = Color.White
            )
        ) {
            Text(
                text = text,
                fontSize = if (isLandscape) 20.sp else 30.sp
            )
        }
    }

    private fun sizes(text: String): TextUnit {
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
        val configuration = LocalConfiguration.current
        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        Button(
            modifier = if (isLandscape) modifier.width(32.dp) else modifier.width(64.dp).height(64.dp),
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

    private fun convertLength(from: String, to: String): Double {

        //  BaseUnits will be Meters
        val meters = when(from) {
            "Meter" -> baseUnits
            "Kilometer" -> baseUnits * 1000
            "Centimeter" -> baseUnits / 100
            "Foot" -> baseUnits / 3.281
            "Inch" -> baseUnits / 39.37
            "Mile" -> baseUnits * 1609
            else -> baseUnits
        }
        val result = when(to) {
            "Meter" -> meters
            "Kilometer" -> meters / 1000
            "Centimeter" -> meters * 100
            "Foot" -> meters * 3.281
            "Inch" -> meters * 39.37
            "Mile" -> meters / 1609
            else -> meters
        }
        return result
    }

    private fun convertCurrency(from: String, to: String): Double {

        //  BaseUnits will be US Dollar
        val currency = when(from) {
            "US Dollar" -> baseUnits
            "Euro" -> baseUnits / 0.86157
            "Pound" -> baseUnits / 0.74929
            "Yen" -> baseUnits / 157.321
            "Yuan" -> baseUnits / 6.90
            else -> baseUnits
        }
        val result = when(to) {
            "US Dollar" -> currency
            "Euro" -> currency * 0.86157
            "Pound" -> currency * 0.74929
            "Yen" -> currency * 157.321
            "Yuan" -> currency * 6.90
            else -> currency
        }
        return result
    }

    private fun convertTemp(from: String, to: String): Double {

        //  BaseUnits will be Celsius
        val temp = when(from) {
            "Celsius" -> baseUnits
            "Fahrenheit" -> (baseUnits - 32) * 5/9
            "Kelvin" -> baseUnits - 273.15
            else -> baseUnits
        }
        Log.d("CALC", "Temp: $temp")
        val result = when(to) {
            "Celsius" -> temp
            "Fahrenheit" -> (temp * 9/5) + 32
            "Kelvin" -> temp + 273.15
            else -> temp
        }
        Log.d("CALC", "Result: $result")
        return result
    }

    private fun convertWeight(from: String, to: String): Double {

    //  BaseUnits will be Kilograms
        val mass = when(from) {
            "Kilogram" -> baseUnits
            "Gram" -> baseUnits / 1000
            "Milligram" -> baseUnits / 1000000
            "Pound" -> baseUnits / 2.205
            "Ounce" -> baseUnits / 35.274
            "Metric Tonne" -> baseUnits * 1000
            else -> baseUnits
        }
        val result = when(to) {
            "Kilogram" -> mass
            "Gram" -> mass * 1000
            "Milligram" -> mass * 1000000
            "Pound" -> mass * 2.205
            "Ounce" -> mass * 35.274
            "Metric Tonne" -> mass / 1000
            else -> mass
        }
        return result
    }

    fun convertOptions(option: String, from: String, to: String) {

        listOf("Kilogram", "Gram", "Milligram", "Pound", "Ounce")
        val df = DecimalFormat("#.###")
        val result = when (option) {
            "Length" -> convertLength(from, to)
            "$$$" -> convertCurrency(from, to)
            "Temp" -> convertTemp(from, to)
            "Weight" -> convertWeight(from, to)
            else -> Log.d("CALC", "Unsupported option: $option")
        }
        output.value = df.format(result)
    }

}