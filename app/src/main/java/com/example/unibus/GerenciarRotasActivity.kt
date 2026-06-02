package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore

class GerenciarRotasActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gerenciar_rotas)

        // Configuração ÚNICA do botão voltar
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack?.setOnClickListener {
            finish() // Apenas fecha esta tela e volta para a que estava aberta antes (InicialAdmin)
        }

        // --- ROTA 1: PARANGABA ---
        findViewById<CardView>(R.id.btnAtivar1)?.setOnClickListener {
            atualizarStatusNoFirebase("rota_parangaba", true)
        }
        findViewById<CardView>(R.id.btnInativar1)?.setOnClickListener {
            atualizarStatusNoFirebase("rota_parangaba", false)
        }

        // --- ROTA 2: PAPICU ---
        findViewById<CardView>(R.id.btnAtivar2)?.setOnClickListener {
            atualizarStatusNoFirebase("rota_papicu", true)
        }
        findViewById<CardView>(R.id.btnInativar2)?.setOnClickListener {
            atualizarStatusNoFirebase("rota_papicu", false)
        }
    }

    private fun atualizarStatusNoFirebase(rotaId: String, novoStatus: Boolean) {
        db.collection("rotas").document(rotaId)
            .update("ativa", novoStatus)
            .addOnSuccessListener {
                if (novoStatus) {
                    startActivity(Intent(this, SucessoAtivacaoActivity::class.java))
                } else {
                    startActivity(Intent(this, SucessoInativacaoActivity::class.java))
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao atualizar no Firebase", Toast.LENGTH_SHORT).show()
            }
    }
}