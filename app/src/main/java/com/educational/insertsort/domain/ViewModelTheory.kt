package com.educational.insertsort.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.educational.insertsort.data.model.TheoryModel
import com.educational.insertsort.presentation.MainActivity.Companion.logs
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ViewModelTheory(
    private val repository: RepositoryTheory
) : ViewModel() {

    private val excHandler = CoroutineExceptionHandler { _, throwable ->
        logs(throwable)
    }

    val flowTheoryData: Flow<List<TheoryModel>> by lazy {
        callbackFlow { repository.getAllTheory().collectLatest { send(it) } }
    }

    val flowCompletedTheoryData: Flow<List<TheoryModel>> by lazy {
        callbackFlow { repository.getCompletedTheory().collectLatest { send(it) } }
    }

    fun setCompleteTheoryPart(title: String) {
        viewModelScope.launch(Dispatchers.IO + excHandler) {
            repository.updateTheoryComplete(title = title)
        }
    }

    fun resetTheoryData() {
        viewModelScope.launch(Dispatchers.IO + excHandler) {
            repository.resetTheoryData()
        }
    }

}