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
        val btnOk = findViewById<androidx.cardview.widget.CardView>(R.id.btnOkAtivar) ?:
        findViewById<androidx.cardview.widget.CardView>(R.id.btnOk)

        btnOk?.setOnClickListener {
            // APENAS ISTO: Fecha o popup e você volta para a tela de Gerenciar Rotas que já está aberta
            finish()
        }
    }
}