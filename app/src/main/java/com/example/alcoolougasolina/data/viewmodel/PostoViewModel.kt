package com.example.alcoolougasolina.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.alcoolougasolina.data.Posto
import com.example.alcoolougasolina.data.database.AppDatabase
import com.example.alcoolougasolina.data.repository.PostoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow

class PostoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostoRepository
    val allPostos: Flow<List<Posto>>

    init {
        val postoDAO = AppDatabase.getDatabase(application).postoDAO()
        repository = PostoRepository(postoDAO)
        allPostos = repository.todosOsPostos
    }

    fun inserir(posto: Posto) = viewModelScope.launch(Dispatchers.IO) {
        repository.inserir(posto)
    }

    fun atualizar(posto: Posto) = viewModelScope.launch(Dispatchers.IO) {
        repository.atualizar(posto)
    }

    fun deletar(posto: Posto) = viewModelScope.launch(Dispatchers.IO) {
        repository.excluir(posto)
    }
}

class PostoViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostoViewModel(application) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}