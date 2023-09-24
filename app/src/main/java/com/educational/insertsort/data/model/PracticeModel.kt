package com.educational.insertsort.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "practice")
data class PracticeModel(
    @PrimaryKey @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "level") val level: String,
    @ColumnInfo(name = "difficulty") val difficulty: Int,
    @ColumnInfo(name = "complete") val complete: Boolean,
    @ColumnInfo(name = "count_questions") val countQuestions: Int,
) : Parcelable
