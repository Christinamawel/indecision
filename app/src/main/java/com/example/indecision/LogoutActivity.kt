package com.example.indecision

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LogoutActivity : AppCompatActivity() {

    private var tvLoggedIn: TextView? = null
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)

        tvLoggedIn = findViewById(R.id.tvLoggedIn)
        val logoutBtn: Button = findViewById(R.id.logoutBtn)
        auth = FirebaseAuth.getInstance()

        logoutBtn.setOnClickListener {
            auth.signOut()
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}