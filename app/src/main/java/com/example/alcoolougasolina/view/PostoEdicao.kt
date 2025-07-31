package com.example.alcoolougasolina.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.alcoolougasolina.data.Posto
import java.math.BigDecimal
import com.example.alcoolougasolina.R

@Composable
fun PostoEdicao(
    posto: Posto,
    onDismiss: () -> Unit,
    onSave: (Posto) -> Unit
) {
    var nome by remember { mutableStateOf(posto.nome) }
    var gasolina by remember { mutableStateOf(posto.valorGasolina.toString()) }
    var alcool by remember { mutableStateOf(posto.valorAlcool.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("${stringResource(R.string.acao_edicao)} ${posto.nome}") },
        text = {
            Column {
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text(stringResource(R.string.campo_nome_posto)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = gasolina,
                    onValueChange = {
                        gasolina = it.replace(',', '.')
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = alcool,
                    onValueChange = {
                        alcool = it.replace(',', '.')
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val postoAtualizado = posto.copy(
                        nome = nome,
                        valorGasolina = BigDecimal(gasolina),
                        valorAlcool = BigDecimal(alcool)
                    )
                    onSave(postoAtualizado)
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.acao_salvar))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.acao_cancelar))
            }
        }
    )
}