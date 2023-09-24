package com.educational.insertsort.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface Storage {

    val Context.dataStore: DataStore<Preferences>

    suspend fun firstUserStart(): Boolean
    suspend fun getLastSaveDataStatistic(): String
    suspend fun setLastSaveDataStatistic(date: String?)

    suspend fun getDataStatisticList(): Flow<FloatArray>
    suspend fun updateDataStatisticDay(day: String, value: Float)
    suspend fun resetStatisticListData()


}