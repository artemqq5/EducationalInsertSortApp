package com.educational.insertsort.presentation.fragments.theory_screen

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.educational.insertsort.R
import com.educational.insertsort.databinding.FragmentTheorySectionBinding
import com.educational.insertsort.domain.ViewModelTheory
import com.educational.insertsort.presentation.MainActivity.Companion.logs
import kotlinx.coroutines.CoroutineExceptionHandler
import org.koin.androidx.viewmodel.ext.android.viewModel

class TheorySectionFragment : Fragment() {

    private lateinit var binding: FragmentTheorySectionBinding

    val args: TheorySectionFragmentArgs by navArgs()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        logs(throwable)
    }

    private val viewModelTheory: ViewModelTheory by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTheorySectionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val modelOfTheory = args.model

        val convertedDesc = modelOfTheory.desc
            .replace("\\n", "\n")
            .replace("\\t", "\t")

        binding.topAppBar.title = modelOfTheory.title
        binding.desc.text = Html.fromHtml(convertedDesc, Html.FROM_HTML_MODE_LEGACY)

        binding.completeTheoryButton.setOnClickListener {
            viewModelTheory.setCompleteTheoryPart(modelOfTheory.title)
            findNavController().navigate(R.id.action_theorySectionFragment_to_theoryFragment)
        }

        binding.topAppBar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_theorySectionFragment_to_theoryFragment)
        }

    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigate(R.id.action_theorySectionFragment_to_theoryFragment)
        }
    }
}

