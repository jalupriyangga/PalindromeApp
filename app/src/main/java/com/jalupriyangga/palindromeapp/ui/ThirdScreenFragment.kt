package com.jalupriyangga.palindromeapp.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.jalupriyangga.palindromeapp.ApiClient
import com.jalupriyangga.palindromeapp.MainActivity
import com.jalupriyangga.palindromeapp.UserResponse
import com.jalupriyangga.palindromeapp.adapter.ThirdScreenAdapter
import com.jalupriyangga.palindromeapp.databinding.SecondScreenBinding
import com.jalupriyangga.palindromeapp.databinding.ThirdScreenBinding
import com.jalupriyangga.palindromeapp.viewmodel.UserViewModel
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdScreenFragment: BaseFragment() {

    private lateinit var adapter: ThirdScreenAdapter
    private var _binding: ThirdScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Gunakan ViewModel yang sama dengan Activity
        viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        // Pass ViewModel ke Adapter
        adapter = ThirdScreenAdapter(requireContext(), arrayListOf(), viewModel)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

        remoteGetUser()

        binding.toolbarTitle.text = "Third Screen"
        binding.backButton.setOnClickListener {
            navController.popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ThirdScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun remoteGetUser() {
        ApiClient.apiService.getUsers().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    response.body()?.data?.let { users ->
                        adapter.setData(users) // Pass the data to the adapter
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("ThirdScreenFragment", "onFailure: ${t.message}")
            }
        })
    }
}
