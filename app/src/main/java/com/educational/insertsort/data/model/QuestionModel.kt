package com.educational.insertsort.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "questions")
data class QuestionModel(
    @PrimaryKey @ColumnInfo(name = "question") val question: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "answer1") val answer1: String,
    @ColumnInfo(name = "answer2") val answer2: String,
    @ColumnInfo(name = "answer3") val answer3: String,
    @ColumnInfo(name = "right_answer") val rightAnswer: String,
    @ColumnInfo(name = "desc") val desc: String,
) : Parcelable
