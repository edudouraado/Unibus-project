package com.example.unibus

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SuporteActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suporte)

        // 1. Mapeia os componentes do seu XML
        val etMensagem = findViewById<EditText>(R.id.etSuporteMensagem)
        val btnEnviar = findViewById<Button>(R.id.btnEnviarSuporte)
        val btnBack = findViewById<android.view.View>(R.id.btnBack)

        // 2. Configura o botão Voltar
        btnBack?.setOnClickListener { finish() }

        // 3. Configura o envio da mensagem
        btnEnviar?.setOnClickListener {
            val texto = etMensagem.text.toString().trim()
            if (texto.isNotEmpty()) {
                enviarMensagemSuporte(texto)
            } else {
                Toast.makeText(this, "Por favor, digite sua mensagem", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun enviarMensagemSuporte(textoDoAluno: String) {
        val emailAluno = FirebaseAuth.getInstance().currentUser?.email ?: "Anônimo"

        val dadosSuporte = hashMapOf(
            "texto" to "SUPORTE ($emailAluno): $textoDoAluno",
            "data" to com.google.firebase.Timestamp.now(),
            "respondido" to false // Crucial para o Admin receber na lista de chamados
        )

        db.collection("avisos")
            .add(dadosSuporte)
            .addOnSuccessListener {
                Toast.makeText(this, "Mensagem enviada com sucesso!", Toast.LENGTH_SHORT).show()
                finish() // Fecha a tela após enviar
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao enviar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}