package com.example.recipeappproject.ui.Fragment_Activity1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.example.recipeappproject.MainActivity
import com.example.recipeappproject.databinding.FragmentSignInBinding
import com.example.recipeappproject.registerDatabaseHelper.DatabaseHelperRegister
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SignInFragment : Fragment() {
    val SETTING_PREFRENCE = "com.example.sharedstorageapplication"
    lateinit var editor : SharedPreferences.Editor
    lateinit var sharedPreferences: SharedPreferences
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


            sharedPreferences = requireActivity().getSharedPreferences(SETTING_PREFRENCE,
                MODE_PRIVATE
            )
            with(sharedPreferences.edit()) {
                putString("loginEmail", email )
                putString("loginPassword", password )
                apply()

            }

            requireActivity().startActivity(intent)
            requireActivity().finish()
        }
        else {
            Toast.makeText(requireContext(),"SignIn Failed ",Toast.LENGTH_SHORT).show()
        }
    }
}
