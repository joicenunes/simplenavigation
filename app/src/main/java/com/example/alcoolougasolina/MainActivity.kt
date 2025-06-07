package com.example.alcoolougasolina

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alcoolougasolina.ui.theme.AppTheme
import com.example.alcoolougasolina.view.Tabs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController: NavHostController = rememberNavController()
                NavHost(navController = navController, startDestination = "tabs") {
                    composable("tabs") { Tabs() }
                }
            }
        }
    }
}