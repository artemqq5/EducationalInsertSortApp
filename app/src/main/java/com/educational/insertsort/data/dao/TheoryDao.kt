package com.educational.insertsort.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.educational.insertsort.data.model.TheoryModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TheoryDao {
    @Query("SELECT * FROM theory")
    fun getAll(): Flow<List<TheoryModel>>

    @Query("SELECT * FROM theory WHERE complete = 1")
    fun getCompletedTheory(): Flow<List<TheoryModel>>

    @Query("UPDATE theory SET complete = 1 WHERE title = :theoryTitle")
    suspend fun updateTheory(theoryTitle: String)

    @Query("UPDATE theory SET complete = 0")
    suspend fun resetTheoryData()
}