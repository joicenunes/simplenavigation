@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.exemplosimplesdecompose.view

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.exemplosimplesdecompose.data.AppConfig

@Composable
fun Tabs(navController: NavHostController) {
    val context = LocalContext.current

    val config = AppConfig(context)
    val lastTab = config.loadIntConfig("selected_tab")

    val tabs = listOf("Calcular", "Postos salvos")

    var selectedTabIndex by remember { mutableIntStateOf(lastTab) }

    Column(modifier = Modifier.fillMaxSize()) {
        PrimaryTabRow (
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .safeDrawingPadding()
        ) {
            tabs.forEachIndexed { index: Int, title: String ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        config.saveIntConfig("selected_tab", index)
                    },
                    text = { Text(title) }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> AlcoolGasolinaPreco(navController)
            1 -> PostosSalvos(navController)
        }
    }
}
