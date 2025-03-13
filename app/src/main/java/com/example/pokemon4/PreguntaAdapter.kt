package com.example.pokemon4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adaptador per a la llista de preguntes
class PreguntaAdapter(private val preguntes: List<Pregunta>) :
    RecyclerView.Adapter<PreguntaAdapter.PreguntaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreguntaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pregunta, parent, false)
        return PreguntaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PreguntaViewHolder, position: Int) {
        val pregunta = preguntes[position]
        holder.textPregunta.text = pregunta.text_pregunta
    }

    override fun getItemCount(): Int {
        return preguntes.size
    }

    // ViewHolder per a cada element de la llista
    class PreguntaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textPregunta: TextView = itemView.findViewById(R.id.textPregunta)
    }
}