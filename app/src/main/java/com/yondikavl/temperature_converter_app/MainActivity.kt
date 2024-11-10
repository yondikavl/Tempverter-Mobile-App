package com.yondikavl.temperature_converter_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.yondikavl.temperature_converter_app.ui.theme.Temperature_Converter_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Temperature_Converter_AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        StatefulTemperatureInput()
                        ConverterApp()
                        TwoWayConverterApp()
                    }
                }
            }
        }
    }
}

@Composable
fun StatefulTemperatureInput(
) {
    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }
    Column(Modifier.padding(16.dp)) {
        Text(
            text = stringResource(R.string.stateful_converter),
            style = MaterialTheme.typography.headlineSmall
        )
        OutlinedTextField(
            value = input,
            label = { Text(stringResource(R.string.enter_celsius)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { newInput ->
                input = newInput
                output = convertToFahrenheit(newInput)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        )
        Text(stringResource(R.string.temperature_fahrenheit, output))
    }
}

@Composable
fun StatelessTemperatureInput(
    input: String,
    output: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier.padding(16.dp)) {
        Text(
            text = stringResource(R.string.stateless_converter),
            style = MaterialTheme.typography.headlineSmall
        )
        OutlinedTextField(
            value = input,
            label = { Text(stringResource(R.string.enter_celsius)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        )
        Text(stringResource(R.string.temperature_fahrenheit, output))
    }
}

@Composable
private fun ConverterApp(
    modifier: Modifier = Modifier,
) {
    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }
    Column(modifier) {
        StatelessTemperatureInput(
            input = input,
            output = output,
            onValueChange = { newInput ->
                input = newInput
                output = convertToFahrenheit(newInput)
            }
        )
    }
}

@Composable
fun GeneralTemperatureInput(
    scale: Scale,
    input: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        OutlinedTextField(
            value = input,
            label = { Text(stringResource(R.string.enter_temperature, scale.scaleName)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        )
    }
}

@Composable
private fun TwoWayConverterApp(
    modifier: Modifier = Modifier,
) {
    var celsius by remember { mutableStateOf("") }
    var fahrenheit by remember { mutableStateOf("") }
    Column (modifier.padding(16.dp)){
        Text(
            text = stringResource(R.string.two_way_converter),
            style = MaterialTheme.typography.headlineSmall
        )
        GeneralTemperatureInput(
            scale = Scale.CELSIUS,
            input = celsius,
            onValueChange = { newInput ->
                celsius = newInput
                fahrenheit = convertToFahrenheit(newInput)
            }
        )
        GeneralTemperatureInput(
            scale = Scale.FAHRENHEIT,
            input = fahrenheit,
            onValueChange = { newInput ->
                fahrenheit = newInput
                celsius = convertToCelsius(newInput)
            }
        )
    }
}


private fun convertToFahrenheit(celsius: String) =
    celsius.toDoubleOrNull()?.let {
        (it * 9 / 5) + 32
    }.toString()

private fun convertToCelsius(fahrenheit: String) =
    fahrenheit.toDoubleOrNull()?.let {
        (it - 32) * 5 / 9
    }.toString()

enum class Scale(val scaleName: String) {
    CELSIUS("Celsius"),
    FAHRENHEIT("Fahrenheit")
}