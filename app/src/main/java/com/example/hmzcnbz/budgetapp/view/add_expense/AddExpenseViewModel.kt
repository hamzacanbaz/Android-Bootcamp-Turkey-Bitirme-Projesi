package com.example.hmzcnbz.budgetapp.view.add_expense

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hmzcnbz.budgetapp.database.CurrencyDao
import com.example.hmzcnbz.budgetapp.database.CurrencyEntity
import com.example.hmzcnbz.budgetapp.database.DatabaseDao
import com.example.hmzcnbz.budgetapp.database.ExpenseEntity

class AddExpenseViewModel( val dataSource: DatabaseDao,val currencySource:CurrencyDao, application: Application) : AndroidViewModel(application) {

    var readAllData: LiveData<List<ExpenseEntity>> = dataSource.getAllExpenses()
    var readAllCurrencyData : LiveData<List<CurrencyEntity>> = currencySource.getAllCurrencies()

    fun insertData(description:String,category:String,price:String){
        println("insert fun invoked")
        dataSource.insert(ExpenseEntity(category = category,description = description,price = price.toInt()))
    }

    fun updateData(description:String,category:String,price:String,id:Long){
        dataSource.update(category = category,price = price.toInt(),description = description,id = id )
    }

}