// app/src/main/java/com/example/pokemon4/MainActivity.kt
package com.example.pokemon4

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog

class MainActivity : AppCompatActivity() {

    // MediaPlayer per reproduir la música de fons
    private var mediaPlayer: MediaPlayer? = null

    // SharedPreferences que emmagatzema les preferències de l'aplicació
    private lateinit var sharedPref: SharedPreferences

    // Listener per detectar canvis en la preferència "MUSIC_ENABLED"
    private val prefListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
        if (key == "MUSIC_ENABLED") {
            // Comprovem si la música està activada o desactivada
            val musicEnabled = sharedPreferences.getBoolean("MUSIC_ENABLED", true)
            if (musicEnabled) {
                // Si la música està activada, es crea o reprèn el MediaPlayer
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.background_music)
                    mediaPlayer?.isLooping = true
                }
                if (!mediaPlayer!!.isPlaying) {
                    mediaPlayer?.start()
                }
            } else {
                // Si la música està desactivada, es pausa el MediaPlayer si s'estava reproduint
                if (mediaPlayer?.isPlaying == true) {
                    mediaPlayer?.pause()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialitza les SharedPreferences i registra el listener per canvis
        sharedPref = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        sharedPref.registerOnSharedPreferenceChangeListener(prefListener)

        // Arrenca la música només si la preferència ho permet
        val musicEnabled = sharedPref.getBoolean("MUSIC_ENABLED", true)
        if (musicEnabled) {
            mediaPlayer = MediaPlayer.create(this, R.raw.background_music)
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
        }

        // Configura els botons per iniciar el joc, obrir preferències i mostrar informació
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
            mostrarDialegInformacio()
        }
    }

    // Mostra un dialeg que conté informació sobre l'aplicació
    private fun mostrarDialegInformacio() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Informació")
        builder.setMessage("Aquesta aplicació està orientada a persones a les que els agrada el món de Pokémon i volen posar a prova els seus coneixements.")
        builder.setPositiveButton("D'acord", null)
        builder.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Desregistra el listener per evitar fugides de memòria
        sharedPref.unregisterOnSharedPreferenceChangeListener(prefListener)
        // Allibera el MediaPlayer
        mediaPlayer?.release()
    }
}