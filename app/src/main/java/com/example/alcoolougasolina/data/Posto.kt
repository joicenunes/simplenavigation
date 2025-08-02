package com.example.alcoolougasolina.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.math.BigDecimal

@Entity(tableName = "postos")
data class Posto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    @Embedded
    var coordenadas: Coordenadas,
    val valorAlcool: BigDecimal,
    val valorGasolina: BigDecimal
): Serializable {}