package com.example.recipeappproject.Fragment_Activity1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.recipeappproject.MainActivity
import com.example.recipeappproject.databinding.FragmentSignInBinding
import com.example.recipeappproject.registerDatabaseHelper.DatabaseHelperRegister

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var databaseHelper: DatabaseHelperRegister

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Use the binding to inflate the layout
            binding = FragmentSignInBinding.inflate(inflater, container, false)
            databaseHelper = DatabaseHelperRegister(requireContext())

            binding.buttonLogIn.setOnClickListener {
                val loginEmail = binding.loginEmailText.text.toString()
                val loginPassword = binding.loginPasswordText.text.toString()
                signInDatabase(loginEmail, loginPassword)
            }
            return binding.root
        }

    private  fun signInDatabase(email:String , password:String){
        val userExists=databaseHelper.readUser(email,password)
        if(userExists){
            Toast.makeText(requireContext(),"SignIn Successful ",Toast.LENGTH_SHORT).show()
            // navigation to home fragment
            val intent = Intent(requireContext(), MainActivity::class.java)
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }
        else {
            Toast.makeText(requireContext(),"SignIn Failed ",Toast.LENGTH_SHORT).show()
        }
    }
}
