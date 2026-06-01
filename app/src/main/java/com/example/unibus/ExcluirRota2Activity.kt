package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.firestore.FirebaseFirestore

class ExcluirRota2Activity : AppCompatActivity() {

    // Inicializa o Firestore
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excluir_rota_2)

        val btnSim = findViewById<CardView>(R.id.btnSim)
        val btnNao = findViewById<CardView>(R.id.btnNao)

        btnSim.setOnClickListener {
            desativarRotaNoFirebase("rota_parangaba") // Use o ID exato do documento no Firestore
        }

        btnNao.setOnClickListener {
            finish() // Apenas fecha o pop-up e volta
        }
    }

    private fun desativarRotaNoFirebase(rotaId: String) {
        // Acessa a coleção "rotas", o documento específico e muda o boolean
        db.collection("rotas").document(rotaId)
            .update("disponivel", false)
            .addOnSuccessListener {
                Toast.makeText(this, "Rota desativada com sucesso!", Toast.LENGTH_SHORT).show()

                // Leva para a tela de sucesso que você já tem
                val intent = Intent(this, SucessoExclusaoActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao desativar: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}