package com.example.unibus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import android.widget.ImageView

class GerenciarRotas1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gerenciar_rotas)

        // 1. Mapeia o botão de excluir da primeira rota (Parangaba)
        val btnDelete1 = findViewById<CardView>(R.id.btnDelete1)

        // 2. Configura o clique para abrir a tela activity_excluir_rota_2
        btnDelete1.setOnClickListener {
            val intent = Intent(this, ExcluirRota2Activity::class.java)
            startActivity(intent)
        }

        // 3. Configura o botão de voltar para retornar ao menu anterior
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        // OPCIONAL: Se quiser configurar a exclusão da segunda rota (Papicu)
        val btnDelete2 = findViewById<CardView>(R.id.btnDelete2)
        btnDelete2.setOnClickListener {
            // Se tiver uma tela específica para a segunda rota, mude aqui
            val intent = Intent(this, ExcluirRota2Activity::class.java)
            startActivity(intent)
        }
    }
}