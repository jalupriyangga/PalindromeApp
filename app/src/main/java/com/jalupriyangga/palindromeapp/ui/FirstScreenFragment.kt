package com.jalupriyangga.palindromeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jalupriyangga.palindromeapp.databinding.FirstScreenBinding

class FirstScreenFragment: BaseFragment() {

private var _binding: FirstScreenBinding? = null
private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener {
            val navDirection = FirstScreenFragmentDirections.actionFirstScreenFragmentToSecondScreenFragment2()
            navController.navigate(navDirection)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FirstScreenBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}