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
import java.text.DecimalFormat
import java.util.*


class MainFragment : Fragment(),ExpenseAdapter.OnItemClickListen {

    private lateinit var binding : FragmentMainBinding
    private lateinit var adapter : ExpenseAdapter
    var expenseList = emptyList<ExpenseEntity>()
    lateinit var viewmodel : MainViewModel
    private lateinit var sharedPreferences : SharedPreferences


    private var currency_usd = 1f
    private var currency_try = 1f
    private var currency_gbp = 1f
    private var currency_euro= 1f

    var total_price: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("firsttime", Context.MODE_PRIVATE)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_main,container,false)
        // Inflate the layout for this fragment
        val application = requireNotNull(this.activity).application
        val dataSource = ExpenseDatabase.getInstance(application).databaseDao
        val currencySource = ExpenseDatabase.getInstance(application).currencyDao

        val viewModelFactory =MainViewModelFactory(dataSource,currencySource,application)
         viewmodel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)
        binding.lifecycleOwner=this
        binding.mainViewModel=viewmodel

        val name =sharedPreferences.getString("name","")
        val gender =sharedPreferences.getString("gender","")
            binding.cardviewName.setText(name?.capitalize(Locale.ROOT)+" "+gender)



//        viewmodel.loadData()
        viewmodel.refreshData()
        //dataSource.delete(1)



        viewmodel.readAllCurrencyData.observe(viewLifecycleOwner, Observer {
            currency_usd = it[0].price
            currency_try = it[2].price
            currency_gbp = it[1].price

        })

        viewmodel.getAllExpenses.observe(viewLifecycleOwner, Observer {
            total_price=0
            for (i in 0..it.size-1) {
                total_price += it[i].price
            }
            val totalPriceStr = DecimalFormat("###,###").format(total_price)
            binding.cardviewTotal.text = totalPriceStr+" €"
        })

        viewmodel.readData.observe(viewLifecycleOwner, Observer {
            adapter = ExpenseAdapter(listener = this,requireContext())
            expenseList = it
            adapter.setData(expenseList,currency_euro,3)
            adapter.notifyDataSetChanged()

            binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerview.adapter = adapter
        })






        var selectedButton = binding.euro
        binding.tl.setOnClickListener {
            adapter.setData(expenses = expenseList,currency_try,0)
            adapter.notifyDataSetChanged()

            selectedButton.setTextColor(resources.getColor(R.color.black))
            binding.tl.setTextColor(resources.getColor(R.color.splash_orange))
            selectedButton=binding.tl
            var id = viewmodel.getCurrency(0)
//            viewmodel.insertCurrency(id=id.toLong(),price = 100.toFloat())

            Toast.makeText(requireContext(),id.toString(),Toast.LENGTH_SHORT).show()
            total_price=(total_price*currency_try).toInt()
            val totalPriceStr = DecimalFormat("###,###").format(total_price)

            binding.cardviewTotal.setText(totalPriceStr+ " $")

        }
        binding.dolar.setOnClickListener {
            adapter.setData(expenses = expenseList,currency_usd,1)
            adapter.notifyDataSetChanged()
            selectedButton.setTextColor(resources.getColor(R.color.black))
            binding.dolar.setTextColor(resources.getColor(R.color.splash_orange))
            selectedButton=binding.dolar
            var id = viewmodel.getCurrency(3)

            Toast.makeText(requireContext(),id.toString(),Toast.LENGTH_SHORT).show()
            total_price=(total_price*currency_usd).toInt()
            val totalPriceStr = DecimalFormat("###,###").format(total_price)

            binding.cardviewTotal.setText(totalPriceStr+ " $")


        }
        binding.euro.setOnClickListener {
            adapter.setData(expenses = expenseList,currency_euro,3)
            adapter.notifyDataSetChanged()

            selectedButton.setTextColor(resources.getColor(R.color.black))
            binding.euro.setTextColor(resources.getColor(R.color.splash_orange))

            selectedButton=binding.euro
            var id = viewmodel.getCurrency(2)

            Toast.makeText(requireContext(),id.toString(),Toast.LENGTH_SHORT).show()
            total_price=(total_price*currency_euro).toInt()
            val totalPriceStr = DecimalFormat("###,###").format(total_price)

            binding.cardviewTotal.setText(totalPriceStr+ " $")

        }
        binding.sterlin.setOnClickListener {
            adapter.setData(expenses = expenseList,currency_gbp,2)
            adapter.notifyDataSetChanged()
            selectedButton.setTextColor(resources.getColor(R.color.black))
            binding.sterlin.setTextColor(resources.getColor(R.color.splash_orange))
            selectedButton=binding.sterlin
            var id = viewmodel.getCurrency(1)

            Toast.makeText(requireContext(),id.toString(),Toast.LENGTH_SHORT).show()

            total_price=(total_price*currency_gbp).toInt()
            val totalPriceStr = DecimalFormat("###,###").format(total_price)

            binding.cardviewTotal.setText(totalPriceStr+ " $")

        }



        binding.addExpense.setOnClickListener {
            Toast.makeText(requireContext(),"Go",Toast.LENGTH_SHORT).show()
            val action = MainFragmentDirections.actionMainFragmentToAddExpense(0,"",0,"",0)
            findNavController().navigate(action)
        }
        binding.cardview.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToEditProfile()
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(context,position.toString(), Toast.LENGTH_LONG).show()
        viewmodel.dataSource.getAllExpenses().observe(viewLifecycleOwner, Observer {
            println(it[position])   //it[position].uuid gönderdik gidilen yerde getexpense(id) ile çağır
//            val action = MainFragmentDirections.actionMainFragmentToAddExpense(1.toLong(),it[position].description,it[position].price.toLong(),it[position].category,it[position].uuid)
            val action = MainFragmentDirections.actionMainFragmentToExpenseDetail(it[position].description,it[position].price.toLong(),it[position].category,it[position].uuid)
            findNavController().navigate(action)
        })


    }

}