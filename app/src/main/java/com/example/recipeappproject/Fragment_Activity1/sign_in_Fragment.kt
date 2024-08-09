package com.example.recipeappproject.Fragment_Activity1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.recipeappproject.databinding.FragmentSignInBinding
import com.example.recipeappproject.registerDatabaseHelper.DatabaseHelperRegister



class sign_in_Fragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var databaseHelper: DatabaseHelperRegister



        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Use the binding to inflate the layout
            binding = FragmentSignInBinding.inflate(inflater, container, false)
            databaseHelper = DatabaseHelperRegister(requireContext())


            Handler(Looper.getMainLooper()).postDelayed({
            }, 6000)

            databaseHelper = DatabaseHelperRegister(requireContext())

            binding.buttonLogIn.setOnClickListener {
                binding.loginEmailText.text?.clear()
                binding.loginPasswordText.text?.clear()
                val loginEmail = binding.loginEmailText.text.toString()
                val loginPassword = binding.loginPasswordText.text.toString()
                signInDatabase(loginEmail, loginPassword)
            }

            return binding.root
        }

    private  fun signInDatabase(email:String , password:String){

        val userExists=databaseHelper.readUser(email,password)

        if(userExists)
        {
            Toast.makeText(requireContext(),"SignIn Successful ",Toast.LENGTH_SHORT).show()
            // navigation to home fragment
        }
        else
        {
            Toast.makeText(requireContext(),"SignIn Failed ",Toast.LENGTH_SHORT).show()

        }

    }


}
