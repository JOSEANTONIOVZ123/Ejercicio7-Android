package com.example.cp06

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
class EditActivity : AppCompatActivity() {

    private lateinit var imageViewPelicula: ImageView
    private lateinit var editTextTitulo: EditText
    private lateinit var buttonGuardar: Button
    private var peliculaId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        imageViewPelicula = findViewById(R.id.imageViewPelicula)
        editTextTitulo = findViewById(R.id.editTextTitulo)
        buttonGuardar = findViewById(R.id.buttonGuardar)

        peliculaId = intent.getIntExtra("id_pelicula", -1)
        if (peliculaId != -1) {
            val pelicula = PeliculaProvider.listaCarga[peliculaId]
            editTextTitulo.setText(pelicula.titulo)
            imageViewPelicula.setImageResource(pelicula.imagen) // 📌 Muestra la imagen en la actividad
        }

        buttonGuardar.setOnClickListener {
            val nuevoTitulo = editTextTitulo.text.toString().trim()
            if (nuevoTitulo.isNotEmpty()) {
                PeliculaProvider.listaCarga[peliculaId].titulo = nuevoTitulo
                val resultIntent = Intent()
                resultIntent.putExtra("id_pelicula", peliculaId)
                resultIntent.putExtra("nuevo_titulo", nuevoTitulo)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}