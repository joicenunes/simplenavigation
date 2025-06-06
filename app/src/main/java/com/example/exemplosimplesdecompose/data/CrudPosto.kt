package com.example.exemplosimplesdecompose.data;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.math.BigDecimal
import java.nio.charset.StandardCharsets
import android.util.Base64

class CrudPosto(private val context: Context) {
    private val FILE_NAME = "postosSalvos"
    private val sp: SharedPreferences = context.getSharedPreferences(
        FILE_NAME,
        Context.MODE_PRIVATE
    )

    fun savePosto(posto: Posto) {
        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(posto)
        val byteData = baos.toByteArray()
        val base64String = Base64.encodeToString(byteData, Base64.DEFAULT)
        val editor = sp.edit()
        editor.putString(posto.id, base64String)
        editor.apply()
    }

    fun getPosto(id: String): Posto {
        val base64String = sp.getString(id, null)
        if (base64String != null && base64String.isNotEmpty()) {
            val byteData = Base64.decode(base64String, Base64.DEFAULT)

            val bis = ByteArrayInputStream(byteData)
            val obi = ObjectInputStream(bis)

            val posto = obi.readObject() as Posto
            return posto
        }
        return Posto("")
    }

    fun removePosto(id: String) {
        val editor = sp.edit()
        editor.remove(id)
        editor.apply()
    }

    // Obtém todos os postos
    fun getTodosPostos(): MutableList<Posto> {
        val listaPostos = mutableListOf<Posto>()
        sp.all.keys.forEach { key ->
            listaPostos.add(getPosto(key))
        }
        return listaPostos
    }
}
