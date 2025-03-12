package com.example.pokemon4

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScoreActivity : AppCompatActivity() {

    private lateinit var textScore: TextView
    private var score = 0
    private var userName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        textScore = findViewById(R.id.textScore)
        score = intent.getIntExtra("SCORE", 0)
        userName = intent.getStringExtra("USER_NAME")

        if (userName == null) {
            Toast.makeText(this, "User name is missing", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        textScore.text = "Your score: $score"

        updateScoreInDatabase()
    }

    private fun updateScoreInDatabase() {
        val apiService = RetrofitClient.instance
        val call = apiService.updateScore(userName!!, score)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ScoreActivity, "Score updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ScoreActivity, "Failed to update score", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@ScoreActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}