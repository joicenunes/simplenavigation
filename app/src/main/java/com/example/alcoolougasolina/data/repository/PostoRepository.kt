package com.example.alcoolougasolina.data.repository

import com.example.alcoolougasolina.data.DAO.PostoDAO
import com.example.alcoolougasolina.data.Posto
import kotlinx.coroutines.flow.Flow

class PostoRepository(
    private val postoDAO: PostoDAO
) {
    val todosOsPostos: Flow<List<Posto>> = postoDAO.getAll()

    suspend fun inserir(posto: Posto) {
        postoDAO.insert(posto)
    }

    suspend fun atualizar(posto: Posto) {
        postoDAO.update(posto)
    }

    suspend fun excluir(posto: Posto) {
        postoDAO.delete(posto)
    }
}