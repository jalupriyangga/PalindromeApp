package com.jalupriyangga.palindromeapp.viewmodel

import androidx.lifecycle.ViewModel
import com.jalupriyangga.palindromeapp.repository.UserRepository

class UserViewModel: ViewModel() {
    private val repository = UserRepository()

}