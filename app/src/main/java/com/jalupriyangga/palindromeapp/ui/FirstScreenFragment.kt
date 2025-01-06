package com.jalupriyangga.palindromeapp.ui

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jalupriyangga.palindromeapp.R
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
        binding.checkButton.setOnClickListener {
            val palindromeAlert = AlertDialog.Builder(requireContext())
                .setTitle("Check Palindrome")
                .setMessage("You sure you want to check the word?")
                .setPositiveButton("Check") { dialog, which ->
                    val input = binding.palindromeInput.text.toString()
                    val word = input!!.toLowerCase().replace("\\s".toRegex(), "")
                    val reversed = word.reversed()
                    if (word == reversed) {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Result")
                            .setMessage("The word is a palindrome")
                            .setPositiveButton("Ok") { dialog, which ->
                                dialog.dismiss()
                            }
                            .show()
                    } else {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Result")
                            .setMessage("The word is not a palindrome")
                            .setPositiveButton("Ok") { dialog, which ->
                                dialog.dismiss()
                            }
                            .show()
                    }
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .create()

            palindromeAlert.setOnShowListener {
                val negativeButton = palindromeAlert.getButton(AlertDialog.BUTTON_NEGATIVE)
                val spannableString = SpannableString(negativeButton.text)
                spannableString.setSpan(ForegroundColorSpan(Color.RED), 0, spannableString.length, 0)
                negativeButton.text = spannableString
            }

            palindromeAlert.show()
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