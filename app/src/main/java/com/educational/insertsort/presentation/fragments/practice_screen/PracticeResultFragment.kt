package com.educational.insertsort.presentation.fragments.practice_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.educational.insertsort.R
import com.educational.insertsort.data.storage.StorageImpl.Companion.currentDate
import com.educational.insertsort.data.storage.StorageImpl.Companion.currentDay
import com.educational.insertsort.data.storage.StorageImpl.Companion.isSameWeek
import com.educational.insertsort.data.storage.StorageImpl.Companion.lastSaveCalendar
import com.educational.insertsort.databinding.FragmentPracticeResultBinding
import com.educational.insertsort.domain.ViewModelPractice
import com.educational.insertsort.domain.ViewModelStatistic
import com.educational.insertsort.presentation.MainActivity.Companion.logs
import com.educational.insertsort.presentation.fragments.practice_screen.sub.RecyclerAdapterPracticeResult
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class PracticeResultFragment : Fragment() {

    private lateinit var binding: FragmentPracticeResultBinding

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        logs(throwable)
    }

    private val args: PracticeResultFragmentArgs by navArgs()
    private val recyclerAdapterPracticeResult by lazy {
        RecyclerAdapterPracticeResult()
    }

    private val viewModelPractice: ViewModelPractice by viewModel()
    private val viewModelStatistic: ViewModelStatistic by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPracticeResultBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = args.result.map
        val rightAnswers = list.filter { it.state == true }

        // update practice complete progress bar in HOME Fragment
        if (rightAnswers.size == list.size)
            viewModelPractice.updatePracticeComplete(list[0].questionModel.category)

        // update statistic of successful test passed
        lifecycleScope.launch(exceptionHandler) {
            val lastSavedDate = withContext(Dispatchers.IO) {
                viewModelStatistic.getLastSavedDate()
            }
//            logs(
//                "RESULT: \n" +
//                        "is same week ${
//                            isSameWeek(
//                                currentDate(),
//                                lastSavedDate.lastSaveCalendar()
//                            )
//                        }"
//            )

            if (!isSameWeek(currentDate(), lastSavedDate.lastSaveCalendar())) {
                viewModelStatistic.clearTestStatisticData()
                viewModelStatistic.setLastSavedDate(null)
            }

            viewModelStatistic.updateTestStatisticData(
                currentDay(),
                rightAnswers.size.toFloat()
            )

        }


        binding.countRightAnswer.text =
            resources.getString(
                R.string.count_right_answer,
                rightAnswers.size.toString(),
                list.size.toString()
            )

        binding.recyclerResult.adapter = recyclerAdapterPracticeResult
        recyclerAdapterPracticeResult.setNewList(list)

        binding.goBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_practiceResultFragment_to_practiceFragment)
        }

    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigate(R.id.action_practiceResultFragment_to_practiceFragment)
        }
    }
}