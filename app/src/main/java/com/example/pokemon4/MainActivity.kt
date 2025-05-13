package com.example.pokemon4

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.gesture.Gesture
import android.gesture.GestureLibraries
import android.gesture.GestureLibrary
import android.gesture.GestureOverlayView
import android.widget.Toast

class MainActivity : AppCompatActivity(), GestureOverlayView.OnGesturePerformedListener {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var sharedPref: SharedPreferences
    private lateinit var telephonyManager: TelephonyManager
    private lateinit var phoneStateListener: PhoneStateListener
    private lateinit var gestureLibrary: GestureLibrary
    private val REQUEST_READ_PHONE_STATE = 100

    private val prefListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == "MUSIC_ENABLED") {
                val musicEnabled = sharedPreferences.getBoolean("MUSIC_ENABLED", true)
                if (musicEnabled) {
                    if (mediaPlayer == null) {
                        mediaPlayer = MediaPlayer.create(this, R.raw.background_music)
                        mediaPlayer?.isLooping = true
                    }
                    if (!mediaPlayer!!.isPlaying) {
                        mediaPlayer?.start()
                    }
                } else {
                    if (mediaPlayer?.isPlaying == true) {
                        mediaPlayer?.pause()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        sharedPref.registerOnSharedPreferenceChangeListener(prefListener)

        val musicEnabled = sharedPref.getBoolean("MUSIC_ENABLED", true)
        if (musicEnabled) {
            mediaPlayer = MediaPlayer.create(this, R.raw.background_music)
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
        }

        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        phoneStateListener = object : PhoneStateListener() {
            override fun onCallStateChanged(state: Int, incomingNumber: String?) {
                when (state) {
                    TelephonyManager.CALL_STATE_RINGING,
                    TelephonyManager.CALL_STATE_OFFHOOK -> {
                        if (mediaPlayer?.isPlaying == true) {
                            mediaPlayer?.pause()
                        }
                    }
                    TelephonyManager.CALL_STATE_IDLE -> {
                        if (sharedPref.getBoolean("MUSIC_ENABLED", true)) {
                            if (mediaPlayer != null && !mediaPlayer!!.isPlaying) {
                                mediaPlayer?.start()
                            }
                        }
                    }
                }
            }
        }

        // Verifica y solicita el permiso READ_PHONE_STATE
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                REQUEST_READ_PHONE_STATE
            )
        } else {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
        }

        // Configuración de botones
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

        // Configuración de gestos
        gestureLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures)
        if (!gestureLibrary.load()) {
            Toast.makeText(this, "Error carregant els gestos", Toast.LENGTH_SHORT).show()
            finish()
        }

        val gestureOverlayView: GestureOverlayView = findViewById(R.id.gestureOverlay)
        gestureOverlayView.addOnGesturePerformedListener(this)
    }

    override fun onGesturePerformed(overlay: GestureOverlayView?, gesture: Gesture?) {
        val predictions = gestureLibrary.recognize(gesture)
        if (predictions.isNotEmpty() && predictions[0].score > 1.0) {
            when (predictions[0].name) {
                "Start" -> {
                    val intent = Intent(this, EnterNameActivity::class.java)
                    startActivity(intent)
                }
                "Info" -> {
                    mostrarDialegInformacio()
                }
                "Preferences" -> {
                    val intent = Intent(this, PreferencesActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                    Toast.makeText(this, "Gest no reconegut", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_READ_PHONE_STATE && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
        }
    }

    private fun mostrarDialegInformacio() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Informació")
        builder.setMessage("Aquesta aplicació està orientada a persones a les que els agrada el món de Pokémon i volen posar a prova els seus coneixements.")
        builder.setPositiveButton("D'acord", null)
        builder.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPref.unregisterOnSharedPreferenceChangeListener(prefListener)
        mediaPlayer?.release()
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE)
    }
}