package com.example.unibus

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.firestore.FirebaseFirestore

class RotasActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotas)

        // 1. Mapear os botões conforme os IDs do seu XML
        val btnParangaba = findViewById<AppCompatButton>(R.id.btnRota1)
        val btnPapicu = findViewById<AppCompatButton>(R.id.btnRota2)
        val btnMessejana = findViewById<AppCompatButton>(R.id.btnRota3)

        // 2. Configurar a visibilidade baseada no Firestore (campo "ativa")
        configurarVisibilidadeDaRota("parangaba", btnParangaba)
        configurarVisibilidadeDaRota("papicu", btnPapicu)
        configurarVisibilidadeDaRota("messejana", btnMessejana)

        // Botão voltar
        findViewById<android.widget.ImageView>(R.id.btnVoltar).setOnClickListener {
            finish()
        }
    }

    private fun configurarVisibilidadeDaRota(documentoId: String, botao: AppCompatButton?) {
        db.collection("rotas").document(documentoId).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // Pega o boolean "ativa" do Firebase
                    val isAtiva = document.getBoolean("ativa") ?: true

                    if (isAtiva) {
                        botao?.visibility = View.VISIBLE
                    } else {
                        // Se o admin desativar, o botão some para o aluno
                        botao?.visibility = View.GONE
                    }
                }
            }
            .addOnFailureListener {
                // Em caso de erro (ex: sem internet), mantém o botão visível
                botao?.visibility = View.VISIBLE
            }
    }
}