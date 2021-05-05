package com.example.hmzcnbz.budgetapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ExpenseEntity::class,CurrencyEntity::class],version =4,exportSchema = false)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract val databaseDao : DatabaseDao
    abstract val currencyDao : CurrencyDao

    companion object{
        @Volatile
        private var INSTANCE : ExpenseDatabase?=null

        fun getInstance(context: Context) : ExpenseDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ExpenseDatabase::class.java,
                        "expense_database"
                    ).allowMainThreadQueries()
                        //allow main thread sil ve başka yerde hallet
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}