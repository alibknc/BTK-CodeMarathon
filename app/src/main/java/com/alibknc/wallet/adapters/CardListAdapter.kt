package com.alibknc.wallet.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alibknc.wallet.R
import com.alibknc.wallet.models.Card
import android.widget.LinearLayout
import com.alibknc.wallet.views.DetailsActivity

class CardListAdapter(private var list: List<Card>): RecyclerView.Adapter<CardListAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var cardNumberText: TextView = itemView.findViewById(R.id.i_cardNumberText)
        var cardDateText: TextView = itemView.findViewById(R.id.i_cardDateText)
        var cvvText: TextView = itemView.findViewById(R.id.i_cvvText)
        var idText: TextView = itemView.findViewById(R.id.i_idText)
        var layout: LinearLayout = itemView.findViewById(R.id.cardItemLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CardListAdapter.ViewHolder, position: Int) {
        holder.cardNumberText.text = list[position].cardNumber
        holder.cardDateText.text = list[position].cardExpire
        holder.cvvText.text = list[position].cvv
        holder.idText.text = list[position].id
        holder.layout.setOnClickListener {
            val context = holder.itemView.context as Activity
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("id",holder.idText.text)
            context.startActivityForResult(intent, 3)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}