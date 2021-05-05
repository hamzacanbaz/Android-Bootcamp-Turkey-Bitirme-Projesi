package com.example.hmzcnbz.budgetapp.view.main

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hmzcnbz.budgetapp.database.CurrencyDao
import com.example.hmzcnbz.budgetapp.database.CurrencyEntity
import com.example.hmzcnbz.budgetapp.database.DatabaseDao
import com.example.hmzcnbz.budgetapp.database.ExpenseEntity
import com.example.hmzcnbz.budgetapp.service.CurrencyApi
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel(val dataSource: DatabaseDao,
                    val currencySource: CurrencyDao,
                    val application: Application)
    : ViewModel() {

    val readData : LiveData<List<ExpenseEntity>> = dataSource.getAllExpenses()
    val BASE_URL = "http://data.fixer.io/api/"
    private lateinit var sharedPreferences : SharedPreferences

    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    private fun editrefreshtime(){
        sharedPreferences.edit().putLong("refreshTime",System.nanoTime()).apply()
    }

    var getAllExpenses : LiveData<List<ExpenseEntity>> = dataSource.getAllExpenses()



    var readAllCurrencyData : LiveData<List<CurrencyEntity>> = currencySource.getAllCurrencies()

    fun getCurrency(id:Int) : Int{

        when (id) {
            0 -> {
                println("tl")
                var id =0
            }
            1 -> {
                println("sterlin")
                var id =1
            }
            2 -> {
                println("euro")
                var id =2
            }
            3 -> {
                println("dolar")
                var id =3
            }
        }
        return id
    }

    fun refreshData(){
        sharedPreferences = application.getSharedPreferences("firsttime", Context.MODE_PRIVATE)

        val updateTime = sharedPreferences.getLong("refreshTime",0)
        if(updateTime!=0L && System.nanoTime()-updateTime<refreshTime){
            getDataFromSql()
        }
        else{
            loadData()
            editrefreshtime()
        }
    }


    fun insertCurrency(id:Long,price:Float){
        currencySource.insert(CurrencyEntity(id=id,price=price))
    }

    fun getExpense(id:Long): LiveData<ExpenseEntity>{
        return dataSource.getExpense(id)
    }




    fun loadData(){
        Toast.makeText(application,"From api", Toast.LENGTH_LONG).show()
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CurrencyApi::class.java)

        viewModelScope.launch {
            val response = retrofit.getCurrency()
            if(response.isSuccessful){

                currencySource.deleteAll()

                println(response.body()?.get("rates")?.asJsonObject?.get("USD"))
                println(response.body()?.get("rates")?.asJsonObject?.get("GBP"))
                println(response.body()?.get("rates")?.asJsonObject?.get("TRY"))

                response.body()?.get("rates")?.asJsonObject?.get("USD")?.let { CurrencyEntity(id = 0,price = it.asFloat ) }?.let { currencySource.insert(it) }
                response.body()?.get("rates")?.asJsonObject?.get("GBP")?.let { CurrencyEntity(id = 1,price = it.asFloat ) }?.let { currencySource.insert(it) }
                response.body()?.get("rates")?.asJsonObject?.get("TRY")?.let { CurrencyEntity(id = 2,price = it.asFloat ) }?.let { currencySource.insert(it) }
                currencySource.insert(CurrencyEntity(id = 3,price = 1F))

                //response.body()?.get("rates")?.asJsonObject?.get("USD")?.let { CurrencyEntity(id = 0,price = it.asFloat ) }?.let { currencySource.insert(it) }
                //insert to sql
            }
        }
    }

    private fun getDataFromSql(){
        Toast.makeText(application,"From SQLite", Toast.LENGTH_LONG).show()
        readAllCurrencyData = currencySource.getAllCurrencies()
    }



}