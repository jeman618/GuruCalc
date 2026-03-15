package dev.csse.jcisne23.gurucalc.ui

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.csse.jcisne23.gurucalc.R
import dev.csse.jcisne23.gurucalc.ui.theme.ConvertBlueButton
import dev.csse.jcisne23.gurucalc.ui.theme.GreyButton

@Composable
fun CalcButtons(
    model: CalcViewModel,
    modifier: Modifier
) {

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    Column(
        modifier = modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
        verticalArrangement = if (isLandscape) Arrangement.spacedBy(0.dp) else Arrangement.spacedBy(12.dp)
    ) {

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            model.SpecButton("a²", Modifier.weight(1f))
            model.SpecButton("√a", Modifier.weight(1f))
            model.SpecButton("(", Modifier.weight(1f))
            model.SpecButton(")", Modifier.weight(1f))
            model.OppButton("÷", Modifier.weight(1f))
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            model.SpecButton("log", Modifier.weight(1f))
            model.NumButton("7", Modifier.weight(1f))
            model.NumButton("8", Modifier.weight(1f))
            model.NumButton("9", Modifier.weight(1f))
            model.OppButton("x", Modifier.weight(1f))
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            model.SpecButton("sin", Modifier.weight(1f))
            model.NumButton("4", Modifier.weight(1f))
            model.NumButton("5", Modifier.weight(1f))
            model.NumButton("6", Modifier.weight(1f))
            model.OppButton("-", Modifier.weight(1f))
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            model.SpecButton("cos", Modifier.weight(1f))
            model.NumButton("1", Modifier.weight(1f))
            model.NumButton("2", Modifier.weight(1f))
            model.NumButton("3", Modifier.weight(1f))
            model.OppButton("+", Modifier.weight(1f))
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            model.SpecButton("π", Modifier.weight(1f))
            model.NumButton("%", Modifier.weight(1f))
            model.NumButton("0", Modifier.weight(1f))
            model.NumButton(".", Modifier.weight(1f))
            model.OppButton("↵", Modifier.weight(1f))
        }
    }
}

@Composable
fun ControlRow(model: CalcViewModel, navController: NavController) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row {
            IconButton(onClick = { model.moveCursorLeft() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Left")
            }

            IconButton(onClick = { model.moveCursorRight() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Right")
            }
        }

        Button(
            modifier = Modifier.height(35.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreyButton,
                contentColor = Color.Black
            ),
            onClick = {
                navController.navigate("convert")
            }
        ) {
            Text(text = "Convert",
                fontSize = 14.sp)
        }

        IconButton(
            onClick = { model.deleteAtCursor() }
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_delete),
                contentDescription = "Remove last character",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun CalcConvertButtons(model: CalcViewModel) {

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    Column(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
        verticalArrangement = if (isLandscape) Arrangement.spacedBy(0.dp) else Arrangement.spacedBy(12.dp)
    ) {

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            IconButton(onClick = { model.moveCursorLeft() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Left")
            }

            IconButton(onClick = { model.moveCursorRight() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Right")
            }
            model.SpecButton("(", Modifier.weight(1f))
            model.SpecButton(")", Modifier.weight(1f))
            model.OppButton("÷", Modifier.weight(1f))
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            model.SpecButton("a²", Modifier.weight(1f))
            model.NumButton("7", Modifier.weight(1f))
            model.NumButton("8", Modifier.weight(1f))
            model.NumButton("9", Modifier.weight(1f))
            model.OppButton("x", Modifier.weight(1f))
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            model.SpecButton("√a", Modifier.weight(1f))
            model.NumButton("4", Modifier.weight(1f))
            model.NumButton("5", Modifier.weight(1f))
            model.NumButton("6", Modifier.weight(1f))
            model.OppButton("-", Modifier.weight(1f))
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            model.SpecButton("", Modifier.weight(1f))
            model.NumButton("1", Modifier.weight(1f))
            model.NumButton("2", Modifier.weight(1f))
            model.NumButton("3", Modifier.weight(1f))
            model.OppButton("+", Modifier.weight(1f))
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            model.SpecButton("", Modifier.weight(1f))
            model.NumButton("%", Modifier.weight(1f))
            model.NumButton("0", Modifier.weight(1f))
            model.NumButton(".", Modifier.weight(1f))
            model.OppButton("↵", Modifier.weight(1f))
        }
    }
}

@Composable
fun ConvertControlRow(model: CalcViewModel, navController: NavController) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Button(
            modifier = Modifier.height(35.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreyButton,
                contentColor = Color.Black
            ),
            onClick = {
                navController.navigate("home")
            }
        ) {
            Text(text = "Calculator",
                fontSize = 14.sp)
        }

        IconButton(
            onClick = { model.deleteAtCursor() }
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_delete),
                contentDescription = "Remove last character",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun UnitButtons(model: CalcViewModel) {

    var selectedButton by remember { mutableStateOf(model.units[0]) }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        model.units.forEach { unit ->
            val isSelected = selectedButton == unit
            Button(
                onClick = {
                    selectedButton = unit
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) ConvertBlueButton else GreyButton
                )
            ) {
                Text(
                    text = unit,
                    color = if (isSelected) Color.White else Color.Black)
            }
        }
    }
    SimpleDropdown(model.options, selectedButton, model)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleDropdown(options: Map<String, List<String>>, selectedButton: String, model: CalcViewModel) {

    var from by remember { mutableStateOf("Meter") }
    var to by remember { mutableStateOf("Meter") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f).padding(end = 8.dp)
        ) {
            var expanded by remember { mutableStateOf(false) }
            var selectedOption by remember(selectedButton) {
                mutableStateOf(options.getValue(selectedButton)[0])
            }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            )
            {
                TextField(
                    value = selectedOption,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("From") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options[selectedButton]?.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedOption = option
                                from = selectedOption
                                expanded = false
                                Log.d("CALC", "From: $from")
                                model.convertOptions(selectedButton, from, to)
                            }
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier.weight(1f).padding(start = 8.dp)
        ) {
            var expanded by remember { mutableStateOf(false) }
            var selectedOption by remember(selectedButton) {
                mutableStateOf(options.getValue(selectedButton)[0])
            }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            )
            {

                TextField(
                    value = selectedOption,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("To") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options[selectedButton]?.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedOption = option
                                expanded = false
                                to = selectedOption
                                Log.d("CALC", "To: $to")
                                model.convertOptions(selectedButton, from, to)
                            }
                        )
                    }
                }
            }
        }
    }
}
