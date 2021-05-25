package com.tonon.a7minuteworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_exercise_status.view.*

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>, val context: Context) : RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {


    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val item = view.item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_exercise_status, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel = items[position]

        holder.item.text = model.getId().toString()

        if (model.getIsSelected()){
            holder.item.background = ContextCompat.getDrawable(context, R.drawable.item_circular_thin_color_purple_border)
        }else if (model.getIsCompleted()){
            holder.item.background = ContextCompat.getDrawable(context, R.drawable. item_circular_purple_background)
            holder.item.setTextColor(Color.WHITE)
        }else{
            holder.item.background = ContextCompat.getDrawable(context, R.drawable.item_circular_color_gray_background)
        }
    }

    override fun getItemCount(): Int {
       return items.size
    }
}