package com.educational.insertsort.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educational.insertsort.data.model.PracticeModel
import com.educational.insertsort.presentation.MainActivity.Companion.logs
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ViewModelPractice(private val repository: RepositoryPractice) : ViewModel() {

    private val excHandler = CoroutineExceptionHandler { _, throwable ->
        logs(throwable)
    }

    val flowPracticeData: Flow<List<PracticeModel>> by lazy {
        callbackFlow { repository.getAllPractice().collectLatest { send(it) } }
    }

    val flowPracticeCompleteData: Flow<List<PracticeModel>> by lazy {
        callbackFlow { repository.getCompletedPractice().collectLatest { send(it) } }
    }

    fun updatePracticeComplete(title: String) {
        viewModelScope.launch(Dispatchers.IO + excHandler) {
            repository.updatePracticeComplete(title = title)
        }
    }

    fun resetPracticeCompleteData() {
        viewModelScope.launch(Dispatchers.IO + excHandler) {
            repository.resetPracticeData()
        }
    }

}