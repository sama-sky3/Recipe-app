package com.example.recipeappproject.Fragment_Activity1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.recipeappproject.MainActivity
import com.example.recipeappproject.R
import com.example.recipeappproject.databinding.FragmentSignUpBinding
import com.example.recipeappproject.registerDatabaseHelper.DatabaseHelperRegister


class SignUpFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var databaseHelper: DatabaseHelperRegister

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Use the binding to inflate the layout
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        databaseHelper = DatabaseHelperRegister(requireContext())
        navController = findNavController()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSignup.setOnClickListener {
            val signupUsername = binding.editTextName.text.toString()
            val signupEmail = binding.editTextEmail.text.toString()
            val signupPassword = binding.editTextPassword.text.toString()

            signupDatabase(signupUsername, signupEmail, signupPassword)
        }

        binding.alreadyHaveAccount.setOnClickListener {
            navController.navigate(R.id.action_sign_up_Fragment_to_sign_in_Fragment)
        }
    }
    private fun signupDatabase(username: String, email: String, password: String) {
        if(username == "" || email == "" || password == ""){
            Toast.makeText(requireContext(), "Enter the missing fields", Toast.LENGTH_LONG).show()
            return
        }

        val insertedRowId = databaseHelper.insertUser(username, email, password)
        if (insertedRowId != -1L) {
            // Navigate to the home fragment
            val intent = Intent(requireContext(), MainActivity::class.java)
            requireActivity().startActivity(intent)
            requireActivity().finish()
        } else {
            Toast.makeText(requireContext(), "Signup Failed!", Toast.LENGTH_SHORT).show()
        }

    }

}
