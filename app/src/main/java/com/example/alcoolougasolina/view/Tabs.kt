@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.alcoolougasolina.view

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.alcoolougasolina.data.AppConfig
import com.example.alcoolougasolina.R

@Composable
fun Tabs() {
    val context = LocalContext.current

    val config = AppConfig(context)
    val lastTab = config.loadIntConfig("selected_tab")
    val calcular = stringResource(R.string.acao_calcular)
    val postosSalvos = stringResource(R.string.postos_salvos)

    val tabs = listOf(calcular, postosSalvos)

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
            0 -> AlcoolGasolinaPreco()
            1 -> PostosSalvos()
        }
    }
}
