package com.example.pokemon4

data class Pregunta(
    val id_pregunta: String,
    val text_pregunta: String
)

data class Resposta(
    val id_resposta: String,
    val id_pregunta: String,
    val text_resposta: String,
    val es_correcta: String
)

data class Usuari(
    val id_usuari: Int,
    val nom_usuari: String,
    val puntuacio: Int
)

data class ApiResponse(
    val preguntes: List<Pregunta>,
    val respostes: List<Resposta>,
    val usuaris: List<Usuari>
)
