package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RecuperarSenhaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_senha)

        val auth = FirebaseAuth.getInstance()

        // Referenciando os componentes do XML
        val editEmail = findViewById<EditText>(R.id.editEmail)
        val btnEnviar = findViewById<Button>(R.id.btnEnviar)
        val btnVoltar = findViewById<TextView>(R.id.btnVoltar)

        // Configura o clique no botão ENVIAR
        btnEnviar.setOnClickListener {
            val emailDigitado = editEmail.text.toString().trim()

            if (emailDigitado.isNotEmpty()) {
                // Comando do Firebase para enviar o e-mail de recuperação
                auth.sendPasswordResetEmail(emailDigitado)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Link enviado com sucesso para o Firebase
                            Toast.makeText(this, "Link de recuperação enviado!", Toast.LENGTH_SHORT).show()

                            // Navega para a tela de confirmação (onde o usuário digita o código 123456)
                            val intent = Intent(this, ConfirmarCodigoActivity::class.java)
                            intent.putExtra("email_digitado", emailDigitado)
                            startActivity(intent)
                        } else {
                            // Erro comum: e-mail não existe no Firebase ou erro de rede
                            val erro = task.exception?.message ?: "Erro desconhecido"
                            Toast.makeText(this, "Erro: $erro", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                // Aviso caso o campo esteja vazio
                Toast.makeText(this, "Por favor, digite seu e-mail primeiro.", Toast.LENGTH_SHORT).show()
            }
        }

        // Configura o clique no botão VOLTAR
        btnVoltar.setOnClickListener {
            finish() // Fecha esta tela e volta para o Login
        }
    }
}