package com.example.hmzcnbz.budgetapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DatabaseDao {

    @Insert
    fun insert(expense: ExpenseEntity)

    @Query("DELETE FROM expense_table WHERE uuid=:id")
    fun delete(id: Long)

    @Query("SELECT * FROM expense_table WHERE uuid=:id")
    fun getExpense(id: Long): LiveData<ExpenseEntity>

    @Query("SELECT * FROM expense_table")
    fun getAllExpenses(): LiveData<List<ExpenseEntity>>

    @Query("UPDATE expense_table set price=:price, category=:category, description=:description where uuid=:id")
    fun update(id: Long, category: String, price: Float, description: String)
}

@Dao
interface CurrencyDao {
    @Insert
    fun insert(currency: CurrencyEntity)

    @Query("DELETE FROM currency_table")
    fun deleteAll()

    @Query("SELECT * FROM currency_table")
    fun getAllCurrencies(): LiveData<List<CurrencyEntity>>


}

