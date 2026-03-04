package dev.csse.jcisne23.gurucalc.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HistorySection(model: CalcViewModel, modifier: Modifier) {

    LazyColumn(
        modifier = modifier,
        reverseLayout = true
    ) {
        items(model.history.reversed()) { it ->

            CalcExp(
                input = it.expression,
                output = it.result,
                clear = "history",
                model = model)
        }
    }
}