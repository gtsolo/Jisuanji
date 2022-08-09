package com.example.jisuanji


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jisuanji.ui.theme.Background
import com.example.jisuanji.ui.theme.JisuanjiTheme
import com.example.jisuanji.ui.theme.Orange

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Calculater()
        }
    }


val buttons = arrayOf(
        arrayOf("AC" to LightGray, "+/-" to LightGray, "%" to LightGray, "÷" to Orange),
        arrayOf("7" to DarkGray, "8" to DarkGray, "9" to DarkGray, "×" to Orange),
        arrayOf("4" to DarkGray, "5" to DarkGray, "6" to DarkGray, "-" to Orange),
        arrayOf("1" to DarkGray, "2" to DarkGray, "3" to DarkGray, "+" to Orange),
        arrayOf("0" to DarkGray, "." to DarkGray, "=" to Orange),
    )
data class CalculatorState(val number1: Int = 0, val number2: Int = 0, val opt: String? = null)

    fun calculate(currState: CalculatorState, input: String): CalculatorState {
        return when (input) {
            in "0".."9" -> currState.copy(number2 = input.toInt(), number1 = currState.number2)
            in arrayOf("+", "-", "×", "÷") -> currState.copy(opt = input)
            "=" -> when (currState.opt) {
                "+" -> currState.copy(number2 = currState.number1 + currState.number2)
                "-" -> currState.copy(number2 = currState.number1 - currState.number2)
                "×" -> currState.copy(number2 = currState.number1 * currState.number2)
                "÷" -> currState.copy(number2 = currState.number1 / currState.number2)
                else -> currState
            }
            else -> currState
        }
    }

@Preview(showSystemUi = true)
@Composable

fun Calculater() {
    var state by remember {
        mutableStateOf(CalculatorState())
    }
    Column(
        modifier = Modifier
            .background(Background)
            .padding(horizontal = 10.dp)
    ) {
        Box(
            Modifier.fillMaxHeight(0.3f)
                .fillMaxWidth()
        )
        {
            Text(text = state.number2.toString(), fontSize = 101.sp, color = Color.White)

        }

        Column(Modifier.fillMaxSize()) {
            buttons.forEach {
                Row(
                    modifier = Modifier
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    it.forEach {
                        CalculatorButton(
                            modifier = Modifier
                                .weight(if (it.first == "0") 2f else 1f)
                                .aspectRatio(if (it.first == "0") 2f else 1f)
                                .background(it.second),
                            symbol = it.first
                        ) {
                            state = calculate(state, it.first)
                        }
                    }
                }
            }
        }
    }
}
}
@Composable
fun CalculatorButton(modifier: Modifier, symbol: String, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.Red)
            .then(modifier)
            .clickable { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Text(symbol,fontSize=40.sp, color = Color.White)
    }
}