package com.example.unibus

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
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

        // 1. Mapear os botões do seu XML
        val btnParangaba = findViewById<AppCompatButton>(R.id.btnRota1)
        val btnPapicu = findViewById<AppCompatButton>(R.id.btnRota2)
        val btnMessejana = findViewById<AppCompatButton>(R.id.btnRota3)

        // 2. Aplicar a lógica de verificação para cada rota em tempo real
        configurarComportamentoDaRota("rota_parangaba", btnParangaba, "Parangaba > Campus")
        configurarComportamentoDaRota("rota_papicu", btnPapicu, "Papicu > Campus")
        configurarComportamentoDaRota("rota_messejana", btnMessejana, "Messejana > Campus")

        // Botão voltar
        findViewById<View>(R.id.btnVoltar)?.setOnClickListener { finish() }
    }

    private fun configurarComportamentoDaRota(docId: String, botao: AppCompatButton?, nomeRota: String) {
        db.collection("rotas").document(docId).addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null) return@addSnapshotListener

            val isAtiva = snapshot.getBoolean("ativa") ?: true

            // Criar o formato redondo para o botão
            val shape = GradientDrawable()
            shape.shape = GradientDrawable.RECTANGLE
            shape.cornerRadius = 100f // Valor alto para garantir que as pontas fiquem redondas

            if (isAtiva) {
                // --- ROTA ATIVA: Botão Branco ---
                shape.setColor(Color.WHITE)
                botao?.background = shape

                // Definimos o texto em azul para dar contraste no fundo branco
                botao?.setTextColor(Color.parseColor("#1A618D"))
                botao?.isEnabled = true

                botao?.setOnClickListener {
                    val intent = Intent(this, MapaActivity::class.java)
                    intent.putExtra("NOME_ROTA", nomeRota)
                    startActivity(intent)
                }
            } else {
                // --- ROTA INATIVA: Botão Cinza ---
                shape.setColor(Color.LTGRAY)
                botao?.background = shape

                // Texto em branco no fundo cinza
                botao?.setTextColor(Color.WHITE)

                botao?.setOnClickListener {
                    Toast.makeText(this, "A rota $nomeRota está temporariamente indisponível.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}