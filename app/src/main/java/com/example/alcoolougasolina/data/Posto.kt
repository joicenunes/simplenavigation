package com.example.alcoolougasolina.data

import java.io.Serializable
import java.math.BigDecimal
import java.util.UUID

data class Posto(
    val id: String,
    val nome: String,
    val coordenadas: Coordenadas,
    val valorAlcool: BigDecimal,
    val valorGasolina: BigDecimal
): Serializable {
    // Construtor secundário com coordenadas de Fortaleza
    constructor(nome: String) : this(
        id = UUID.randomUUID().toString(),
        nome,
        Coordenadas(41.40338, 2.17403),
        BigDecimal("0.00"),
        BigDecimal("0.00")
    )

    constructor(
        nome: String,
        valorAlcool: String,
        valorGasolina: String
    ) : this(
        id = UUID.randomUUID().toString(),
        nome,
        Coordenadas(41.40338, 2.17403),
        BigDecimal(valorAlcool),
        BigDecimal(valorGasolina)
    )
}