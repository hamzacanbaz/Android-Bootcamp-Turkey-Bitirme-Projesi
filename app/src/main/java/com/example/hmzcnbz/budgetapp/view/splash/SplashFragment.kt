package com.example.hmzcnbz.budgetapp.view.splash

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hmzcnbz.budgetapp.R
import com.example.hmzcnbz.budgetapp.view.splash.SplashFragmentDirections

class SplashFragment : Fragment() {
   lateinit var sharedPreferences :SharedPreferences
    var firstTime : Boolean = false
    var handler : Handler = Handler()

    val runnable = Runnable {
        firstTime = sharedPreferences.getBoolean("first",false)
        if(!firstTime){
            val action = SplashFragmentDirections.actionSplashFragmentToOnBoardingScreen1()
            findNavController().navigate(action)
        }
        else{
            val action = SplashFragmentDirections.actionSplashFragmentToMainFragment()
            findNavController().navigate(action)

        }
        println("delayed")
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        sharedPreferences =  requireActivity().getSharedPreferences("firsttime",
            android.content.Context.MODE_PRIVATE)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }


    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable,4000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences.edit().putBoolean("first",true).apply()
        sharedPreferences.edit().putString("name","").apply()
        sharedPreferences.edit().putString("gender","").apply()
        sharedPreferences.edit().putInt("total",0).apply()
    }


}