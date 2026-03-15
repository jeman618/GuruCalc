package dev.csse.jcisne23.gurucalc.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun CalcScreen(
    navController: NavController,
    model: CalcViewModel = viewModel<CalcViewModel>()
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {CalcAppBar()}
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (isLandscape) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.8f)
                            .fillMaxHeight()
                            .padding(end = 8.dp)
                    ) {
                        HistorySection(
                            model = model,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        )

                        CalcExp(
                            input = model.input.value,
                            output = model.output.value,
                            clear = "current",
                            screen = "home",
                            model = model
                        )
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        ControlRow(model, navController)

                        CalcButtons(
                            model = model,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                    }
                }
            }
            else {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        HistorySection(
                            model = model,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        )
                        CalcExp(
                            input = model.input.value,
                            output = model.output.value,
                            clear = "current",
                            screen = "home",
                            model = model
                        )
                    }
                    ControlRow(model, navController)
                    CalcButtons(model, Modifier)
                }
            }
        }
    }

}