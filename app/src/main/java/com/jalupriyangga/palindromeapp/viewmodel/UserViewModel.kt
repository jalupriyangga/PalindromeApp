package com.jalupriyangga.palindromeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jalupriyangga.palindromeapp.UserResponse
import com.jalupriyangga.palindromeapp.repository.UserRepository

class UserViewModel: ViewModel() {
    private val repository = UserRepository()

    val selectedUserName = MutableLiveData<String>()
    val inputedName = MutableLiveData<String>()
}