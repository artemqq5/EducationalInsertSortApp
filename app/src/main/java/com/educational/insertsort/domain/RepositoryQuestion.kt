package com.educational.insertsort.domain

import com.educational.insertsort.data.database.AppDatabase
import com.educational.insertsort.data.model.QuestionModel
import kotlinx.coroutines.flow.Flow

interface RepositoryQuestion {

    val databaseInstance: AppDatabase

    suspend fun getQuestionsByCategory(categoryName: String): Flow<List<QuestionModel>>
}