package com.educational.insertsort.domain

import com.educational.insertsort.data.database.AppDatabase
import com.educational.insertsort.data.model.TheoryModel
import kotlinx.coroutines.flow.Flow

interface RepositoryTheory {

    val databaseInstance: AppDatabase

    suspend fun getAllTheory(): Flow<List<TheoryModel>>
    suspend fun getCompletedTheory(): Flow<List<TheoryModel>>
    suspend fun updateTheoryComplete(title: String)
    suspend fun resetTheoryData()

}