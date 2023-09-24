package com.educational.insertsort.presentation.fragments.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.educational.insertsort.R
import com.educational.insertsort.data.storage.StorageImpl.Companion.currentDate
import com.educational.insertsort.data.storage.StorageImpl.Companion.isSameWeek
import com.educational.insertsort.data.storage.StorageImpl.Companion.lastSaveCalendar
import com.educational.insertsort.databinding.FragmentHomeBinding
import com.educational.insertsort.domain.ViewModelPractice
import com.educational.insertsort.domain.ViewModelStatistic
import com.educational.insertsort.domain.ViewModelTheory
import com.educational.insertsort.presentation.MainActivity.Companion.logs
import com.educational.insertsort.presentation.fragments.home_screen.sub.ListOfTipsHome.listOfTipsToLearn
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        logs(throwable)
    }

    private val snackbarToExit by lazy {
        Snackbar.make(
            binding.root,
            resources.getString(R.string.exit_snackbar),
            Snackbar.LENGTH_SHORT
        )
    }

    private val viewModelTheory: ViewModelTheory by viewModel()
    private val viewModelPractice: ViewModelPractice by viewModel()
    private val viewModelStatistic: ViewModelStatistic by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch(exceptionHandler) {
            viewModelTheory.flowCompletedTheoryData.collectLatest {
                var value = String.format("%.2f", (100f / 5) * it.size)
                    .replace(",", ".").toFloat()
                if (value > 100f) value = 100f
                binding.circularProgressBarTheory.progress = value
            }
        }

        lifecycleScope.launch(exceptionHandler) {
            viewModelPractice.flowPracticeCompleteData.collectLatest {
                var value = String.format("%.2f", (100f / 3) * it.size)
                    .replace(",", ".").toFloat()
                if (value > 100f) value = 100f
                binding.circularProgressBarPractice.progress = value
            }
        }

        lifecycleScope.launch(exceptionHandler) {
            viewModelStatistic.flowTestStatisticData.collectLatest { list ->
                binding.weekBarChartView.dayValues = list
            }
        }



        binding.resetProgressButton.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Видалити прогрес")
                .setMessage("Ви дійсно бажаєте видалити весь здобутий прогрес?")
                .setPositiveButton("Видалити") { dialog, _ ->
                    dialog.dismiss()
                    viewModelTheory.resetTheoryData()
                    viewModelPractice.resetPracticeCompleteData()
                    viewModelStatistic.clearTestStatisticData()
                }
                .setNegativeButton("Скасувати") { dialog, _ ->
                    dialog.cancel()
                }.create().show()
        }

        binding.theoryPart.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_theoryFragment)
        }

        binding.practicePart.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_practiceFragment)
        }

    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch(exceptionHandler) {
            val lastSavedDate = withContext(Dispatchers.IO) {
                viewModelStatistic.getLastSavedDate()
            }

            if (!isSameWeek(currentDate(), lastSavedDate.lastSaveCalendar())) {
                viewModelStatistic.setLastSavedDate(null)
                viewModelStatistic.clearTestStatisticData()
            }
        }


        // exit from app if user dabble press back in 2 seconds
        var backTimeExit = 0L

        requireActivity().onBackPressedDispatcher.addCallback {
            if (backTimeExit + 2000L >= System.currentTimeMillis()) {
                requireActivity().finish()
            } else {
                snackbarToExit.show()
                backTimeExit = System.currentTimeMillis()
            }
        }

        // set tips to learn
        binding.tipsTextField.text = resources.getString(listOfTipsToLearn.random())
    }

}