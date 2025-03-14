package com.example.cp06

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class PeliculaAdapter(private val peliculas: MutableList<Pelicula>, private val context: Context) :
    RecyclerView.Adapter<PeliculaAdapter.PeliculaViewHolder>() {

    inner class PeliculaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titulo: TextView = itemView.findViewById(R.id.textViewTitulo)
        val imagen: ImageView = itemView.findViewById(R.id.imageViewPelicula)

        init {
            // 🔥 Recuperamos la funcionalidad de click normal
            itemView.setOnClickListener {
                mostrarInfoPelicula(adapterPosition)
            }

            // 🔥 Mantenemos la funcionalidad de "long-click" para abrir el menú
            itemView.setOnLongClickListener {
                mostrarMenu(it, adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pelicula, parent, false)
        return PeliculaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        val pelicula = peliculas[position]
        holder.titulo.text = pelicula.titulo
        holder.imagen.setImageResource(pelicula.imagen)
    }

    override fun getItemCount(): Int = peliculas.size

    // 🔹 Método para mostrar información de la película al hacer click normal
    private fun mostrarInfoPelicula(position: Int) {
        val pelicula = peliculas[position]
        val mensaje = "Título: ${pelicula.titulo}\nDuración: ${pelicula.duracion} min\nAño: ${pelicula.anio}"
        Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
    }

    // 🔹 Método para mostrar menú contextual con opciones de editar y eliminar
    private fun mostrarMenu(view: View, position: Int) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.menu_contextual, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_editar -> {
                    val intent = Intent(context, EditActivity::class.java)
                    intent.putExtra("id_pelicula", position)
                    context.startActivity(intent)
                    true
                }
                R.id.menu_eliminar -> {
                    confirmarEliminarPelicula(position)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun confirmarEliminarPelicula(position: Int) {
        AlertDialog.Builder(context)
            .setTitle("Eliminar película")
            .setMessage("¿Estás seguro de que deseas eliminar esta película?")
            .setPositiveButton("Sí") { _, _ ->
                peliculas.removeAt(position)
                notifyItemRemoved(position)
            }
            .setNegativeButton("No", null)
            .show()
    }
}
