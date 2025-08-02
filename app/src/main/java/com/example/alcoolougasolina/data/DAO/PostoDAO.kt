package com.example.alcoolougasolina.data.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.alcoolougasolina.data.Posto
import kotlinx.coroutines.flow.Flow

@Dao
interface PostoDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(posto: Posto)

    @Update
    suspend fun update(posto: Posto)

    @Delete
    suspend fun delete(posto: Posto)

    @Query("SELECT * FROM postos ORDER BY nome ASC")
    fun getAll(): Flow<List<Posto>>

    @Query("SELECT * FROM postos WHERE id = :id")
    suspend fun getById(id: Long): Posto?

    @Query("DELETE FROM postos WHERE id = :id")
    suspend fun deleteById(id: Long)
}