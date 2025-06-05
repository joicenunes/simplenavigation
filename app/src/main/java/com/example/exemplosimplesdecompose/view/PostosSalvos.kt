package com.example.exemplosimplesdecompose.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.exemplosimplesdecompose.data.Coordenadas
import com.example.exemplosimplesdecompose.data.Posto

@Composable
fun PostosSalvos(navController: NavHostController) {
    val context = LocalContext.current

    val items = listOf(
        Posto(
            "Posto 1",
            Coordenadas(0.2505891,0.365646)
        ),
        Posto(
            "Posto 2",
            Coordenadas(0.132165465,0.48789987)
        )
    )
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                MeuCard(
                    item = item,
                    onEditClick = { itemEditado ->
                        // Toast.makeText(context, "Editar: ${itemEditado.titulo}", Toast.LENGTH_SHORT).show()
                        // Aqui você navegaria para uma tela de edição ou abriria um diálogo
                        println("Solicitada edição do item: ${itemEditado.nome}")
                    },
                    onDeleteClick = { itemExcluido ->
                        // Toast.makeText(context, "Excluir: ${itemExcluido.titulo}", Toast.LENGTH_SHORT).show()
                        // Aqui você removeria o item da sua lista de dados (por exemplo, de um MutableList)
                        println("Solicitada exclusão do item: ${itemExcluido.nome}")
                    }
                )
            }
        }
    }
}
