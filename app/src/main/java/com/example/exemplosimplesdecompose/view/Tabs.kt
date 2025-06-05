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

@Composable
fun Tabs(navController: NavHostController, lastTab: Int, check: Boolean) {
    val context = LocalContext.current
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
                        saveIntConfig(context, "selected_tab", index)
                    },
                    text = { Text(title) }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> AlcoolGasolinaPreco(navController, check)
            1 -> PostosSalvos(navController)
        }
    }
}

fun saveBooleanConfig(context: Context, chave: String, value: Boolean) {
    val sharedFileName = "config"
    val sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    val editor = sp.edit()
    editor.putBoolean(chave, value)
    editor.apply()
}

fun saveIntConfig(context: Context, chave: String, value: Int) {
    val sharedFileName = "config"
    val sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    val editor = sp.edit()
    editor.putInt(chave, value)
    editor.apply()
}