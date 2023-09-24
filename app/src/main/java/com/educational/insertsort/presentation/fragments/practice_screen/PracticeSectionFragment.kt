package com.educational.insertsort.presentation.fragments.practice_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.educational.insertsort.R
import com.educational.insertsort.data.model.QuestionModel
import com.educational.insertsort.data.storage.StorageImpl
import com.educational.insertsort.data.storage.StorageImpl.Companion.currentDay
import com.educational.insertsort.data.storage.StorageImpl.Companion.lastSaveCalendar
import com.educational.insertsort.databinding.FragmentPracticeSectionBinding
import com.educational.insertsort.domain.ViewModelPractice
import com.educational.insertsort.domain.ViewModelQuestion
import com.educational.insertsort.domain.ViewModelStatistic
import com.educational.insertsort.presentation.MainActivity.Companion.logs
import com.educational.insertsort.presentation.fragments.practice_screen.model.LinkedHashMapWrapper
import com.educational.insertsort.presentation.fragments.practice_screen.model.UserAnswerModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PracticeSectionFragment : Fragment() {

    private lateinit var binding: FragmentPracticeSectionBinding

    private val args: PracticeSectionFragmentArgs by navArgs()

    private val snackbarToGoBack by lazy {
        Snackbar.make(
            binding.root,
            resources.getString(R.string.go_back_snackbar),
            Snackbar.LENGTH_SHORT
        )
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        logs(throwable)
    }

    private val viewModelQuestion: ViewModelQuestion by viewModel()

    private val listOfQuestion = mutableListOf<QuestionModel>()
    private val resultListQuestion = mutableListOf<UserAnswerModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPracticeSectionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = args.model

        binding.topAppBar.title = model.title

        lifecycleScope.launch(exceptionHandler + Dispatchers.Main) {
            viewModelQuestion.flowQuestionData(model.title).collectLatest {
                it.forEach { question -> listOfQuestion.add(question) }
                setNextQuestion()

                binding.firstRadioAnswer.isEnabled = true
                binding.secondRadioAnswer.isEnabled = true
                binding.thirdRadioAnswer.isEnabled = true
                binding.nextQuestionButton.isEnabled = true
            }
        }

        binding.nextQuestionButton.setOnClickListener {

            binding.radioGroup.checkedRadioButtonId.also {
                if (it != -1) {
                    (view.findViewById<RadioButton>(it).text).let { answer ->
                        resultListQuestion.last().userAnswer = answer.toString()
                        resultListQuestion.last().state =
                            answer == resultListQuestion.last().questionModel.rightAnswer
                    }

                }
            }


            binding.radioGroup.clearCheck()
            setNextQuestion()
        }

        binding.topAppBar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_practiceSectionFragment_to_practiceFragment)
        }

    }

    private fun setNextQuestion() {
        if (listOfQuestion.isNotEmpty()) {
            val question = listOfQuestion.random()

            resultListQuestion.add(resultListQuestion.size, UserAnswerModel(question))

            binding.topAppBar.subtitle = resources.getString(
                R.string.current_question_num,
                (args.model.countQuestions - (listOfQuestion.size - 1)).toString(),
                args.model.countQuestions.toString()
            )
            val answerList = arrayListOf(question.answer1, question.answer2, question.answer3)
            binding.questionText.text = question.question
            binding.firstRadioAnswer.text = answerList.getRandomAndDelete()
            binding.secondRadioAnswer.text = answerList.getRandomAndDelete()
            binding.thirdRadioAnswer.text = answerList.getRandomAndDelete()

            listOfQuestion.remove(question)
        } else {

            findNavController().navigate(
                R.id.action_practiceSectionFragment_to_practiceResultFragment,
                bundleOf(
                    "result" to LinkedHashMapWrapper(resultListQuestion)
                )
            )
        }
    }

    private fun <T> MutableList<T>.getRandomAndDelete(): T {
        return this.random().also {
            this.remove(it)
        }
    }

    override fun onResume() {
        super.onResume()
        // go back if user dabble press back in 2 seconds
        var backTimeExit = 0L

        requireActivity().onBackPressedDispatcher.addCallback {
            if (backTimeExit + 2000L >= System.currentTimeMillis()) {
                findNavController().navigate(R.id.action_practiceSectionFragment_to_practiceFragment)

            } else {
                snackbarToGoBack.show()
                backTimeExit = System.currentTimeMillis()
            }
        }
    }
}