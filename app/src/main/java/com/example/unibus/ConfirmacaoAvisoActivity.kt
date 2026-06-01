package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ConfirmacaoAvisoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmacao_aviso)

        val btnBackToHome = findViewById<Button>(R.id.btnBackToHome)
        val btnBackConfirm = findViewById<ImageView>(R.id.btnBackConfirm)

        btnBackToHome.setOnClickListener {
            // Retorna ao início do motorista
            val intent = Intent(this, InicialMotoristaActivity::class.java)
            // Limpa a pilha para que o usuário não consiga "voltar" para o sucesso
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        btnBackConfirm.setOnClickListener {
            // Apenas volta para a tela de avisos
            finish()
        }
    }
}