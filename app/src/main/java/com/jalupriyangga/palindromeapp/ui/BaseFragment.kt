package com.jalupriyangga.palindromeapp.ui

import androidx.fragment.app.Fragment
import com.jalupriyangga.palindromeapp.MainActivity

abstract class BaseFragment: Fragment() {
    protected val navController by lazy {
        (activity as MainActivity).navController
    }


}