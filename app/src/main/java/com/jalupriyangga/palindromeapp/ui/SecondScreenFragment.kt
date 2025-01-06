package com.jalupriyangga.palindromeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jalupriyangga.palindromeapp.databinding.SecondScreenBinding

class SecondScreenFragment: BaseFragment() {

    private var _binding: SecondScreenBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarTitle.text = "Second Screen"
        binding.backButton.setOnClickListener {
            navController.popBackStack()
        }
        binding.chooseUserButton.setOnClickListener {
            val navDirection = SecondScreenFragmentDirections.actionSecondScreenFragment2ToThirdScreenFragment()
            navController.navigate(navDirection)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SecondScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}