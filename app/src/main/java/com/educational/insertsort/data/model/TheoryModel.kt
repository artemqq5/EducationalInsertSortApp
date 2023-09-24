package com.educational.insertsort.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "theory")
data class TheoryModel(
    @PrimaryKey @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "desc") val desc: String,
    @ColumnInfo(name = "level") val level: String,
    @ColumnInfo(name = "complete") val complete: Boolean,
) : Parcelable
