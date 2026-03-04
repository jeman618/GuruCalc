package dev.csse.jcisne23.gurucalc.ui

import android.service.controls.Control
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.csse.jcisne23.gurucalc.R

@Composable
fun CalcConvertScreen(
    modifier: Modifier = Modifier,
    model: CalcViewModel = viewModel<CalcViewModel>()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {CalcAppBar()}
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {

            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                UnitButtons(model)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    CalcExp(
                        input = model.input.value,
                        output = model.output.value,
                        clear = "current",
                        model = model)
                }
                ConvertControlRow(model)
                CalcConvertButtons(model)
            }
        }
    }

}

@Preview(
    heightDp = 800,
    widthDp = 400
)
@Composable
fun CalcConvertScreenPreview() {
    CalcConvertScreen(
        modifier = Modifier
            .height(800.dp)
            .width(450.dp)
    )
}