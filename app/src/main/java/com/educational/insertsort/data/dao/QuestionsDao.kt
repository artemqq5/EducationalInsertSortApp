package com.educational.insertsort.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.educational.insertsort.data.model.QuestionModel
import kotlinx.coroutines.flow.Flow

@Dao

interface QuestionsDao {

    @Query("SELECT * FROM questions WHERE category = :categoryQuestion")
    fun getAll(categoryQuestion: String): Flow<List<QuestionModel>>

}