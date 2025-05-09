package com.example.pokemon4

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.media.MediaPlayer

// Activitat per mostrar les preguntes i respostes
class QuestionActivity : AppCompatActivity() {

    private lateinit var textPregunta: TextView
    private lateinit var btnNext: Button
    private lateinit var recyclerViewAnswers: RecyclerView
    private var preguntes: List<Pregunta> = listOf()
    private var respostes: List<Resposta> = listOf()
    private var currentIndex = 0
    private var selectedAnswer: Resposta? = null
    private var score = 0
    private var userName: String? = null
    private var numberOfQuestions = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        textPregunta = findViewById(R.id.textPregunta)
        btnNext = findViewById(R.id.btnNext)
        recyclerViewAnswers = findViewById(R.id.recyclerViewAnswers)
        recyclerViewAnswers.layoutManager = LinearLayoutManager(this)

        userName = intent.getStringExtra("USER_NAME")
        if (userName == null) {
            Toast.makeText(this, "User name is missing", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val sharedPref = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        numberOfQuestions = sharedPref.getInt("NUMBER_OF_QUESTIONS", 5)

        btnNext.setOnClickListener {
            if (selectedAnswer != null) {
                if (selectedAnswer!!.es_correcta == "1") {
                    score++
                }
                showNextQuestion()
            } else {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
            }
        }

        fetchQuestions()
    }

    // Función para reproducir efecto sonoro
    private fun playSoundEffect() {
        val sharedPref = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        if (sharedPref.getBoolean("SOUND_EFFECT_ENABLED", true)) {
            val mediaPlayer = MediaPlayer.create(this, R.raw.correct_choice)
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener { it.release() }
        }
    }

    // Obté les preguntes de l'API
    private fun fetchQuestions() {
        val apiService = RetrofitClient.instance
        val call = apiService.getData()
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null) {
                        preguntes = apiResponse.preguntes.shuffled().take(numberOfQuestions)
                        respostes = apiResponse.respostes
                        showNextQuestion()
                    }
                } else {
                    Toast.makeText(this@QuestionActivity, "Failed to load questions", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(this@QuestionActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Mostra la següent pregunta
    private fun showNextQuestion() {
        if (currentIndex < preguntes.size) {
            val pregunta = preguntes[currentIndex]
            textPregunta.text = pregunta.text_pregunta
            val filteredRespostes = respostes.filter { it.id_pregunta == pregunta.id_pregunta }
            recyclerViewAnswers.adapter = AnswerAdapter(filteredRespostes) { resposta ->
                selectedAnswer = resposta
                playSoundEffect()
                if (resposta.es_correcta == "1") {
                    updateScoreInDatabase()
                }
            }
            currentIndex++
        } else {
            val intent = Intent(this, ScoreActivity::class.java)
            intent.putExtra("SCORE", score)
            intent.putExtra("USER_NAME", userName)
            startActivity(intent)
            finish()
        }
    }

    // Actualitza la puntuació a la base de dades
    private fun updateScoreInDatabase() {
        val apiService = RetrofitClient.instance
        val call = apiService.incrementScore(userName!!)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@QuestionActivity, "Score updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@QuestionActivity, "Failed to update score", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@QuestionActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}