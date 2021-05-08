package com.example.hmzcnbz.budgetapp.view.edit_profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hmzcnbz.budgetapp.R
import com.example.hmzcnbz.budgetapp.databinding.FragmentEditProfileBinding

class EditProfile : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var gender: String? = ""
    private var name: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        sharedPreferences = requireActivity().getSharedPreferences("firsttime", Context.MODE_PRIVATE)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)

        name = sharedPreferences.getString("name", "")
        binding.profileName.setText(name)

        gender = sharedPreferences.getString("gender", "")
        if (gender == "Bey") {
            binding.man.isChecked = true
        } else if (gender == "Hanım") {
            binding.woman.isChecked = true
        } else {
            binding.other.isChecked = true
        }

        binding.radioGroupGender.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.man -> {
                    gender = "Bey"
                }
                R.id.woman -> {
                    gender = "Hanım"
                }
                R.id.other -> {
                    gender = " "
                }
            }
        }

        binding.updateProfile.setOnClickListener {
            sharedPreferences.edit().putString("name", binding.profileName.text.toString()).apply()
            sharedPreferences.edit().putString("gender", gender).apply()
            val action = EditProfileDirections.actionEditProfileToMainFragment()
            findNavController().navigate(action)
            Toast.makeText(requireContext(), "Kaydedildi", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

}