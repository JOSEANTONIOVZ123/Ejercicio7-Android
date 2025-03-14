package com.example.cp06

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var peliculaAdapter: PeliculaAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var peliculas = mutableListOf<Pelicula>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar la Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Configurar el RecyclerView
        recyclerView = findViewById(R.id.recyclerViewPeliculas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Configurar SwipeRefreshLayout
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            recargarLista()
            swipeRefreshLayout.isRefreshing = false
        }

        // Cargar lista de películas
        cargarPeliculas()
    }

    private fun cargarPeliculas() {
        peliculas = PeliculaProvider.listaCarga.toMutableList()
        peliculaAdapter = PeliculaAdapter(peliculas, this)
        recyclerView.adapter = peliculaAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_recargar -> {
                recargarLista()
                true
            }
            R.id.menu_limpiar -> {
                confirmarEliminarLista()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun recargarLista() {
        cargarPeliculas()
        peliculaAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Lista recargada", Toast.LENGTH_SHORT).show()
    }

    private fun confirmarEliminarLista() {
        AlertDialog.Builder(this)
            .setTitle("Eliminar lista")
            .setMessage("¿Estás seguro de que deseas eliminar todas las películas?")
            .setPositiveButton("Sí") { _, _ -> limpiarLista() }
            .setNegativeButton("No", null)
            .show()
    }

    private fun limpiarLista() {
        peliculas.clear()
        peliculaAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Lista vaciada", Toast.LENGTH_SHORT).show()
    }
}
