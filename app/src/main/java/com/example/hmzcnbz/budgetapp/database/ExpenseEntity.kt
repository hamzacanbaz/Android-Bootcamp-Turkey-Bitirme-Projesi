package com.example.hmzcnbz.budgetapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_table")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    var uuid:Long = 0L,

    @ColumnInfo(name = "category")
    var category:String = "",

    @ColumnInfo(name = "description")
    var description : String = "",

    @ColumnInfo(name = "price")
    var price : Int = 0
)

@Entity(tableName = "currency_table")
data class CurrencyEntity(
        @PrimaryKey(autoGenerate = true)
        var uuid:Long = 0L,
        @ColumnInfo(name = "id")
        var id : Long = 0L,
        @ColumnInfo(name = "euro_price")
        var price : Float = 0F
)

