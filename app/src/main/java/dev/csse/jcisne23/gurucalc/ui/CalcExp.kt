package dev.csse.jcisne23.gurucalc.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.csse.jcisne23.gurucalc.ui.theme.ExpBackground
import dev.csse.jcisne23.gurucalc.ui.theme.ExpSeparator

@Composable
fun CalcExp(input: TextFieldValue, output: String, screen: String, clear: String, model: CalcViewModel) {
    HorizontalDivider(thickness = 2.dp, color = ExpSeparator)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(ExpBackground)
            .padding(6.dp)
    )
    {
        IconButton(
            onClick = {
                if (clear == "current") {
                    model.reset()
                }
                else {
                    model.clearHistory()
                }
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(30.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Clear",
            )

        }
        Spacer(modifier = Modifier.padding(bottom = 72.dp))

        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            BasicTextField(
                value = input,
                onValueChange = { model.input.value = it },
                textStyle = TextStyle(fontSize = 36.sp),
                cursorBrush = SolidColor(Color.Black),
                modifier = Modifier
                    .weight(1f)
                    .background(ExpBackground)
            )
            OutputExists(output, screen)
            Text(
                modifier = Modifier,
                text = output,
                fontSize = 36.sp
            )
        }
    }
}

@Composable
fun OutputExists(s: String, screen: String) {
    return if (s.isEmpty()) {
        Text(modifier = Modifier,
            text = "",
            fontSize = 36.sp
        )
    }
    else {
        if (screen == "home") {
            Text(modifier = Modifier.padding(end = 32.dp),
                text = "=",
                fontSize = 36.sp
            )
        }
        else {
            Icon(
                modifier = Modifier.padding(end = 32.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Converted to"
            )
        }
    }
}