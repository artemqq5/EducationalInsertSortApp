package com.educational.insertsort.data.repository

import android.content.Context
import androidx.room.Room
import com.educational.insertsort.data.database.AppDatabase
import com.educational.insertsort.data.model.QuestionModel
import com.educational.insertsort.domain.RepositoryQuestion
import kotlinx.coroutines.flow.Flow

class RepositoryQuestionImpl(context: Context): RepositoryQuestion {

    override val databaseInstance: AppDatabase by lazy {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "eis_db"
        ).createFromAsset("eis_db.db").build()
    }


    override suspend fun getQuestionsByCategory(categoryName: String): Flow<List<QuestionModel>> {
        return databaseInstance.questionsDao().getAll(categoryQuestion = categoryName)
    }

}