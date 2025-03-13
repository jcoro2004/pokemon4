package com.example.pokemon4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog

// Activitat principal de l'aplicació
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStartGame: Button = findViewById(R.id.btnStartGame)
        val btnPreferences: Button = findViewById(R.id.btnPreferences)
        val btnInfo: Button = findViewById(R.id.btnInfo)

        btnStartGame.setOnClickListener {
            val intent = Intent(this, EnterNameActivity::class.java)
            startActivity(intent)
        }

        btnPreferences.setOnClickListener {
            val intent = Intent(this, PreferencesActivity::class.java)
            startActivity(intent)
        }

        btnInfo.setOnClickListener {
            showInfoDialog()
        }
    }

    // Mostra un diàleg amb informació sobre l'aplicació
    private fun showInfoDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Informació")
        builder.setMessage("Aquesta aplicació va dirigida a gent que li agrada el món de Pokémon i que vol posar a prova els seus coneixements.")
        builder.setPositiveButton("D'acord", null)
        builder.show()
    }
}