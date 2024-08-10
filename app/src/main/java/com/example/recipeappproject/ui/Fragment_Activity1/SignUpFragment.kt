package com.example.recipeappproject.ui.Fragment_Activity1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.recipeappproject.MainActivity
import com.example.recipeappproject.R
import com.example.recipeappproject.databinding.FragmentSignUpBinding
import com.example.recipeappproject.viewmodels.RegisterViewModel
import com.example.recipeappproject.viewmodels.RegisterViewModelFactory
import com.example.recipeappproject.database.RegisterDatabase
import com.example.recipeappproject.Repo.RegisterRepository

class SignUpFragment : Fragment() {

    private val SETTING_PREFRENCE = "com.example.sharedstorageapplication"
    private lateinit var sharedPreferences: SharedPreferences
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory(
            RegisterRepository(
                RegisterDatabase.getInstance(requireContext()).registerDatabaseDao
            ),
            requireActivity().application
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignup.setOnClickListener {
            val username = binding.editTextName.text.toString()
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            signUp(username, email, password)
        }

        binding.alreadyHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_sign_up_Fragment_to_sign_in_Fragment)
        }
    }

    private fun signUp(username: String, email: String, password: String) {
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.signUp(username, email, password)
        viewModel.navigateToHome.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                Toast.makeText(requireContext(), "Sign Up Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), MainActivity::class.java)
                sharedPreferences = requireActivity().getSharedPreferences(SETTING_PREFRENCE, MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putString("loginEmail", email)
                    putString("loginPassword", password)
                    apply()
                }
                requireActivity().startActivity(intent)
                requireActivity().finish()
            }
        }

        viewModel.errorToast.observe(viewLifecycleOwner) { showError ->
            if (showError) {
                Toast.makeText(requireContext(), "User already exists", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear binding to avoid memory leaks
    }
}
