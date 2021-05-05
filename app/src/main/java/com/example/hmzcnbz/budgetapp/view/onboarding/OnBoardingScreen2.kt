package com.example.hmzcnbz.budgetapp.view.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hmzcnbz.budgetapp.view.onboarding.OnBoardingScreen2Directions
import com.example.hmzcnbz.budgetapp.R


class OnBoardingScreen2 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_on_boarding_screen2, container, false)

        var button = view.findViewById<View>(R.id.onboarding_button2)
        button.setOnClickListener {
            val action = OnBoardingScreen2Directions.actionOnBoardingScreen2ToOnBoardingScreen3()
            findNavController().navigate(action)
        }


        return view
    }

}