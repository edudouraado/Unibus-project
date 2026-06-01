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

        auth = Firebase.auth

        val etMatricula = findViewById<EditText>(R.id.etMatricula)
        val etSenha = findViewById<EditText>(R.id.etSenha)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)
        val tvEsqueciSenha = findViewById<TextView>(R.id.tvEsqueciSenha)

        btnEntrar.setOnClickListener {
            val matricula = etMatricula.text.toString().trim()
            val senha = etSenha.text.toString().trim()

            if (matricula.isNotEmpty() && senha.isNotEmpty()) {
                val email = when {
                    matricula.contains("@") -> matricula
                    matricula.lowercase() == "adm" -> "adm@unibus.com"
                    matricula.lowercase() == "motorista" -> "motorista@unibus.com"
                    else -> "$matricula@unibus.com"
                }
                loginNoFirebase(email, senha)
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        tvEsqueciSenha.setOnClickListener {
            val intent = Intent(this, RecuperarSenhaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginNoFirebase(email: String, senha: String) {
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userEmail = auth.currentUser?.email?.lowercase() ?: ""
                    
                    // Lógica de Redirecionamento Atualizada
                    val intent = when (userEmail) {
                        "adm@unibus.com" -> Intent(this, InicialAdminActivity::class.java)
                        "motorista@unibus.com" -> Intent(this, InicialMotoristaActivity::class.java)
                        "eduardodourado.sdo@gmail.com" -> Intent(this, HomeAlunoActivity::class.java)
                        else -> Intent(this, RotasActivity::class.java)
                    }

                    Toast.makeText(this, "Bem-vindo ao Unibus!", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    finish()
                } else {
                    val erro = task.exception?.message ?: "Usuário ou senha incorretos"
                    Toast.makeText(this, "Falha no login: $erro", Toast.LENGTH_LONG).show()
                }
            }
    }
}
