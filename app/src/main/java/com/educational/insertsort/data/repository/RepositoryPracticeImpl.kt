package com.educational.insertsort.data.repository

import android.content.Context
import androidx.room.Room
import com.educational.insertsort.data.database.AppDatabase
import com.educational.insertsort.data.model.PracticeModel
import com.educational.insertsort.domain.RepositoryPractice
import kotlinx.coroutines.flow.Flow

class RepositoryPracticeImpl(context: Context) : RepositoryPractice {

    override val databaseInstance: AppDatabase by lazy {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "eis_db"
        ).createFromAsset("eis_db.db").build()
    }


    override suspend fun getAllPractice(): Flow<List<PracticeModel>> {
        return databaseInstance.practiceDao().getAll()
    }

    override suspend fun getCompletedPractice(): Flow<List<PracticeModel>> {
        return databaseInstance.practiceDao().getCompletedPractice()
    }

    override suspend fun updatePracticeComplete(title: String) {
        return databaseInstance.practiceDao().updatePractice(title)
    }

    override suspend fun resetPracticeData() {
        return databaseInstance.practiceDao().resetPracticeData()
    }

}