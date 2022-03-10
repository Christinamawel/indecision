package com.example.indecision

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CustomAdapterListView(private val mList: List<Question>) : RecyclerView.Adapter<CustomAdapterListView.ViewHolder>() {

    private val questionCollectionRef = Firebase.firestore.collection("questions")
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
            val possibleOutcomes = mList[position].answers
            val pickedNumber = (0 until possibleOutcomes.size).random()
            val outcomeMapped = mutableMapOf<String, String>()
            val newOutcome = possibleOutcomes[pickedNumber]
            outcomeMapped["pickedAnswer"] = newOutcome
            val activity = holder.itemView.context as Activity
            val question = mList[position]

            updateChosenOption(question, outcomeMapped)

            Intent(activity, SecondActivity::class.java).also {
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

    private fun updateChosenOption(question: Question, chosenOptionMap: Map<String, String>) = CoroutineScope(Dispatchers.IO).launch {
        val questionQuery = questionCollectionRef
            .whereEqualTo("question", question.question)
            .whereEqualTo("answers", question.answers)
            .whereEqualTo("pickedAnswer", question.pickedAnswer)
            .whereEqualTo("user", question.user)
            .get()
            .await()
        if(questionQuery.documents.isNotEmpty()) {
            for(document in questionQuery) {
                try {
                    questionCollectionRef.document(document.id).set(
                        chosenOptionMap,
                        SetOptions.merge()
                    ).await()
                } catch(e: Exception){

                }
            }
        }
    }
}