package com.example.alcoolougasolina.view

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcoolougasolina.data.Posto
import com.example.alcoolougasolina.ui.theme.Purple40
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alcoolougasolina.R
import com.example.alcoolougasolina.data.viewmodel.PostoViewModel
import com.example.alcoolougasolina.data.viewmodel.PostoViewModelFactory

@Composable
fun PostosSalvos() {
    val context = LocalContext.current
    val application = context.applicationContext as Application
    val postoViewModel: PostoViewModel = viewModel(
        factory = PostoViewModelFactory(application)
    )

    val postos by postoViewModel.allPostos.collectAsState(initial = emptyList())

    var showEditDialog by remember { mutableStateOf(false) }
    var postoToEdit by remember { mutableStateOf<Posto?>(null) }

    val complementoExcluido = stringResource(R.string.complemento_excluido)
    val complementoSalvo = stringResource(R.string.complemento_salvo)

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        if (postos.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(postos, key = { it.id }) { item ->
                    MeuCard(
                        item = item,
                        onEditClick = { itemParaEditar ->
                            val postoOriginal = postos.find { it.id == itemParaEditar.id }
                            postoOriginal?.let {
                                postoToEdit = it
                                showEditDialog = true
                            }
                        },
                        onDeleteClick = { itemParaExcluir ->
                            val itemToRemoveFromList = postos.find { it.id == itemParaExcluir.id }
                            if (itemToRemoveFromList != null) {
                                postoViewModel.deletar(itemToRemoveFromList)
                                Toast.makeText(
                                    context,
                                    "${itemToRemoveFromList.nome} $complementoExcluido",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )
                }
            }
        } else {
            Row (
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.lista_posto_vazia),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Purple40,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }

    if (showEditDialog && postoToEdit != null) {
        PostoEdicao (
            posto = postoToEdit!!,
            onDismiss = {
                showEditDialog = false
                postoToEdit = null
            },
            onSave = { updatedPosto ->
                postoViewModel.atualizar(updatedPosto)
                Toast.makeText(
                    context,
                    "${updatedPosto.nome} $complementoSalvo",
                    Toast.LENGTH_SHORT
                ).show()
                showEditDialog = false
                postoToEdit = null
            }
        )
    }
}
