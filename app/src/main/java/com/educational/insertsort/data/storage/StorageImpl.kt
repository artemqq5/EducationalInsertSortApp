package com.educational.insertsort.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class StorageImpl(private val context: Context) : Storage {

    companion object {
        fun isSameWeek(currentDate: Calendar, lastSavedDate: Calendar): Boolean {
            return currentDate.get(Calendar.WEEK_OF_YEAR) == lastSavedDate.get(Calendar.WEEK_OF_YEAR) &&
                    currentDate.get(Calendar.YEAR) == lastSavedDate.get(Calendar.YEAR)
        }

        fun currentDay(): String {
            return SimpleDateFormat("EEEE", Locale.ENGLISH)
                .format(Date()).lowercase()
        }

        fun currentDate() = Calendar.getInstance().apply {
            firstDayOfWeek = Calendar.MONDAY
        }

        fun String.lastSaveCalendar() = Calendar.getInstance().apply {
            time = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .parse(this@lastSaveCalendar)!!
            firstDayOfWeek = Calendar.MONDAY
        }
    }

    private val FIRST_START = booleanPreferencesKey("firstStart")
    private val LAST_SAVE_DATA = stringPreferencesKey("last_save_data")

    override val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


    override suspend fun firstUserStart(): Boolean {
        return context.dataStore.data.first()[FIRST_START] ?: run {
            context.dataStore.edit { it[FIRST_START] = false }
            true
        }
    }

    override suspend fun getLastSaveDataStatistic(): String {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return context.dataStore.data.first()[LAST_SAVE_DATA] ?: today
    }

    override suspend fun setLastSaveDataStatistic(date: String?) {
        context.dataStore.edit {
            it[LAST_SAVE_DATA] = date
                ?: SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        }
    }

    override suspend fun getDataStatisticList(): Flow<FloatArray> {
        return context.dataStore.data.map {
            floatArrayOf(
                it[floatPreferencesKey("monday")] ?: 0f,
                it[floatPreferencesKey("tuesday")] ?: 0f,
                it[floatPreferencesKey("wednesday")] ?: 0f,
                it[floatPreferencesKey("thursday")] ?: 0f,
                it[floatPreferencesKey("friday")] ?: 0f,
                it[floatPreferencesKey("saturday")] ?: 0f,
                it[floatPreferencesKey("sunday")] ?: 0f
            )
        }
    }

    override suspend fun updateDataStatisticDay(day: String, value: Float) {
        context.dataStore.edit {
            val oldValue = it[floatPreferencesKey(day)] ?: 0f
            it[floatPreferencesKey(day)] = oldValue + value
        }
    }

    override suspend fun resetStatisticListData() {
        context.dataStore.edit { pref ->
            arrayOf(
                "monday", "tuesday", "wednesday", "thursday",
                "friday", "saturday", "sunday"
            ).forEach {
                pref[floatPreferencesKey(it)] = 0f
            }
        }

    }

}