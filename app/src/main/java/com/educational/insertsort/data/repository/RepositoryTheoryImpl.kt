package com.educational.insertsort.data.repository

import android.content.Context
import androidx.room.Room
import com.educational.insertsort.data.database.AppDatabase
import com.educational.insertsort.data.model.TheoryModel
import com.educational.insertsort.domain.RepositoryTheory
import kotlinx.coroutines.flow.Flow

class RepositoryTheoryImpl(context: Context): RepositoryTheory {

    override val databaseInstance: AppDatabase by lazy {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "eis_db"
        ).createFromAsset("eis_db.db").build()
    }


    override suspend fun getAllTheory(): Flow<List<TheoryModel>> {
        return databaseInstance.theoryDao().getAll()
    }

    override suspend fun getCompletedTheory(): Flow<List<TheoryModel>> {
        return databaseInstance.theoryDao().getCompletedTheory()
    }

    override suspend fun updateTheoryComplete(title: String) {
        databaseInstance.theoryDao().updateTheory(theoryTitle = title)
    }

    override suspend fun resetTheoryData() {
        databaseInstance.theoryDao().resetTheoryData()
    }

}