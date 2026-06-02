package com.example.unibus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class SucessoAtivacaoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sucesso_ativacao)

        // Dentro do onCreate da SucessoAtivacaoActivity e da SucessoInativacaoActivity
        val btnOk = findViewById<CardView>(R.id.btnOkAtivar) // ou btnOk

        btnOk?.setOnClickListener {
            finish() // ISSO É O MAIS IMPORTANTE: Apenas fecha o popup e você já estará na tela de rotas
        }
    }
}