package com.example.exemplosimplesdecompose.data

import android.content.Context
import android.content.SharedPreferences

class AppConfig(private val context: Context) {
    val FILE_NAME = "appConfig";
    fun loadIntConfig(chave: String, default: Int = 0): Int {
        val sharedFileName = FILE_NAME
        val sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
        val selectedTab = sp.getInt(chave, default)
        return selectedTab
    }

    fun loadBooleanConfig(chave: String, default: Boolean = false): Boolean {
        val sharedFileName = FILE_NAME
        val sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
        val selectedTab = sp.getBoolean(chave, default)
        return selectedTab
    }

    fun saveBooleanConfig(chave: String, value: Boolean) {
        val sharedFileName = FILE_NAME
        val sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putBoolean(chave, value)
        editor.apply()
    }

    fun saveIntConfig(chave: String, value: Int) {
        val sharedFileName = FILE_NAME
        val sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putInt(chave, value)
        editor.apply()
    }
}