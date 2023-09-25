package com.educational.insertsort.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educational.insertsort.data.storage.Storage
import com.educational.insertsort.presentation.MainActivity.Companion.logs
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ViewModelStatistic(private val storage: Storage) : ViewModel() {

    private val excHandler = CoroutineExceptionHandler { _, throwable ->
        logs(throwable)
    }

    val flowTestStatisticData: Flow<FloatArray> by lazy {
        callbackFlow { storage.getDataStatisticList().collectLatest { send(it) } }
    }

    fun clearTestStatisticData() {
        viewModelScope.launch(Dispatchers.IO + excHandler) {
            storage.resetStatisticListData()
            storage.setLastSaveDataStatistic()
        }
    }

    fun updateTestStatisticData(day: String, value: Float) {
        viewModelScope.launch(Dispatchers.IO + excHandler) {
            storage.updateDataStatisticDay(day = day, value = value)
            storage.setLastSaveDataStatistic()
        }
    }

    suspend fun getLastSavedDate(): String {
        return storage.getLastSaveDataStatistic()
    }

    fun setLastSavedDate() {
        viewModelScope.launch(Dispatchers.IO + excHandler) {
            storage.setLastSaveDataStatistic()
        }
    }
}