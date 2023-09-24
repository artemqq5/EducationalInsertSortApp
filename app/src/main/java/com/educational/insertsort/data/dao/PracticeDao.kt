package com.educational.insertsort.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.educational.insertsort.data.model.PracticeModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PracticeDao {

    @Query("SELECT * FROM practice")
    fun getAll(): Flow<List<PracticeModel>>

    @Query("SELECT * FROM practice WHERE complete = 1")
    fun getCompletedPractice(): Flow<List<PracticeModel>>

    @Query("UPDATE practice SET complete = 1 WHERE title = :practiceTitle")
    suspend fun updatePractice(practiceTitle: String)

    @Query("UPDATE practice SET complete = 0")
    suspend fun resetPracticeData()

}