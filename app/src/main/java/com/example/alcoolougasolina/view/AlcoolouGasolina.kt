package com.example.alcoolougasolina.view

import com.example.alcoolougasolina.util.getCurrentLocation
import android.widget.Toast
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcoolougasolina.R
import com.example.alcoolougasolina.data.AppConfig
import com.example.alcoolougasolina.data.CrudPosto
import com.example.alcoolougasolina.data.Posto
import com.example.alcoolougasolina.ui.theme.Purple40

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.alcoolougasolina.data.Coordenadas
import com.google.android.gms.location.LocationServices

@Composable
fun AlcoolGasolinaPreco() {
    val context = LocalContext.current

    val config = AppConfig(context)
    val check = config.loadBooleanConfig("is_75_checked")

    val postoService = CrudPosto(context)

    var alcool by remember { mutableStateOf("") }
    var gasolina by remember { mutableStateOf("") }
    var nomeDoPosto by remember { mutableStateOf("") }
    var checkedState by remember { mutableStateOf(check) }

    val placeholderResultado = stringResource(R.string.placeholder_resultado)
    var textoResultado by remember { mutableStateOf(placeholderResultado) }
    val descricao75p = stringResource(R.string.desc_75p)
    val complementoAdicao = stringResource(R.string.complemento_resultado)
    val complementoResultado = stringResource(R.string.complemento_resultado)
    val gasolinaCapitalizada = stringResource(R.string.gasolina)
    val alcoolCapitalizada = stringResource(R.string.alcool)

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var lastKnownLocation by remember {
        mutableStateOf<android.location.Location?>(null)
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                getCurrentLocation(context, fusedLocationClient) { location ->
                    lastKnownLocation = location
                }
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                getCurrentLocation(context, fusedLocationClient) { location ->
                    lastKnownLocation = location
                }
            }
            else -> {
                Toast
                    .makeText(context, "Permissão de localização negada.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun checkAndRequestLocationPermissions(onPermissionsGranted: () -> Unit) {
        val fineLocationPermissionGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseLocationPermissionGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (fineLocationPermissionGranted || coarseLocationPermissionGranted) {
            onPermissionsGranted()
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

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
                contentDescription = stringResource(R.string.desc_imagem_principal),
                modifier = Modifier.size(128.dp)
            )
            OutlinedTextField(
                value = alcool,
                onValueChange = {
                    alcool = it
                    textoResultado = placeholderResultado
                },
                label = { Text(text = stringResource(id = R.string.campo_preco_alcool)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = gasolina,
                onValueChange = {
                    gasolina = it
                    textoResultado = placeholderResultado
                },
                label = { Text(text = stringResource(R.string.campo_preco_gasolina)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = nomeDoPosto,
                onValueChange = { nomeDoPosto = it },
                label = { Text("${stringResource(R.string.campo_nome_posto)} ${stringResource(R.string.opcional)}") },
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
                            modifier = Modifier.semantics {
                                contentDescription = descricao75p
                            },
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
                            checkAndRequestLocationPermissions {
                                getCurrentLocation(context, fusedLocationClient) { location ->
                                    lastKnownLocation = location

                                    val novoPosto = Posto(
                                        nome = nomeDoPosto,
                                        valorGasolina = validateValue(gasolina),
                                        valorAlcool = validateValue(alcool)
                                    )

                                    if (location != null) {
                                        novoPosto.coordenadas = Coordenadas(
                                            location.latitude,
                                            location.longitude
                                        )
                                    }

                                    postoService.savePosto(novoPosto)

                                    Toast.makeText(
                                        context,
                                        "${novoPosto.nome} $complementoAdicao",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    nomeDoPosto = ""
                                    alcool = ""
                                    gasolina = ""
                                }
                            }
                        },
                        enabled = nomeDoPosto != "" && gasolina != "" && alcool != ""
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            stringResource(R.string.acao_adicao)
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

                        val melhor = if (resultado > fator)
                            gasolinaCapitalizada.uppercase()
                        else alcoolCapitalizada.uppercase()

                        textoResultado = "$complementoResultado $melhor"
                    }
                },
                enabled = gasolina != "" && alcool != ""
            ) {
                Text(stringResource(R.string.acao_calcular))
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

fun validateValue(value: String): String {
    return value.replace(",", ".")
}
