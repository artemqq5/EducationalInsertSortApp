package com.educational.insertsort.domain

import com.educational.insertsort.data.database.AppDatabase
import com.educational.insertsort.data.model.PracticeModel
import kotlinx.coroutines.flow.Flow

interface RepositoryPractice {

    val databaseInstance: AppDatabase


    suspend fun getAllPractice(): Flow<List<PracticeModel>>
    suspend fun getCompletedPractice(): Flow<List<PracticeModel>>
    suspend fun updatePracticeComplete(title: String)
    suspend fun resetPracticeData()

}