package dev.csse.jcisne23.gurucalc.ui

import android.R.attr.value
import android.R.color.white
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColor
import dev.csse.jcisne23.gurucalc.R
import dev.csse.jcisne23.gurucalc.ui.theme.BlueButton
import dev.csse.jcisne23.gurucalc.ui.theme.GreyButton

@Composable
fun CalcScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {CalcAppBar()}
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            var count by remember { mutableStateOf(0) }

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Count: $count")
                Row {
                    SpecButton(
                        onClick = {
                            count++
                        },
                        text = "a²"
                    )
                    SpecButton(
                        onClick = {
                            count++
                        },
                        text = "√a"
                    )
                    SpecButton(
                        onClick = {
                            count++
                        },
                        text = "("
                    )
                    SpecButton(
                        onClick = {

                        },
                        text = ")"
                    )
                    OppButton(
                        onClick = {

                        },
                        text = "÷"
                    )

                }
                Row {
                    SpecButton(
                        onClick = {
                            count++
                        },
                        text = "log"
                    )
                    NumButton(
                        onClick = {
                            count++
                        },
                        text = "7"
                    )
                    NumButton(
                        onClick = {
                            count++
                        },
                        text = "8"
                    )
                    NumButton(
                        onClick = {
                            count++
                        },
                        text = "9"
                    )
                    OppButton(
                        onClick = {

                        },
                        text = "x"
                    )
                }
                Row {
                    SpecButton(
                        onClick = {
                            count++
                        },
                        text = "sin"
                    )
                    NumButton(
                        onClick = {
                            count++
                        },
                        text = "4"
                    )
                    NumButton(
                        onClick = {
                            count++
                        },
                        text = "5"
                    )
                    NumButton(
                        onClick = {
                            count++
                        },
                        text = "6"
                    )
                    OppButton(
                        onClick = {

                        },
                        text = "-"
                    )
                }
                Row {
                    SpecButton(
                        onClick = {
                            count++
                        },
                        text = "cos"
                    )
                    NumButton(
                        onClick = {
                            count++
                        },
                        text = "1"
                    )
                    NumButton(
                        onClick = {
                            count++
                        },
                        text = "2"
                    )
                    NumButton(
                        onClick = {
                            count++
                        },
                        text = "3"
                    )
                    OppButton(
                        onClick = {

                        },
                        text = "+"
                    )
                }
                Row {
                    SpecButton(
                        onClick = {
                            count++
                        },
                        text = "π"
                    )
                    NumButton(
                        onClick = {
                            count++
                        },
                        text = "%"
                    )
                    NumButton(
                        onClick = {
                            count++
                        },
                        text = "0"
                    )
                    NumButton(
                        onClick = {
                            count++
                        },
                        text = "."
                    )
                    OppButton(
                        onClick = {

                        },
                        text = "="
                    )
                }

            }
        }
    }

}

@Preview
@Composable
fun CalcScreenPreview() {
    CalcScreen()
}