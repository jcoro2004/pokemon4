package com.example.pokemon4

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreferencesActivity : AppCompatActivity() {

    private lateinit var recyclerViewUsers: RecyclerView
    private lateinit var btnResetScore: Button
    private lateinit var numberPicker: NumberPicker
    private lateinit var btnSavePreferences: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        recyclerViewUsers = findViewById(R.id.recyclerViewUsers)
        recyclerViewUsers.layoutManager = LinearLayoutManager(this)

        // Setup the NumberPicker between 5 and 10 questions.
        numberPicker = findViewById(R.id.numberPicker)
        numberPicker.minValue = 5
        numberPicker.maxValue = 10

        val sharedPref = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val savedQuestions = sharedPref.getInt("NUMBER_OF_QUESTIONS", 5)
        numberPicker.value = savedQuestions

        btnResetScore = findViewById(R.id.btnResetScore)
        btnResetScore.setOnClickListener {
            fetchUsers() // Refresh the list when resetting score.
        }

        btnSavePreferences = findViewById(R.id.btnSavePreferences)
        btnSavePreferences.setOnClickListener {
            val selectedQuestions = numberPicker.value
            sharedPref.edit().putInt("NUMBER_OF_QUESTIONS", selectedQuestions).apply()
            Toast.makeText(
                this,
                "Preferences saved: $selectedQuestions questions",
                Toast.LENGTH_SHORT
            ).show()
        }

        fetchUsers()
    }

    private fun fetchUsers() {
        val apiService = RetrofitClient.instance
        val call = apiService.getUsers()
        call.enqueue(object : Callback<List<Usuari>> {
            override fun onResponse(call: Call<List<Usuari>>, response: Response<List<Usuari>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    if (users != null) {
                        recyclerViewUsers.adapter = UserAdapter(users) { user ->
                            resetUserScore(user.id_usuari)
                        }
                    }
                } else {
                    Toast.makeText(this@PreferencesActivity, "Failed to load users", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Usuari>>, t: Throwable) {
                Toast.makeText(this@PreferencesActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun resetUserScore(userId: Int) {
        val apiService = RetrofitClient.instance
        val call = apiService.resetScore(userId)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@PreferencesActivity, "Score reset successfully", Toast.LENGTH_SHORT).show()
                    fetchUsers() // Refresh after score reset.
                } else {
                    Toast.makeText(this@PreferencesActivity, "Failed to reset score", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@PreferencesActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}