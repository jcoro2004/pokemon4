package com.example.pokemon4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnInfo: Button = findViewById(R.id.btnInfo)
        val btnStartGame: Button = findViewById(R.id.btnStartGame)
        val btnPreferences: Button = findViewById(R.id.btnPreferences)

        btnInfo.setOnClickListener {
            // Afegeix el codi per mostrar informació sobre l'aplicació
        }

        btnStartGame.setOnClickListener {
            val intent = Intent(this, EnterNameActivity::class.java)
            startActivity(intent)
        }

        btnPreferences.setOnClickListener {
            // Afegeix el codi per obrir les preferències
        }
    }
}