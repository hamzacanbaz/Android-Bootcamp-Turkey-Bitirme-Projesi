package com.example.hmzcnbz.budgetapp.view.onboarding

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hmzcnbz.budgetapp.view.onboarding.OnBoardingScreen1Directions
import com.example.hmzcnbz.budgetapp.R

class OnBoardingScreen1 : Fragment() {
    lateinit var sharedPreferences : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences =  requireActivity().getSharedPreferences("firsttime",
            android.content.Context.MODE_PRIVATE)

        sharedPreferences.edit().putBoolean("first",true).apply()


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_on_boarding_screen1, container, false)

        var button = view.findViewById<View>(R.id.onboarding_button1)
        button.setOnClickListener {
            val action = OnBoardingScreen1Directions.actionOnBoardingScreen1ToOnBoardingScreen2()
            findNavController().navigate(action)

        }

        return view;
    }


}