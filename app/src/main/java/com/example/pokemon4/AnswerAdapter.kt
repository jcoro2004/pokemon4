package com.example.pokemon4

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class AnswerAdapter(
    private val respostes: List<Resposta>,
    private val onItemClick: (Resposta) -> Unit
) : RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder>() {

    private var selectedPosition = -1
    private var correctAnswerPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_resposta, parent, false)
        return AnswerViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        val resposta = respostes[position]
        holder.btnResposta.text = resposta.text_resposta

        when {
            position == selectedPosition && resposta.es_correcta == "1" -> holder.btnResposta.setBackgroundColor(Color.GREEN)
            position == selectedPosition && resposta.es_correcta == "0" -> holder.btnResposta.setBackgroundColor(Color.RED)
            position == correctAnswerPosition -> holder.btnResposta.setBackgroundColor(Color.GREEN)
            else -> holder.btnResposta.setBackgroundColor(Color.WHITE)
        }

        holder.btnResposta.setOnClickListener {
            selectedPosition = position
            correctAnswerPosition = respostes.indexOfFirst { it.es_correcta == "1" }
            notifyDataSetChanged()
            onItemClick(resposta)
        }
    }

    override fun getItemCount(): Int {
        return respostes.size
    }

    class AnswerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnResposta: Button = itemView.findViewById(R.id.btnResposta)
    }
}