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

class RotasMotoristaActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotas)

        // 1. Mapear os botões do XML
        val btnParangaba = findViewById<AppCompatButton>(R.id.btnRota1)
        val btnPapicu = findViewById<AppCompatButton>(R.id.btnRota2)
        val btnMessejana = findViewById<AppCompatButton>(R.id.btnRota3)

        // 2. Configurar comportamento em tempo real para cada rota
        configurarRotaMotorista("rota_parangaba", btnParangaba, "Parangaba > Campus")
        configurarRotaMotorista("rota_papicu", btnPapicu, "Papicu > Campus")
        configurarRotaMotorista("rota_messejana", btnMessejana, "Messejana > Campus")

        // Botão voltar
        findViewById<View>(R.id.btnVoltar)?.setOnClickListener { finish() }
    }

    private fun configurarRotaMotorista(docId: String, botao: AppCompatButton?, nomeRota: String) {
        // Escuta o Firebase em tempo real
        db.collection("rotas").document(docId).addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null) return@addSnapshotListener

            val isAtiva = snapshot.getBoolean("ativa") ?: true

            // Criar o formato redondo
            val shape = GradientDrawable()
            shape.shape = GradientDrawable.RECTANGLE
            shape.cornerRadius = 100f

            if (isAtiva) {
                // --- ROTA ATIVA: Botão Branco ---
                shape.setColor(Color.WHITE)
                botao?.background = shape
                botao?.setTextColor(Color.parseColor("#1A618D"))
                botao?.isEnabled = true

                botao?.setOnClickListener {
                    // Abre a tela de rota em andamento (Gráfico/Scanner)
                    // Procure onde está: val intent = Intent(this, RotaAndamentoActivity::class.java)
                    val intent = Intent(this, MapaMotoristaActivity::class.java)
                    intent.putExtra("ROTA_ID", docId)
                    intent.putExtra("NOME_ROTA", nomeRota)
                    startActivity(intent)
                }
            } else {
                // --- ROTA INATIVA: Botão Cinza ---
                shape.setColor(Color.LTGRAY)
                botao?.background = shape
                botao?.setTextColor(Color.WHITE)

                botao?.setOnClickListener {
                    // Avisa ao motorista que o Admin bloqueou esta rota
                    Toast.makeText(this, "Esta rota foi inativada pelo Administrador.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}