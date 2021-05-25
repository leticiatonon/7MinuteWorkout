package com.tonon.a7minuteworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tonon.a7minuteworkout.R
import kotlinx.android.synthetic.main.item_history_row.view.*

class HistoryAdapter(val context: Context, val items: ArrayList<String>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(
                R.layout.item_history_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date : String = items.get(position)

        holder.position.text = (position + 1).toString()
        holder.item.text = date

        if (position % 2 == 0){
            holder.historyItem.setBackgroundColor(Color.parseColor("#ede4e4"))
        }else{
            holder.historyItem.setBackgroundColor(Color.WHITE)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val historyItem = view.history_item_main!!
        val item = view.item!!
        val position = view.position!!
    }
}