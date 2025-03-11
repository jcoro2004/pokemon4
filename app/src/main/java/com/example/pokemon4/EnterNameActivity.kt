package com.example.pokemon4

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnterNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_name)

        val btnSubmit: Button = findViewById(R.id.btnSubmit)
        val editTextName: EditText = findViewById(R.id.editTextName)

        btnSubmit.setOnClickListener {
            val userName = editTextName.text.toString()
            if (userName.isNotEmpty()) {
                submitUserName(userName)
            } else {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun submitUserName(name: String) {
        val apiService = RetrofitClient.instance
        val call = apiService.submitName(name)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EnterNameActivity, "Name submitted successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@EnterNameActivity, "Failed to submit name", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@EnterNameActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}