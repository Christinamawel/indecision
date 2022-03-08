package com.example.indecision

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class OptionAdapter (
    val options: MutableList<String>
    ) : RecyclerView.Adapter<OptionAdapter.OptionViewHolder>() {

    private lateinit var binding: Activity

    class OptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        return OptionViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.options,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        val curOption = options[position]
        holder.tvOptionTitle

        }
    }

    override fun getItemCount(): Int {
        return options.size
    }
}