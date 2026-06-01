package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecuperarSenhaActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_senha)

        // Inicializar o Firebase Auth usando o padrão KTX (mesmo do LoginActivity)
        auth = Firebase.auth

        val editEmail = findViewById<EditText>(R.id.editEmail)
        val btnEnviar = findViewById<Button>(R.id.btnEnviar)
        val btnVoltar = findViewById<TextView>(R.id.btnVoltar)

        btnEnviar.setOnClickListener {
            val email = editEmail.text.toString().trim()

            if (email.isNotEmpty()) {
                // Envio real do e-mail via Firebase
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "E-mail de recuperação enviado!", Toast.LENGTH_SHORT).show()
                            
                            // NAVEGAÇÃO: Mesmo enviando o link, vamos para a tela de código para seu teste
                            val intent = Intent(this, ConfirmarCodigoActivity::class.java)
                            startActivity(intent)
                        } else {
                            val erro = task.exception?.message ?: "Erro ao enviar e-mail"
                            Toast.makeText(this, erro, Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Por favor, insira o seu e-mail.", Toast.LENGTH_SHORT).show()
            }
        }

        btnVoltar.setOnClickListener {
            finish()
        }
    }
}
