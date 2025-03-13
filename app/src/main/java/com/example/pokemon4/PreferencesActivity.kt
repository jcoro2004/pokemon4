package com.example.pokemon4

import android.os.Bundle
import android.widget.Button
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        recyclerViewUsers = findViewById(R.id.recyclerViewUsers)
        recyclerViewUsers.layoutManager = LinearLayoutManager(this)

        // Call fetchUsers during initialization to attach the adapter.
        fetchUsers()

        btnResetScore = findViewById(R.id.btnResetScore)
        btnResetScore.setOnClickListener {
            fetchUsers()  // This call will refresh the adapter on reset.
        }
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
                    fetchUsers() // Refresh the list after reset.
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