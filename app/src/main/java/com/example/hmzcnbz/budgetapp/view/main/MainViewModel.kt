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

    val readData: LiveData<List<ExpenseEntity>> = dataSource.getAllExpenses()
    val BASE_URL = "http://data.fixer.io/api/"
    private lateinit var sharedPreferences: SharedPreferences

    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    private fun editrefreshtime() {
        sharedPreferences.edit().putLong("refreshTime", System.nanoTime()).apply()
    }

    var getAllExpenses: LiveData<List<ExpenseEntity>> = dataSource.getAllExpenses()


    var readAllCurrencyData: LiveData<List<CurrencyEntity>> = currencySource.getAllCurrencies()


    fun refreshData() {
        sharedPreferences = application.getSharedPreferences("firsttime", Context.MODE_PRIVATE)

        val updateTime = sharedPreferences.getLong("refreshTime", 0)
        if (updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSql()
        } else {
            loadData()
            editrefreshtime()
        }
    }


    fun  loadData() {
        Toast.makeText(application, "From api", Toast.LENGTH_LONG).show()
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CurrencyApi::class.java)

        viewModelScope.launch {
            val response = retrofit.getCurrency()
            if (response.isSuccessful) {

                currencySource.deleteAll()

                response.body()?.get("rates")?.asJsonObject?.get("USD")?.let { CurrencyEntity(id = 0, price = it.asFloat) }?.let { currencySource.insert(it) }
                response.body()?.get("rates")?.asJsonObject?.get("GBP")?.let { CurrencyEntity(id = 1, price = it.asFloat) }?.let { currencySource.insert(it) }
                response.body()?.get("rates")?.asJsonObject?.get("TRY")?.let { CurrencyEntity(id = 2, price = it.asFloat) }?.let { currencySource.insert(it) }
                currencySource.insert(CurrencyEntity(id = 3, price = 1F))


            }
        }
    }

    private fun getDataFromSql() {
        Toast.makeText(application, "From SQLite", Toast.LENGTH_LONG).show()
        readAllCurrencyData = currencySource.getAllCurrencies()
    }


}