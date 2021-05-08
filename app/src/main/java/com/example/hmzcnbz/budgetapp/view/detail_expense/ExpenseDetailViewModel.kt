package com.example.hmzcnbz.budgetapp.view.detail_expense

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.hmzcnbz.budgetapp.database.CurrencyDao
import com.example.hmzcnbz.budgetapp.database.DatabaseDao

class ExpenseDetailViewModel(private val dataSource: DatabaseDao, currencySource: CurrencyDao, application: Application) : ViewModel() {

    fun deleteExpense(id:Long){
        dataSource.delete(id)
    }

}