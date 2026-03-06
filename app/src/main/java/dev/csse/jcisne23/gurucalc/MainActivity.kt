package dev.csse.jcisne23.gurucalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.jcisne23.gurucalc.ui.CalcApp
import dev.csse.jcisne23.gurucalc.ui.CalcScreen
import dev.csse.jcisne23.gurucalc.ui.CalcViewModel
import dev.csse.jcisne23.gurucalc.ui.theme.GuruCalcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GuruCalcTheme {
                    CalcApp()
                }
            }
        }
}

@Preview(showBackground = true)
@Composable
fun CalcPreview() {
    GuruCalcTheme {
        CalcApp()
    }
}