package com.example.hmzcnbz.budgetapp.view.detail_expense

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.hmzcnbz.budgetapp.R
import com.example.hmzcnbz.budgetapp.database.ExpenseDatabase
import com.example.hmzcnbz.budgetapp.databinding.FragmentExpenseDetailBinding
import com.example.hmzcnbz.budgetapp.view.add_expense.AddExpenseViewModel
import com.example.hmzcnbz.budgetapp.view.add_expense.AddExpenseViewModelFactory


class ExpenseDetail : Fragment() {
    val argum : ExpenseDetailArgs by navArgs()
    lateinit var binding : FragmentExpenseDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_expense_detail,container,false)
        val application = requireNotNull(this.activity).application
        val dataSource = ExpenseDatabase.getInstance(application).databaseDao
        val currencySource = ExpenseDatabase.getInstance(application).currencyDao
        val viewmodelFactory = AddExpenseViewModelFactory(dataSource,currencySource,application)


        var viewmodel = ViewModelProvider(this,viewmodelFactory).get(ExpenseDetailViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel=viewmodel
        binding.expenseDetailDescription.text = argum.description
        binding.expenseDetailPrice.text = argum.price.toString()
        when(argum.category){
            "eğlence"->{
                binding.expenseDetailImage.setImageResource(R.drawable.`fun`)
            }
            "fatura"->{
                binding.expenseDetailImage.setImageResource(R.drawable.bill)
            }
            "kira"->{
                binding.expenseDetailImage.setImageResource(R.drawable.rent)
            }
            "diğer"->{
                binding.expenseDetailImage.setImageResource(R.drawable.other)
            }
        }


        return binding.root
    }

}