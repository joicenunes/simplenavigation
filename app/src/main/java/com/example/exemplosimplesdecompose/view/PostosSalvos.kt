package com.example.exemplosimplesdecompose.view

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.exemplosimplesdecompose.data.CrudPosto
import com.example.exemplosimplesdecompose.data.Posto
import com.example.exemplosimplesdecompose.ui.theme.Purple40

@Composable
fun PostosSalvos(navController: NavHostController) {
    val context = LocalContext.current
    val postoService = CrudPosto(context)

    var items: MutableList<Posto> = remember { loadItems(postoService) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        if (items.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items, key = { it.id }) { item ->
                    MeuCard(
                        item = item,
                        onEditClick = { itemParaEditar ->
                            // Toast.makeText(context, "Editar: ${itemEditado.titulo}", Toast.LENGTH_SHORT).show()
                            // Aqui você navegaria para uma tela de edição ou abriria um diálogo
                            println("Solicitada edição do item: ${itemParaEditar.nome}")
                        },
                        onDeleteClick = { itemParaExcluir ->
                            val itemToRemoveFromList = items.find { it.id == itemParaExcluir.id }
                            if (itemToRemoveFromList != null) {
                                postoService.removePosto(itemToRemoveFromList.id) // Remove do SharedPreferences
                                items.remove(itemToRemoveFromList) // Remove do estado da UI
                                Toast.makeText(
                                    context,
                                    "Posto ${itemToRemoveFromList.nome} excluído!",
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
                    text = "Não há postos salvos",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Purple40,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

fun loadItems(postoService: CrudPosto): MutableList<Posto> {
    return postoService.getTodosPostos()
}
