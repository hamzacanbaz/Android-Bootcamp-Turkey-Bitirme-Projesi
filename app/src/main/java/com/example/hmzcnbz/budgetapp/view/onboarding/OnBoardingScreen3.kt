package com.example.hmzcnbz.budgetapp.view.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.hmzcnbz.budgetapp.R
import com.example.hmzcnbz.budgetapp.view.onboarding.OnBoardingScreen3Directions


class OnBoardingScreen3 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        var view= inflater.inflate(R.layout.fragment_on_boarding_screen3, container, false)

        val button = view.findViewById<Button>(R.id.onboarding_button3)

        button.setOnClickListener {
            val action = OnBoardingScreen3Directions.actionOnBoardingScreen3ToMainFragment()
            findNavController().navigate(action)
        }
        return view
    }

}