package com.example.alcoolougasolina.view

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.alcoolougasolina.data.Posto
import com.example.alcoolougasolina.ui.theme.Purple40
import com.example.alcoolougasolina.ui.theme.Purple80
import com.example.alcoolougasolina.R
import androidx.core.net.toUri

@Composable
fun MeuCard(
    item: Posto,
    onEditClick: (Posto) -> Unit,
    onDeleteClick: (Posto) -> Unit
) {
    val context = LocalContext.current
    val precoGasolina = stringResource(R.string.campo_preco_gasolina)
    val precoAlcool = stringResource(R.string.campo_preco_alcool)
    val simboloMonetario = stringResource(R.string.simbolo_monetario)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = item.nome,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.primary
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    IconButton(
                        onClick = {
                            val url = "geo:0,0?q=${item.coordenadas.latitude},${item.coordenadas.longitude}${nameToLabel(item.nome)}"
                            val gmmIntentUri = url.toUri()
                            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                                setPackage("com.google.android.apps.maps")
                            }
                            context.startActivity(mapIntent)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = stringResource(R.string.acao_mapa),
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(80.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Row {
                        Text(
                            text = "$precoGasolina: $simboloMonetario",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = item.valorGasolina.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row {
                        Text(
                            text = "$precoAlcool: $simboloMonetario",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = item.valorAlcool.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { onEditClick(item) },
                    colors = ButtonColors(
                        Purple80, Purple40, Purple80, Purple40
                    )
                ) {
                    Text(stringResource(R.string.acao_edicao))
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { onDeleteClick(item) }
                ) {
                    Text(stringResource(R.string.acao_exclusao))
                }
            }
        }
    }
}

fun nameToLabel(name: String): String {
    return name.replace(" ", "+")
}