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
import com.example.recipeappproject.databinding.FragmentSignInBinding
import com.example.recipeappproject.viewmodels.RegisterViewModel
import com.example.recipeappproject.viewmodels.RegisterViewModelFactory
import com.example.recipeappproject.database.RegisterDatabase
import com.example.recipeappproject.Repo.RegisterRepository

class SignInFragment : Fragment() {

    private val SETTING_PREFRENCE = "com.example.sharedstorageapplication"
    private lateinit var sharedPreferences: SharedPreferences
    private var _binding: FragmentSignInBinding? = null
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
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogIn.setOnClickListener {
            val loginEmail = binding.loginEmailText.text.toString()
            val loginPassword = binding.loginPasswordText.text.toString()
            signIn(loginEmail, loginPassword)
        }
    }

    private fun signIn(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(requireContext(), "Please enter both email and password", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.login(email, password).observe(viewLifecycleOwner) { user ->
            if (user != null) {
                Toast.makeText(requireContext(), "Sign In Successful", Toast.LENGTH_SHORT).show()

                sharedPreferences = requireActivity().getSharedPreferences(SETTING_PREFRENCE, MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putString("loginEmail", email)
                    putString("loginPassword", password)
                    apply()
                }

                val intent = Intent(requireContext(), MainActivity::class.java)
                requireActivity().startActivity(intent)
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "Sign In Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
