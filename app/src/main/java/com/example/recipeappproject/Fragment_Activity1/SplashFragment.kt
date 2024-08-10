package com.example.recipeappproject.Fragment_Activity1

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.recipeappproject.MainActivity
import com.example.recipeappproject.R


class SplashFragment : Fragment() {
    val SETTING_PREFRENCE = "com.example.sharedstorageapplication"
    lateinit var editor : SharedPreferences.Editor
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_splash_, container, false)

        Handler(Looper.getMainLooper()).postDelayed({
            sharedPreferences = requireActivity().getSharedPreferences(SETTING_PREFRENCE,MODE_PRIVATE)
            val Value1 = sharedPreferences.getString("loginEmail", null)
            val Value2 = sharedPreferences.getString("loginPassword", null)

            if(Value1!=null&&Value2!=null){
                val intent = Intent(requireContext(), MainActivity::class.java)
                requireActivity().startActivity(intent)
                requireActivity().finish()
            }else{
                findNavController().navigate(R.id.action_splash_Fragment_to_sign_up_Fragment)
            }

        },3000)
        return view
    }
}