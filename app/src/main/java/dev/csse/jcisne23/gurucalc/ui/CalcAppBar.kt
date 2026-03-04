package dev.csse.jcisne23.gurucalc.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import dev.csse.jcisne23.gurucalc.ui.theme.BlueButton
import dev.csse.jcisne23.gurucalc.ui.theme.GuruCalcTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalcAppBar(

) {
    TopAppBar(
        title = {
            Text(
                text="GuruCalc",
                fontSize = 32.sp)
        },
        colors = TopAppBarColors(
            containerColor = BlueButton,
            scrolledContainerColor = BlueButton,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Preview
@Composable
fun TSAppBarPreview() {
    GuruCalcTheme{
        Scaffold(
            topBar = { CalcAppBar() }
        ) {
                innerPadding ->
            Box(modifier = Modifier.padding(innerPadding))
        }
    }
}