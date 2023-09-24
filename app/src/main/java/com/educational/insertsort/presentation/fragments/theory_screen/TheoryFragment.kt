package com.educational.insertsort.presentation.fragments.theory_screen

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
import com.educational.insertsort.data.model.TheoryModel
import com.educational.insertsort.databinding.FragmentTheoryBinding
import com.educational.insertsort.domain.ViewModelTheory
import com.educational.insertsort.presentation.MainActivity.Companion.logs
import com.educational.insertsort.presentation.fragments.theory_screen.sub.CallBackClickItemTheoryRecycler
import com.educational.insertsort.presentation.fragments.theory_screen.sub.RecyclerAdapterTheory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TheoryFragment : Fragment(), CallBackClickItemTheoryRecycler {

    private lateinit var binding: FragmentTheoryBinding

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        logs(throwable)
    }

    private val recyclerAdapterTheory by lazy {
        RecyclerAdapterTheory(
            callback = this
        )
    }

    private val viewModelTheory: ViewModelTheory by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTheoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerTheory.adapter = recyclerAdapterTheory

        lifecycleScope.launch(exceptionHandler) {
            viewModelTheory.flowTheoryData.collectLatest {
                recyclerAdapterTheory.setNewList(it)
            }
        }

        binding.faqTheory.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("FAQ Розділ Теорії")
                .setMessage(
                    "Це розділ теорії в якому ви зможете ознайомитися з теоретичним" +
                            " матеріалом щодо алгоритму сортування вставками в декілька етапів," +
                            " а потім перейти до практики.\n\n Ми рекомендуємо вам проходити від" +
                            " першої частини поступово до останньої та потім перейти до практики," +
                            " проте вам нічого не заважає починати з будь-якої частини або" +
                            " одразу перейти до практики"
                )
                .setPositiveButton("Закрити") { dialog, _ ->
                    dialog.dismiss()
                }.create().show()
        }

    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigate(R.id.action_theoryFragment_to_homeFragment)
        }
    }

    override fun callbackAfterClick(model: TheoryModel) {
        findNavController().navigate(
            R.id.action_theoryFragment_to_theorySectionFragment,
            bundleOf(
                "model" to model
            )
        )
    }
}