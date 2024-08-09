package com.example.recipeappproject.Fragment_Activity1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.recipeappproject.databinding.FragmentSignUpBinding
import com.example.recipeappproject.registerDatabaseHelper.DatabaseHelperRegister


class Sign_up_Fragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var databaseHelper: DatabaseHelperRegister

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Use the binding to inflate the layout
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        Handler(Looper.getMainLooper()).postDelayed({
        }, 6000)

        databaseHelper = DatabaseHelperRegister(requireContext())

        binding.buttonSignup.setOnClickListener {
            binding.editTextName.text?.clear()
            binding.editTextEmail.text?.clear()
            binding.editTextPassword.text?.clear()

            val signupUsername = binding.editTextName.text.toString()
            val signupEmail = binding.editTextEmail.text.toString()
            val signupPassword = binding.editTextPassword.text.toString()

            signupDatabase(signupUsername, signupEmail, signupPassword)
        }

        return binding.root
    }

    private fun signupDatabase(username: String, email: String, password: String) {
        val insertedRowId = databaseHelper.insertUser(username, email, password)

        if (insertedRowId != -1L) {
            Toast.makeText(requireContext(), "Signup Successful", Toast.LENGTH_SHORT).show()
            // Navigate to the home fragment
           // findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
        } else {
            Toast.makeText(requireContext(), "Signup Failed!", Toast.LENGTH_SHORT).show()
        }
    }
}
