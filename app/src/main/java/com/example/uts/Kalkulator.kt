// file: Kalkulator.kt
package com.example.uts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- Skema Warna Baru ---
val darkBackground = Color(0xFF22252D)
val displayBackground = Color(0xFFF3EFE3)
val buttonRed = Color(0xFFE67A7A)
val buttonBlue = Color(0xFF6DA8A5)
val buttonGray = Color(0xFF4B4F58)
val textDark = Color(0xFF333333)
val textLightGray = Color(0xFFAAAAAA)

// --- Enum untuk Tipe Tombol ---
enum class ButtonType {
    Number,
    Operation,
    Clear,
    Equals,
    Special,
    Delete
}

@Composable
fun KalkulatorPage() {
    // --- State Management ---
    var history by remember { mutableStateOf("") }
    var display by remember { mutableStateOf("0") }
    var currentNumber by remember { mutableStateOf("") }
    var previousNumber by remember { mutableStateOf<Double?>(null) }
    var currentOperation by remember { mutableStateOf<String?>(null) }
    var isNewCalculation by remember { mutableStateOf(true) }

    // --- Logika Tombol ---
    val onButtonClick: (String, ButtonType) -> Unit = onButtonClick@{ buttonText, type ->
        when (type) {
            ButtonType.Number -> {
                if (isNewCalculation) {
                    display = ""
                    isNewCalculation = false
                }
                if (display == "0" && buttonText != ".") display = ""
                // Mencegah ada lebih dari satu titik desimal
                if (buttonText == "." && display.contains(".")) return@onButtonClick

                display += buttonText
                currentNumber += buttonText
            }
            ButtonType.Operation -> {
                if (currentNumber.isNotEmpty()) {
                    if (previousNumber != null && currentOperation != null) {
                        // Jika sudah ada operasi sebelumnya, hitung dulu (chaining)
                        val prev = previousNumber!!
                        val curr = currentNumber.toDouble()
                        val res = performCalculation(prev, curr, currentOperation!!)
                        previousNumber = res
                        display = formatResult(res)
                    } else {
                        previousNumber = currentNumber.toDouble()
                    }
                }
                currentOperation = buttonText
                history = "${formatResult(previousNumber!!)} $currentOperation"
                currentNumber = ""
                display = "0" // Siapkan display untuk angka berikutnya
            }
            ButtonType.Equals -> {
                if (previousNumber != null && currentOperation != null && currentNumber.isNotEmpty()) {
                    val prev = previousNumber!!
                    val curr = currentNumber.toDouble()
                    val result = performCalculation(prev, curr, currentOperation!!)

                    history = "${formatResult(prev)} $currentOperation ${formatResult(curr)} ="
                    display = formatResult(result)

                    // Reset untuk kalkulasi baru
                    previousNumber = result
                    currentNumber = result.toString().removeSuffix(".0")
                    currentOperation = null
                    isNewCalculation = true
                }
            }
            ButtonType.Special -> {
                if (display.isNotEmpty() && display != "0") {
                    var num = display.toDouble()
                    val result = when (buttonText) {
                        "+/-" -> -num
                        "%" -> num / 100
                        else -> num
                    }
                    display = formatResult(result)
                    currentNumber = display
                }
            }
            ButtonType.Delete -> {
                if (display.length > 1 && !isNewCalculation) {
                    display = display.dropLast(1)
                    currentNumber = currentNumber.dropLast(1)
                } else {
                    display = "0"
                    currentNumber = ""
                }
            }
            ButtonType.Clear -> {
                display = "0"
                history = ""
                currentNumber = ""
                previousNumber = null
                currentOperation = null
                isNewCalculation = true
            }
        }
    }

    // --- UI Utama ---
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // --- Layar Display ---
            CalculatorDisplay(history, display,)

            // --- Tombol-tombol ---
            CalculatorGrid(onButtonClick)
        }
    }
}

fun performCalculation(a: Double, b: Double, op: String): Double {
    return when (op) {
        "+" -> a + b
        "-" -> a - b
        "×" -> a * b
        "÷" -> if (b != 0.0) a / b else Double.NaN
        else -> 0.0
    }
}

fun formatResult(result: Double): String {
    if (result.isNaN()) return "Error"
    // Jika hasil adalah integer, tampilkan tanpa .0
    return if (result == result.toLong().toDouble()) {
        result.toLong().toString()
    } else {
        result.toString()
    }
}

@Composable
fun CalculatorDisplay(history: String, display: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(top = 30.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(displayBackground)
            .padding(24.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = history,
                color = textLightGray,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.End,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = display,
                color = textDark,
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                maxLines = 2
            )
        }
    }
}

@Composable
fun CalculatorGrid(onButtonClick: (String, ButtonType) -> Unit) {
    val buttonLayout = listOf(
        listOf("AC", "+/-", "%", "÷"),
        listOf("7", "8", "9", "×"),
        listOf("4", "5", "6", "-"),
        listOf("1", "2", "3", "+"),
        listOf("del", "0", ".", "=")
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        buttonLayout.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                row.forEach { buttonText ->
                    val modifier = if (buttonText == "0") Modifier.weight(2.1f) else Modifier.weight(1f)
                    val action = when (buttonText) {
                        in "0".."9", "." -> ButtonType.Number
                        "AC" -> ButtonType.Clear
                        "=" -> ButtonType.Equals
                        "+/-", "%" -> ButtonType.Special
                        "del" -> ButtonType.Delete
                        else -> ButtonType.Operation
                    }
                    CalculatorButton(
                        text = buttonText,
                        modifier = modifier,
                        onClick = { onButtonClick(buttonText, action) }
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    // Tentukan warna berdasarkan tipe tombol
    val backgroundColor = when (text) {
        "AC", "+/-", "%" -> buttonGray
        "÷", "×", "-", "+", "=" -> buttonBlue
        else -> buttonRed
    }
    val contentColor = Color.White
    // Bentuk tombol yang lebih oval untuk angka dan lebih bulat untuk lainnya
    val shape = if (text in "0".."9" || text == "del") RoundedCornerShape(24.dp) else RoundedCornerShape(50)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(shape)
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .aspectRatio(if (text == "0") 2f else 1f)
            .padding(8.dp) // Padding lebih kecil agar teks lebih besar
    ) {
        if (text == "del") {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Backspace,
                contentDescription = "Delete",
                tint = contentColor
            )
        } else {
            Text(
                text = text,
                color = contentColor,
                fontSize = if (text in "0".."9" || text == ".") 28.sp else 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
