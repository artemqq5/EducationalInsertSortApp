package com.educational.insertsort.presentation.fragments.practice_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.educational.insertsort.R
import com.educational.insertsort.data.model.PracticeModel
import com.educational.insertsort.databinding.FragmentPracticeBinding
import com.educational.insertsort.domain.ViewModelPractice
import com.educational.insertsort.presentation.MainActivity.Companion.logs
import com.educational.insertsort.presentation.fragments.practice_screen.sub.CallBackClickItemPracticeRecycler
import com.educational.insertsort.presentation.fragments.practice_screen.sub.RecyclerAdapterPractice
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PracticeFragment : Fragment(), CallBackClickItemPracticeRecycler {

    private lateinit var binding: FragmentPracticeBinding

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        logs(throwable)
    }

    private val recyclerAdapterPractice by lazy {
        RecyclerAdapterPractice(callback = this)
    }

    private val viewModelPractice: ViewModelPractice by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPracticeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerPractice.adapter = recyclerAdapterPractice

        lifecycleScope.launch(exceptionHandler) {
            viewModelPractice.flowPracticeData.collectLatest {
                recyclerAdapterPractice.setNewList(it)
            }
        }

        binding.faqPractice.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("FAQ Розділ Практики")
                .setMessage(
                    "Це розділ практики, де ви можете перевірити отриманні знання з теорії," +
                            " після проходження тестів ви зможете переглянути де ви зробили" +
                            " помилку та дізнатися правильну відповідь з детальним поясненням"
                )
                .setPositiveButton("Закрити") { dialog, _ ->
                    dialog.dismiss()
                }.create().show()
        }

    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigate(R.id.action_practiceFragment_to_homeFragment)
        }
    }

    override fun callbackAfterClick(model: PracticeModel) {
        findNavController().navigate(
            R.id.action_practiceFragment_to_practiceSectionFragment, bundleOf(
                "model" to model
            )
        )
    }
}