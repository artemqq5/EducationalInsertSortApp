package com.educational.insertsort.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.educational.insertsort.data.dao.PracticeDao
import com.educational.insertsort.data.dao.QuestionsDao
import com.educational.insertsort.data.dao.TheoryDao
import com.educational.insertsort.data.model.PracticeModel
import com.educational.insertsort.data.model.QuestionModel
import com.educational.insertsort.data.model.TheoryModel

@Database(
    entities = [
        TheoryModel::class,
        PracticeModel::class,
        QuestionModel::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun theoryDao(): TheoryDao
    abstract fun practiceDao(): PracticeDao
    abstract fun questionsDao(): QuestionsDao

}