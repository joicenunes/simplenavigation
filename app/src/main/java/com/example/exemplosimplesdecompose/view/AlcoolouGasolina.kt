package com.example.exemplosimplesdecompose.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.trimmedLength
import androidx.navigation.NavHostController
import com.example.exemplosimplesdecompose.R
import com.example.exemplosimplesdecompose.data.AppConfig
import com.example.exemplosimplesdecompose.data.CrudPosto
import com.example.exemplosimplesdecompose.data.Posto
import com.example.exemplosimplesdecompose.ui.theme.Purple40

@Composable
fun AlcoolGasolinaPreco(navController: NavHostController) {
    val context = LocalContext.current

    val config = AppConfig(context)
    val check = config.loadBooleanConfig("is_75_checked")

    val postoService = CrudPosto(context)

    var alcool by remember { mutableStateOf("") }
    var gasolina by remember { mutableStateOf("") }
    var nomeDoPosto by remember { mutableStateOf("") }
    var checkedState by remember { mutableStateOf(check) }
    var textoResultado by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.gasstation),
                contentDescription = "Imagem principal: Bomba de gasolina com ícone de localização",
                modifier = Modifier.size(128.dp)
            )
            OutlinedTextField(
                value = alcool,
                onValueChange = {
                    alcool = it
                    textoResultado = "Vamos calcular?"
                },
                label = { Text("Preço do Álcool (R$)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = gasolina,
                onValueChange = {
                    gasolina = it
                    textoResultado = "Vamos calcular?"
                },
                label = { Text("Preço da Gasolina (R$)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = nomeDoPosto,
                onValueChange = { nomeDoPosto = it },
                label = { Text("Nome do Posto (Opcional))") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Row {
                        Text(
                            text = "75%",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(top = 16.dp, end = 8.dp)
                        )
                        Switch(
                            modifier = Modifier.semantics { contentDescription = "Switch para indicar 75%" },
                            checked = checkedState,
                            onCheckedChange = {
                                checkedState = it
                                config.saveBooleanConfig("is_75_checked", checkedState)
                            },
                            thumbContent = {
                                if (checkedState) {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = null,
                                        modifier = Modifier.size(SwitchDefaults.IconSize),
                                    )
                                }
                            }
                        )
                    }
                }
                Column {
                    Button (
                        onClick = {
                            val novoPosto = Posto(
                                nome = nomeDoPosto,
                                valorGasolina = gasolina,
                                valorAlcool = alcool
                            )
                            postoService.savePosto(novoPosto)
                        },
                        enabled = nomeDoPosto != "" && gasolina != "" && alcool != ""
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            "Salvar Posto"
                        )
                    }
                }
            }

            Button(
                onClick = {
                    val precoAlcool = alcool.replace(",", ".").toDoubleOrNull()
                    val precoGasolina = gasolina.replace(",", ".").toDoubleOrNull()
                    if (precoGasolina != null && precoAlcool != null) {
                        val resultado = precoAlcool / precoGasolina

                        val fator = if (checkedState) 0.75 else 0.7

                        val melhor = if (resultado > fator) "GASOLINA" else "ÁLCOOL"

                        val inicio = if (nomeDoPosto.trimmedLength() > 0)
                            "No posto $nomeDoPosto"
                        else "No posto"

                        textoResultado = "$inicio a melhor escolha é $melhor"
                    }
                },
                enabled = gasolina != "" && alcool != ""
            ) {
                Text("Calcular")
            }

            Text(
                text = textoResultado,
                style = MaterialTheme.typography.bodyMedium,
                color = Purple40,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
