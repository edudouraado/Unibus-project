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
import com.google.firebase.firestore.FirebaseFirestore
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

        // Lógica de Matrícula e Redirecionamento de Email Preservada
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

    private fun loginNoFirebase(email: String, senha: String) {auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this) { task ->
        if (task.isSuccessful) {
            val db = FirebaseFirestore.getInstance()
            db.collection("usuarios").whereEqualTo("email", email.lowercase()).get()
                .addOnSuccessListener { docs ->
                    // Dentro do addOnSuccessListener do login (onde o acesso é liberado)
                    val logAcesso = hashMapOf(
                        "data" to com.google.firebase.Timestamp.now(),
                        "tipo" to "login"
                    )
                    db.collection("acessos").add(logAcesso)
                    val userDoc = docs.documents.firstOrNull()
                    val isAtivo = userDoc?.getBoolean("acessoAtivo") ?: true

                    if (isAtivo) {
                        redirecionarUsuario(email.lowercase())
                    } else {
                        // LOG: Tentativa com conta inativa
                        registrarFalhaLogin(email, "Tentativa de acesso com conta INATIVA")

                        auth.signOut()
                        Toast.makeText(this, "Sua conta está inativa.", Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            // LOG: Erro de autenticação (senha errada ou usuário não existe)
            val erroMsg = task.exception?.message ?: "Senha incorreta ou usuário inexistente"
            registrarFalhaLogin(email, erroMsg)

            Toast.makeText(this, "Falha no login: Usuário ou senha inválidos", Toast.LENGTH_SHORT).show()
        }
    }
    }

    // FUNÇÃO QUE ESTAVA FALTANDO (MANTÉM TODO O FLUXO DE TELAS)
    private fun redirecionarUsuario(userEmail: String) {
        val intent = when (userEmail) {
            "adm@unibus.com" -> Intent(this, InicialAdminActivity::class.java)
            "motorista@unibus.com" -> Intent(this, InicialMotoristaActivity::class.java)
            "eduardodourado.sdo@gmail.com" -> Intent(this, HomeAlunoActivity::class.java)
            else -> Intent(this, HomeAlunoActivity::class.java)
        }
        startActivity(intent)
        finish()
    }

    private fun registrarFalhaLogin(email: String, motivo: String) {
        val db = FirebaseFirestore.getInstance()
        val logErro = hashMapOf(
            "email" to email,
            "motivo" to motivo,
            "data" to com.google.firebase.Timestamp.now()
        )

        db.collection("tentativas_invalidas")
            .add(logErro)
            .addOnFailureListener {
                // Silencioso: se não conseguir gravar o log, o usuário não precisa saber
            }
    }

}