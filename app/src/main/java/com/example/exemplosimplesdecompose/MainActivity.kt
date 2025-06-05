package com.example.exemplosimplesdecompose

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exemplosimplesdecompose.ui.theme.ExemploSimplesDeComposeTheme
import com.example.exemplosimplesdecompose.view.Tabs
import com.example.exemplosimplesdecompose.view.Welcome

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val tab = loadIntConfig(this, "selected_tab")
        val check = loadBooleanConfig(this, "is_75_checked")
        setContent {
            ExemploSimplesDeComposeTheme {
                val navController: NavHostController = rememberNavController()
                NavHost(navController = navController, startDestination = "welcome") {
                    composable("welcome") { Welcome(navController) }
                    composable("tabs") { Tabs(navController, tab, check) }
                    //composable("mainalcgas") { AlcoolGasolinaPreco(navController, config.check) }
                }
            }
        }
    }
}

fun loadIntConfig(context: Context, chave: String, default: Int = 0): Int {
    val sharedFileName = "config"
    val sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    val selectedTab = sp.getInt(chave, default)
    return selectedTab
}

fun loadBooleanConfig(context: Context, chave: String, default: Boolean = false): Boolean {
    val sharedFileName = "config"
    val sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    val selectedTab = sp.getBoolean(chave, default)
    return selectedTab
}