package com.example.indecision

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EightBallActivity: AppCompatActivity()  {

    private var eightBallReply: TextView? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eightball)

        val shakeBtn: Button = findViewById(R.id.shakeBtn)
        eightBallReply = findViewById(R.id.eightBallReply)

        val possibleReplies = arrayOf(
            "It is certain.",
            "It is decidedly so.",
            "Without a doubt.",
            "Yes definitely.",
            "You may rely on it",
            "As I see it, yes.",
            "Most likely",
            "Outlook good.",
            "Yes.",
            "Signs point to yes.",
            "Reply hazy, try again.",
            "Ask again later.",
            "Better not tell you now.",
            "Cannot predict now.",
            "Concentrate and ask again.",
            "Don't count on it.",
            "My reply is no.",
            "My sources say no.",
            "Outlook not so good.",
            "Very doubtful."
        )

        shakeBtn.setOnClickListener {
            val pickedNumber = (possibleReplies.indices).random()
            val reply = possibleReplies[pickedNumber]

            eightBallReply?.text = reply
        }
    }
}