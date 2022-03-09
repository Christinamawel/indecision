package com.example.indecision

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.Exception

class QuestionListActivity : AppCompatActivity(){

    private var header: TextView? = null
    lateinit var auth: FirebaseAuth
    private val questionCollectionRef = Firebase.firestore.collection("questions")
    private lateinit var customAdapter: CustomAdapterListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val recyclerview = findViewById<RecyclerView>(R.id.rvDecisionList)
        header = findViewById(R.id.header)
        val listViewBtn: Button = findViewById(R.id.listViewBtn)

        auth = FirebaseAuth.getInstance()

        val defaultView = mutableListOf<Question>()
        customAdapter = CustomAdapterListView(defaultView)

        recyclerview.adapter = customAdapter
        recyclerview.layoutManager = LinearLayoutManager(this)


        retrieveDecisions()


        listViewBtn.setOnClickListener {
            if(auth.currentUser == null) {
                Intent(this, LoginActivity::class.java).also {
                    startActivity(it)
                }
            } else {
                Intent(this, QuestionFormActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }


    companion object {
           fun rollDecision(decision: Question) {

           }
    }

    private fun retrieveDecisions() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot = questionCollectionRef.get().await()
            val decisionList = mutableListOf<Question>()
            for(document in querySnapshot.documents) {
                val decision = document.toObject(Question::class.java)
                if (decision != null && decision.user == auth.currentUser?.email.toString()) {
                    decisionList.add(decision)
                }
            }
            withContext(Dispatchers.Main) {
                val recyclerview = findViewById<RecyclerView>(R.id.rvDecisionList)
                customAdapter = CustomAdapterListView(decisionList)
                recyclerview.adapter = customAdapter
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@QuestionListActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}