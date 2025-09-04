package com.example.calculador_de_gorjeta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.pm.ShortcutInfoCompat
import com.example.calculador_de_gorjeta.ui.theme.CalculadordegorjetaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadordegorjetaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalcularGorjetaApp(modifier = Modifier
                        .padding(innerPadding))
                }
            }
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    showSystemUi = true
)
fun CalcularGorjetaApp(modifier: Modifier = Modifier) {
    var conta by remember { mutableStateOf(0.0) }
    var tarifa by remember { mutableStateOf(10.0) }
    var gorjeta by remember { mutableStateOf(0.0) }
    var arredondar by remember { mutableStateOf(false) }

    Column (
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Calculador de gorjeta",
            textAlign = TextAlign.Start,
            fontSize = 24.sp
        )
        MyTextField(
            value = conta.toString(),
            onValueChange = {
                conta = it.toDoubleOrNull() ?: 0.0
                gorjeta = calcularGorjeta(conta, tarifa, arredondar) },
            label = R.string.valor_da_conta,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(8.dp))
        MyTextField(
            value = tarifa.toString(),
            onValueChange = { conta = it.toDoubleOrNull() ?: 0.0 },
            label = R.string.tarifa_da_gorjeta,
            modifier = Modifier
        )
        Row (
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Arredondar gorjeta?",
            )
            Switch(
                checked = arredondar,
                onCheckedChange = { arredondar = it }
            )
        }
        Text(
            text = "Gorjeta: R$ ${"%.2f".format(gorjeta * 0.1)}",
            modifier = Modifier.padding(16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: Int,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}

fun calcularGorjeta(conta: Double = 0.0, tarifa: Double = 10.0, arredondar: Boolean = false): Double {
    var gorjeta = conta * (tarifa / 100)
    if (arredondar) {
        gorjeta = kotlin.math.ceil(gorjeta)
    }
    return gorjeta
}