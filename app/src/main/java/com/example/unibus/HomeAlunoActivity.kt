package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class HomeAlunoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicial_aluno)

        // 1. Mapeia o CardView do botão "Rotas"
        val btnRotas = findViewById<CardView>(R.id.btnRotasAluno)

        // 2. Mapeia o item da barra inferior (opcional, mas recomendado)
        val navRotas = findViewById<TextView>(R.id.navRotasAluno)

        // Configura o clique no botão central
        btnRotas.setOnClickListener {
            val intent = Intent(this, RotasActivity::class.java)
            startActivity(intent)
        }

        // Configura o clique no ícone da barra inferior
        navRotas.setOnClickListener {
            val intent = Intent(this, RotasActivity::class.java)
            startActivity(intent)
        }

        // Exemplo para o botão de Suporte (caso queira configurar depois)
        val navSuporte = findViewById<TextView>(R.id.navSuporteAluno)
        navSuporte.setOnClickListener {
            val intent = Intent(this, SuporteActivity::class.java)
            startActivity(intent)
        }
    }
}