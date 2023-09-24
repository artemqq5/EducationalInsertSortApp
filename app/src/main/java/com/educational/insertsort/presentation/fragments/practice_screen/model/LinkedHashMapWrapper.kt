package com.educational.insertsort.presentation.fragments.practice_screen.model

import android.os.Parcelable
import com.educational.insertsort.data.model.QuestionModel
import kotlinx.parcelize.Parcelize

@Parcelize
class LinkedHashMapWrapper(val map: List<UserAnswerModel>) : Parcelable