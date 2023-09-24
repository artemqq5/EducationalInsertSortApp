package com.educational.insertsort.presentation.fragments.practice_screen.model

import android.os.Parcelable
import com.educational.insertsort.data.model.QuestionModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserAnswerModel(
    val questionModel: QuestionModel,
    var userAnswer: String? = null,
    var state: Boolean? = null
) : Parcelable