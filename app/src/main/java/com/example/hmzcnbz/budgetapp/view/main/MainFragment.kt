package com.example.hmzcnbz.budgetapp.view.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hmzcnbz.budgetapp.R
import com.example.hmzcnbz.budgetapp.adapter.ExpenseAdapter
import com.example.hmzcnbz.budgetapp.database.ExpenseDatabase
import com.example.hmzcnbz.budgetapp.database.ExpenseEntity
import com.example.hmzcnbz.budgetapp.databinding.FragmentMainBinding
import com.example.hmzcnbz.budgetapp.view.add_expense.AddExpenseViewModelFactory
import java.text.DecimalFormat
import java.util.*


class MainFragment : Fragment(), ExpenseAdapter.OnItemClickListen {

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: ExpenseAdapter
    private var expenseList = emptyList<ExpenseEntity>()
    lateinit var viewmodel: MainViewModel
    private lateinit var sharedPreferences: SharedPreferences
    var currencyId = 2


    private var currencyUsd = 1f
    private var currencyTry = 1f
    private var currencyGbp = 1f
    private var currencyEuro = 1f

    var total_price = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("firsttime", Context.MODE_PRIVATE)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_main, container, false)
        // Inflate the layout for this fragment
        val application = requireNotNull(this.activity).application
        val dataSource = ExpenseDatabase.getInstance(application).databaseDao
        val currencySource = ExpenseDatabase.getInstance(application).currencyDao

        val viewModelFactory = AddExpenseViewModelFactory(dataSource, currencySource, application)
        viewmodel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.mainViewModel = viewmodel

        val name = sharedPreferences.getString("name", "")
        val gender = sharedPreferences.getString("gender", "")
        binding.cardviewName.setText(name?.capitalize(Locale.ROOT) + " " + gender)


        if (name==""){
            viewmodel.loadData()
        }
        //viewmodel.loadData()

//        viewmodel.loadData()
        viewmodel.refreshData()
        //dataSource.delete(1)

        viewmodel.readAllCurrencyData.observe(viewLifecycleOwner, {
                if(it.size>0){
                    currencyUsd = it[0].price
                    currencyTry = it[2].price
                    currencyGbp = it[1].price
                }


        })

        viewmodel.getAllExpenses.observe(viewLifecycleOwner, {
            total_price = 0f
            for (element in it) {
                total_price += element.price
            }
            val totalPriceStr = DecimalFormat("###,###").format(total_price)
            binding.cardviewTotal.text = totalPriceStr + " €"
        })

        viewmodel.readData.observe(viewLifecycleOwner, {
            adapter = ExpenseAdapter(listener = this, requireContext())
            expenseList = it
            adapter.setData(expenseList, currencyEuro, 3)
            adapter.notifyDataSetChanged()

            binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerview.adapter = adapter
        })


        var selectedButton = binding.euro
        binding.tl.setOnClickListener {
            adapter.setData(expenses = expenseList, currencyTry, 0)
            adapter.notifyDataSetChanged()

            selectedButton.setTextColor(resources.getColor(R.color.black))
            binding.tl.setTextColor(resources.getColor(R.color.splash_orange))
            selectedButton = binding.tl
            currencyId = 0

            total_price = (total_price * currencyTry)
            val totalPriceStr = DecimalFormat("###,###").format(total_price)

            binding.cardviewTotal.setText(totalPriceStr + " TL")
            total_price = (total_price / currencyTry)

        }
        binding.dolar.setOnClickListener {
            adapter.setData(expenses = expenseList, currencyUsd, 1)
            adapter.notifyDataSetChanged()
            selectedButton.setTextColor(resources.getColor(R.color.black))
            binding.dolar.setTextColor(resources.getColor(R.color.splash_orange))
            selectedButton = binding.dolar
            currencyId = 3

            total_price = (total_price * currencyUsd)
            val totalPriceStr = DecimalFormat("###,###").format(total_price)

            binding.cardviewTotal.setText(totalPriceStr + " $")
            total_price = (total_price / currencyUsd)


        }
        binding.euro.setOnClickListener {
            adapter.setData(expenses = expenseList, currencyEuro, 3)
            adapter.notifyDataSetChanged()

            selectedButton.setTextColor(resources.getColor(R.color.black))
            binding.euro.setTextColor(resources.getColor(R.color.splash_orange))

            selectedButton = binding.euro
            currencyId = 2

            total_price = (total_price * currencyEuro)
            val totalPriceStr = DecimalFormat("###,###").format(total_price)

            binding.cardviewTotal.setText(totalPriceStr + " €")
            total_price = (total_price / currencyEuro)


        }
        binding.sterlin.setOnClickListener {
            adapter.setData(expenses = expenseList, currencyGbp, 2)
            adapter.notifyDataSetChanged()
            selectedButton.setTextColor(resources.getColor(R.color.black))
            binding.sterlin.setTextColor(resources.getColor(R.color.splash_orange))
            selectedButton = binding.sterlin
            currencyId = 1

            total_price = (total_price * currencyGbp)
            val totalPriceStr = DecimalFormat("###,###").format(total_price)

            binding.cardviewTotal.setText(totalPriceStr + " £")
            total_price = (total_price / currencyGbp)


        }



        binding.addExpense.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToAddExpense(0, "", 0, "", 0)
            findNavController().navigate(action)
        }
        binding.cardview.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToEditProfile()
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onItemClick(position: Int) {
        viewmodel.dataSource.getAllExpenses().observe(viewLifecycleOwner, {

            if (currencyId == 0) {
                val action = MainFragmentDirections.actionMainFragmentToExpenseDetail(it[position].description, (it[position].price * currencyTry), it[position].category, it[position].uuid, currencyId.toLong())
                findNavController().navigate(action)
            } else if (currencyId == 1) {
                val action = MainFragmentDirections.actionMainFragmentToExpenseDetail(it[position].description, (it[position].price * currencyGbp), it[position].category, it[position].uuid, currencyId.toLong())
                findNavController().navigate(action)
            } else if (currencyId == 2) {
                val action = MainFragmentDirections.actionMainFragmentToExpenseDetail(it[position].description, it[position].price, it[position].category, it[position].uuid, currencyId.toLong())
                findNavController().navigate(action)
            } else {
                val action = MainFragmentDirections.actionMainFragmentToExpenseDetail(it[position].description, (it[position].price * currencyUsd), it[position].category, it[position].uuid, currencyId.toLong())
                findNavController().navigate(action)
            }
            currencyId = 2
        })


    }

    override fun onItemLongClick(position: Int) {
        viewmodel.dataSource.getAllExpenses().observe(viewLifecycleOwner, {
            //it[position].uuid gönderdik gidilen yerde getexpense(id) ile çağır
            val action = MainFragmentDirections.actionMainFragmentToAddExpense(1.toLong(), it[position].description, it[position].price.toLong(), it[position].category, it[position].uuid)
//            val action = MainFragmentDirections.actionMainFragmentToExpenseDetail(it[position].description,it[position].price.toLong(),it[position].category,it[position].uuid)
            findNavController().navigate(action)
        })
    }

}