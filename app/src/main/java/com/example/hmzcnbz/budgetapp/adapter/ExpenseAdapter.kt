package com.example.hmzcnbz.budgetapp.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hmzcnbz.budgetapp.R
import com.example.hmzcnbz.budgetapp.database.ExpenseEntity
import com.example.hmzcnbz.budgetapp.databinding.RowItemBinding
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList

class ExpenseAdapter(private val listener : OnItemClickListen,val context : Context ) : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    interface OnItemClickListen{
        fun onItemClick(position: Int)
    }

    private var expenseList = emptyList<ExpenseEntity>()
    private var rate = 1f
    private var currency = "TL"


    inner class ViewHolder(var viewview:RowItemBinding) : RecyclerView.ViewHolder(viewview.root),View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position!=RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item,parent,false)
        val view = DataBindingUtil.inflate<RowItemBinding>(LayoutInflater.from(parent.context),R.layout.row_item,parent,false)
        return ViewHolder(view)

    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.viewview.expense = expenseList[position]
        holder.viewview.prices = DecimalFormat("###,###").format( editPrice(expenseList[position].price,rate))+" "+currency
        when (expenseList[position].category) {
            "kira" -> {
                Glide.with(context).load(R.drawable.rent).into(holder.itemView.findViewById(R.id.image))
            }
            "fatura" -> {
                Glide.with(context).load(R.drawable.bill).into(holder.itemView.findViewById(R.id.image))
            }
            "eglence" -> {
                Glide.with(context).load(R.drawable.`fun`).into(holder.itemView.findViewById(R.id.image))
            }
            "diğer" -> {
                Glide.with(context).load(R.drawable.other).into(holder.itemView.findViewById(R.id.image))
            }
        }

    /*
        val currenItem = expenseList[position]
        holder.itemView.findViewById<TextView>(R.id.name).text=currenItem.description
        if(currency==0){
            holder.itemView.findViewById<TextView>(R.id.price).text = (currenItem.price*8).toString()
        }
        else if (currency==1){
            holder.itemView.findViewById<TextView>(R.id.price).text = (currenItem.price*4).toString()
        }

        when (currenItem.category) {
            "kira" -> {
            }
            "fatura" -> {
                Glide.with(context).load(R.drawable.bill).into(holder.itemView.findViewById(R.id.image))
            }
            "eglence" -> {
                Glide.with(context).load(R.drawable.`fun`).into(holder.itemView.findViewById(R.id.image))
            }
            "diğer" -> {
                Glide.with(context).load(R.drawable.other).into(holder.itemView.findViewById(R.id.image))
            }
        }
        println(currenItem.category)*/
    }


    override fun getItemCount(): Int {
        return expenseList.size
    }

    fun setData(expenses : List<ExpenseEntity>, rate: Float,curreny:Int){
        this.expenseList=expenses
        this.rate = rate
        when(curreny){
            0->{
                this.currency="TL"
            }
            1->{
                this.currency="$"
            }
            2->{
                this.currency="£"
            }
            3->{
                this.currency="€"
            }
        }


    }
        private fun editPrice( expenseList: Int, rate:Float) : Int {
        var expense = 0
        /*if (currency==0){
            expense=expenseList
        }
        else if (currency==1){
            expense=expenseList*2
        }
        else if (currency==2){
            expense=expenseList*5
        }
        else if (currency==3){
            expense=expenseList*10
        }*/
        expense = (expenseList*rate).toInt()

        return expense

    }


}