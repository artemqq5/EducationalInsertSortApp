package com.educational.insertsort.presentation.fragments.hello_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.educational.insertsort.R
import com.educational.insertsort.databinding.FragmentHelloBinding
import com.google.android.material.snackbar.Snackbar

class HelloFragment : Fragment() {

    private lateinit var binding: FragmentHelloBinding

    private val snackbarToExit by lazy {
        Snackbar.make(
            binding.root,
            resources.getString(R.string.exit_snackbar),
            Snackbar.LENGTH_SHORT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHelloBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_helloFragment_to_homeFragment)
        }

    }

    override fun onResume() {
        super.onResume()
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
    }

}