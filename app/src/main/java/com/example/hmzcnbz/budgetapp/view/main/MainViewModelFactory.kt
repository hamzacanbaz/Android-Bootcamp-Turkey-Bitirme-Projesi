package com.example.hmzcnbz.budgetapp.view.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hmzcnbz.budgetapp.database.CurrencyDao
import com.example.hmzcnbz.budgetapp.database.DatabaseDao

class MainViewModelFactory (
        private val dataSource: DatabaseDao,
        private val currencySource: CurrencyDao,
        private val application: Application
) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(dataSource,currencySource,application) as T
        }
        throw IllegalArgumentException("Unknown")
    }

}