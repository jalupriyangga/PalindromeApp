package com.jalupriyangga.palindromeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jalupriyangga.palindromeapp.databinding.SecondScreenBinding
import com.jalupriyangga.palindromeapp.viewmodel.UserViewModel

class SecondScreenFragment : BaseFragment() {

    private lateinit var viewModel: UserViewModel

    private var _binding: SecondScreenBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarTitle.text = "Second Screen"
        binding.backButton.setOnClickListener {
            navController.popBackStack()
        }
        binding.chooseUserButton.setOnClickListener {
            val navDirection =
                SecondScreenFragmentDirections.actionSecondScreenFragment2ToThirdScreenFragment()
            navController.navigate(navDirection)
        }

        // Initialize ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        // Observe the selectedUser Name LiveData
        viewModel.selectedUserName.observe(viewLifecycleOwner) { userName ->
            if (userName != null) {
                binding.nameTextView.text = userName
                binding.selectedUsernameTextView.text = userName
            }
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