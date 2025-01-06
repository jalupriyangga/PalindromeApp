package com.jalupriyangga.palindromeapp.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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

class ThirdScreenFragment: BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var adapter: ThirdScreenAdapter
    private var _binding: ThirdScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UserViewModel

    private lateinit var layoutManager: LinearLayoutManager
    private var page = 1
    private var totalPage = 1
    private var isLoading = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Gunakan ViewModel yang sama dengan Activity
        viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        layoutManager = LinearLayoutManager(navController.context)
        binding.swipeRefreshLayout.setOnRefreshListener(this)

        // Pass ViewModel ke Adapter
        adapter = ThirdScreenAdapter(requireContext(), arrayListOf(), viewModel)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

//        remoteGetUser()
        remoteGetUser(false)

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = adapter.itemCount
                if (!isLoading && page < totalPage){
//                    if (visibleItemCount + pastVisibleItem < total){
//                        page++
//                        remoteGetUser(false)
//                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

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

    private fun remoteGetUser(isOnRefresh: Boolean) {
        isLoading = true
        if (!isOnRefresh) binding.progressBar.visibility = View.VISIBLE
        val parameters = HashMap<String, String>()
        parameters["page"] = page.toString()
        Handler().postDelayed({
            ApiClient.apiService.getUsers(parameters).enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    totalPage = response.body()?.total_pages!!
                    if (response.isSuccessful) {
                        response.body()?.data?.let { users ->
                            adapter.setData(users) // Pass the data to the adapter
                        }
                    }
                    binding.progressBar.visibility = View.INVISIBLE
                    isLoading = false
                    binding.swipeRefreshLayout.isRefreshing = false
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("ThirdScreenFragment", "onFailure: ${t.message}")
                }
            })
        }, 2000)

    }

//    private fun remoteGetUser() {
//        ApiClient.apiService.getUsers().enqueue(object : Callback<UserResponse> {
//            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
//                if (response.isSuccessful) {
//                    response.body()?.data?.let { users ->
//                        adapter.setData(users) // Pass the data to the adapter
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
//                Log.d("ThirdScreenFragment", "onFailure: ${t.message}")
//            }
//        })
//    }

    override fun onRefresh() {
        adapter.clear()
        if (page<totalPage) page++ else page = 1
        binding.progressBar.visibility = View.VISIBLE
//        page = 1
        remoteGetUser(true)
    }
}
