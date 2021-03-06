package com.example.indecision

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(val mList: MutableList<OptionViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.options, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val optionViewModel = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.textView.text = optionViewModel.text

        holder.optionDeleteBtn.setOnClickListener {
            removeOption(mList[position], position)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        val optionDeleteBtn: Button = itemView.findViewById(R.id.optionDeleteBtn)
    }

    fun addOption(option: OptionViewModel) {
        mList.add(option)
        notifyItemInserted(mList.size - 1)
    }

    fun removeOption(option: OptionViewModel, position: Int) {
        mList.remove(option)
        notifyItemRemoved(position)
    }
}