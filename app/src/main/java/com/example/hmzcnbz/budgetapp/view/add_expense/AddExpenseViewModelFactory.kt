package com.example.hmzcnbz.budgetapp.view.add_expense

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hmzcnbz.budgetapp.database.CurrencyDao
import com.example.hmzcnbz.budgetapp.database.DatabaseDao
import com.example.hmzcnbz.budgetapp.view.detail_expense.ExpenseDetailViewModel
import com.example.hmzcnbz.budgetapp.view.main.MainViewModel


class AddExpenseViewModelFactory(
        private val dataSource: DatabaseDao,
        private val currencySource: CurrencyDao,
        private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExpenseViewModel::class.java)) {
            return AddExpenseViewModel(dataSource, currencySource, application) as T
        } else if (modelClass.isAssignableFrom(ExpenseDetailViewModel::class.java)) {
            return ExpenseDetailViewModel(dataSource, currencySource, application) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dataSource, currencySource, application) as T
        }
        throw IllegalArgumentException("Unknown")
    }

}