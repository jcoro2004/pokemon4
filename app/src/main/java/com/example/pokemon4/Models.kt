package com.example.pokemon4

// Model per a una pregunta
data class Pregunta(
    val id_pregunta: String,
    val text_pregunta: String
)

// Model per a una resposta
data class Resposta(
    val id_resposta: String,
    val id_pregunta: String,
    val text_resposta: String,
    val es_correcta: String
)

// Model per a un usuari
data class Usuari(
    val id_usuari: Int,
    val nom_usuari: String,
    val puntuacio: Int
)

// Model per a la resposta de l'API
data class ApiResponse(
    val preguntes: List<Pregunta>,
    val respostes: List<Resposta>,
    val usuaris: List<Usuari>
)