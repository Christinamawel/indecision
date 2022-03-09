package com.example.indecision

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapterListView(private val mList: List<Question>) : RecyclerView.Adapter<CustomAdapterListView.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.decisions, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val decisionViewModel = mList[position].question

        // sets the text to the textview from our itemHolder class
        holder.textView.text = decisionViewModel

        holder.reRollBtn.setOnClickListener {
            val activity = holder.itemView.context as Activity
            val question = mList[position]
            val intent = Intent(activity, SecondActivity::class.java).also {
                it.putExtra("EXTRA_QUESTION", question)
               activity.startActivity(it)
            }
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

//     Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        val reRollBtn: Button = itemView.findViewById(R.id.reRollBtn)
    }
}