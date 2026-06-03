package com.example.unibus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class SucessoInativacaoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sucesso_inativacao)

        val btnOk = findViewById<CardView>(R.id.btnOk)

        btnOk?.setOnClickListener {
            // APENAS ISTO:
            // Fecha este popup e volta para a tela que já estava aberta por baixo (GerenciarRotas)
            // Isso preserva a tela InicialAdmin lá no fundo da pilha.
            finish()
        }
    }
}