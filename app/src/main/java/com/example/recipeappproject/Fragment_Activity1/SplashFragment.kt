package com.example.recipeappproject.Fragment_Activity1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.recipeappproject.R


class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_splash_, container, false)

        Handler(Looper.getMainLooper()).postDelayed({
          findNavController().navigate(R.id.action_splash_Fragment_to_sign_up_Fragment)
        },3000)
        return view
    }
}