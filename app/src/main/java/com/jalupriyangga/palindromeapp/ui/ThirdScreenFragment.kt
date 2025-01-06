package com.jalupriyangga.palindromeapp.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.jalupriyangga.palindromeapp.ApiClient
import com.jalupriyangga.palindromeapp.MainActivity
import com.jalupriyangga.palindromeapp.UserResponse
import com.jalupriyangga.palindromeapp.adapter.ThirdScreenAdapter
import com.jalupriyangga.palindromeapp.databinding.SecondScreenBinding
import com.jalupriyangga.palindromeapp.databinding.ThirdScreenBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdScreenFragment: BaseFragment() {

    private lateinit var adapter: ThirdScreenAdapter
    private var _binding: ThirdScreenBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ThirdScreenAdapter(navController.context, arrayListOf())

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun remoteGetUser () {
        ApiClient.apiService.getUsers().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    Log.d("ThirdScreenFragment", "User  response: $userResponse") // Log the response
                    userResponse?.data?.let { users ->
                        setDataToAdapter(users) // Pass the list of UserResponse.Data
                    }
                } else {
                    Log.d("ThirdScreenFragment", "Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("ThirdScreenFragment", "onFailure: ${t.message}")
            }
        })
    }

    fun setDataToAdapter(data: List<UserResponse.Data>) {
        adapter.setData(data) // Update the adapter with the list of UserResponse.Data
    }
}