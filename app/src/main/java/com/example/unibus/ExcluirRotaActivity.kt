package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class ExcluirRotaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Se você excluiu o XML "activity_excluir_rota_2", mude para o seu XML de confirmação ativo
        setContentView(R.layout.activity_excluir_rota_2)

        val btnSim = findViewById<CardView>(R.id.btnSim)
        val btnNao = findViewById<CardView>(R.id.btnNao)

        btnSim?.setOnClickListener {
            // Garanta que esta Activity SucessoInativacaoActivity ainda exista
            val intent = Intent(this, SucessoInativacaoActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnNao?.setOnClickListener {
            finish()
        }
    }
}