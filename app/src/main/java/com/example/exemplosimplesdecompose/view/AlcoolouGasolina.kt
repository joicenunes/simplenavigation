package com.example.exemplosimplesdecompose.view

import android.content.Context
import android.content.SharedPreferences
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
import androidx.compose.material3.FloatingActionButton
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
import androidx.core.text.trimmedLength
import androidx.navigation.NavHostController
import com.example.exemplosimplesdecompose.R

@Composable
fun AlcoolGasolinaPreco(navController: NavHostController, initialCheck: Boolean) {
    val context = LocalContext.current
    var alcool by remember { mutableStateOf("") }
    var gasolina by remember { mutableStateOf("") }
    var nomeDoPosto by remember { mutableStateOf("") }
    var checkedState by remember { mutableStateOf(initialCheck) }
    var textoResultado by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
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
                modifier = Modifier
                    .size(128.dp)
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
                .padding(16.dp),
                horizontalArrangement = Arrangement.Start) {
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
                        saveConfig(context, checkedState)
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

            Button(
                onClick = {
                    val precoAlcool = alcool.replace(",", ".").toDoubleOrNull()
                    val precoGasolina = gasolina.replace(",", ".").toDoubleOrNull()
                    if (precoGasolina != null && precoAlcool != null) {
                        val resultado = precoAlcool / precoGasolina

                        val fator = if (checkedState) 0.75 else 0.7

                        val melhor = if (resultado > fator) "gasolina" else "álcool"

                        val inicio = if (nomeDoPosto.trimmedLength() > 0)
                            "No posto $nomeDoPosto"
                        else "No posto"

                        textoResultado = "$inicio a melhor escolha é $melhor"
                    }
                }
            ) {
                Text("Calcular")
            }

            Text(
                text = textoResultado,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                FloatingActionButton(
                    onClick = { navController.navigate("ListaDePostos/$nomeDoPosto")},
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Filled.Add, "Salvar Posto")
                }
            }
        }
    }
}

fun saveConfig(context: Context, switchState: Boolean) {
    val sharedFileName = "config"
    val sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    val editor = sp.edit()
    editor.putBoolean("is_75_checked", switchState)
    editor.apply()
}