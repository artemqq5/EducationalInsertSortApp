package com.educational.insertsort.domain

import androidx.lifecycle.ViewModel
import com.educational.insertsort.data.model.QuestionModel
import com.educational.insertsort.presentation.MainActivity.Companion.logs
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest

class ViewModelQuestion(private val repository: RepositoryQuestion) : ViewModel() {

    private val excHandler = CoroutineExceptionHandler { _, throwable ->
        logs(throwable)
    }

    val flowQuestionData: (String) -> Flow<List<QuestionModel>> = { category ->
        callbackFlow { repository.getQuestionsByCategory(category).collectLatest { send(it) } }
    }

}