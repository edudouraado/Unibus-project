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

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 1. Inicializar o Firebase Auth
        auth = Firebase.auth

        // 2. Referenciar os elementos da UI
        val etMatricula = findViewById<EditText>(R.id.etMatricula)
        val etSenha = findViewById<EditText>(R.id.etSenha)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)
        val tvEsqueciSenha = findViewById<TextView>(R.id.tvEsqueciSenha)

        // 3. Configurar o clique do botão entrar
        btnEntrar.setOnClickListener {
            val matricula = etMatricula.text.toString()
            val senha = etSenha.text.toString()

            if (matricula.isNotEmpty() && senha.isNotEmpty()) {
                val email = if (matricula.contains("@")) matricula else "$matricula@unibus.com"
                loginNoFirebase(email, senha)
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        // 4. Configurar o clique de "Esqueci a Senha"
        tvEsqueciSenha.setOnClickListener {
            val intent = Intent(this, RecuperarSenhaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginNoFirebase(email: String, senha: String) {
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Bem-vindo ao Unibus!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MapaActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val erro = task.exception?.message ?: "Erro desconhecido"
                    Toast.makeText(this, "Falha no login: $erro", Toast.LENGTH_LONG).show()
                }
            }
    }
}
