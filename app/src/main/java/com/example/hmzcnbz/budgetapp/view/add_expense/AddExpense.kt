package com.example.hmzcnbz.budgetapp.view.add_expense

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hmzcnbz.budgetapp.R
import com.example.hmzcnbz.budgetapp.database.ExpenseDatabase
import com.example.hmzcnbz.budgetapp.databinding.FragmentAddExpenseBinding
import java.lang.Exception


class AddExpense : Fragment() {
    val argum: AddExpenseArgs by navArgs()
    lateinit var binding: FragmentAddExpenseBinding
    var expense_type: String = ""
    private var currency_usd = 1f
    private var currency_try = 1f
    private var currency_gbp = 1f
    private var currency_euro = 1f
    private var new_price = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_expense, container, false)
        if (argum.addOrUpdate == 1L) {
            binding.addExpenseButton.text = "Guncelle"
            binding.toolbar.title="Harcama Güncelle"
            binding.addExpenseDescription.setText(argum.description)
            binding.addExpensePrice.setText(argum.price.toString())
            println("category:${argum.category}")
            when (argum.category) {
                "kira" -> {
                    binding.rent.isChecked = true
                }
                "fatura" -> {
                    binding.bill.isChecked = true
                }
                "eglence" -> {
                    binding.funn.isChecked = true
                }
                "diğer" -> {
                    binding.other.isChecked = true
                }

            }
        }
        val application = requireNotNull(this.activity).application
        val dataSource = ExpenseDatabase.getInstance(application).databaseDao
        val currencySource = ExpenseDatabase.getInstance(application).currencyDao
        val viewmodelFactory = AddExpenseViewModelFactory(dataSource, currencySource, application)


        var viewmodel = ViewModelProvider(this, viewmodelFactory).get(AddExpenseViewModel::class.java)
        binding.lifecycleOwner = this
        binding.addExpenseViewModel = viewmodel

        viewmodel.readAllCurrencyData.observe(viewLifecycleOwner, Observer {
            currency_usd = it[0].price //1,2
            currency_try = it[2].price //9,9
            currency_gbp = it[1].price //0,86
        })

        binding.radioGroup1.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rent -> {
                    expense_type = "kira"
                }
                R.id.bill -> {
                    expense_type = "fatura"
                }
                R.id.other -> {
                    expense_type = "diğer"
                }
                R.id.funn -> {
                    expense_type = "eglence"
                }
            }
        }
        binding.radioGroup2.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.tl -> {
                    try {
                        new_price = ((binding.addExpensePrice.text.toString()).toFloat() / currency_try)
                        println("newprice=$new_price")
                    } catch (e: Exception) {
                        println(e)
                    }
                }
                R.id.dolar -> {
                    try {
//                        println("new price"+ )
                        new_price = ((binding.addExpensePrice.text.toString()).toFloat() / currency_usd)
                        println("newprice=$new_price")
                    } catch (e: Exception) {
                        println(e)
                    }

                }
                R.id.euro -> {
                    try {
                        new_price = ((binding.addExpensePrice.text.toString()).toFloat() / currency_euro)
                        println("newprice=$new_price")
                    } catch (e: Exception) {
                        println(e)
                    }
                }
                R.id.sterlin -> {
                    try {
                        new_price = ((binding.addExpensePrice.text.toString()).toFloat() / currency_gbp)
                        println("newprice=$new_price")
                    } catch (e: Exception) {
                        println(e)
                    }
                }
            }
        }

        binding.addExpenseButton.setOnClickListener {
            if (expense_type == "") {
                expense_type = "diğer"
            }
            if (new_price == 0f) {
                new_price = ((binding.addExpensePrice.text.toString()).toFloat() / currency_euro)

            }
            if (argum.addOrUpdate == 1L) {
                viewmodel.updateData(binding.addExpenseDescription.text.toString(), expense_type, new_price, argum.uuid)
            } else {
                println("new price2$new_price")
                viewmodel.insertData(binding.addExpenseDescription.text.toString(), expense_type, new_price)
            }
            val action = AddExpenseDirections.actionAddExpenseToMainFragment()
            findNavController().navigate(action)

        }
        viewmodel.readAllData.observe(viewLifecycleOwner, Observer {
            println("size=" + it.size)
        })

        return binding.root
    }


}