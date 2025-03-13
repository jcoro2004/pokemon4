package com.example.pokemon4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adaptador per a la llista d'usuaris
class UserAdapter(
    private val users: List<Usuari>,
    private val onUserClick: (Usuari) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    // Crea un nou ViewHolder per a un element de la llista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    // Lliga les dades d'un usuari a un ViewHolder
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
        holder.itemView.setOnClickListener { onUserClick(user) }
    }

    // Retorna el nombre d'elements a la llista
    override fun getItemCount(): Int = users.size

    // ViewHolder per a un element de la llista d'usuaris
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textUserName: TextView = itemView.findViewById(R.id.textUserName)
        private val textUserScore: TextView = itemView.findViewById(R.id.textUserScore)

        // Lliga les dades d'un usuari als elements de la vista
        fun bind(user: Usuari) {
            textUserName.text = user.nom_usuari
            textUserScore.text = user.puntuacio.toString()
        }
    }
}