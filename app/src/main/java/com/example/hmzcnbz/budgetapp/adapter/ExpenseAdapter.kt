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

class ExpenseAdapter(private val listener: OnItemClickListen, val context: Context) : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    interface OnItemClickListen {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }

    private var expenseList = emptyList<ExpenseEntity>()
    private var rate = 1f
    private var currency = "TL"


    inner class ViewHolder(var viewview: RowItemBinding) : RecyclerView.ViewHolder(viewview.root), View.OnClickListener, View.OnLongClickListener {

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemLongClick(position)
            }
            return true
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item,parent,false)
        val view = DataBindingUtil.inflate<RowItemBinding>(LayoutInflater.from(parent.context), R.layout.row_item, parent, false)
        return ViewHolder(view)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.viewview.expense = expenseList[position]
        holder.viewview.prices = DecimalFormat("###,###").format(editPrice(expenseList[position].price, rate)) + " " + currency
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


    }


    override fun getItemCount(): Int {
        return expenseList.size
    }

    fun setData(expenses: List<ExpenseEntity>, rate: Float, curreny: Int) {
        this.expenseList = expenses
        this.rate = rate
        when (curreny) {
            0 -> {
                this.currency = "TL"
            }
            1 -> {
                this.currency = "$"
            }
            2 -> {
                this.currency = "£"
            }
            3 -> {
                this.currency = "€"
            }
        }


    }

    private fun editPrice(expenseList: Float, rate: Float): Float {
        var expense = 0f
        expense = (expenseList * rate)

        return expense

    }


}