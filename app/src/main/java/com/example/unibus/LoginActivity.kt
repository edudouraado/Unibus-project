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

        // MANTIDO: Lógica de clique no botão entrar
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

        // MANTIDO: Lógica do esqueci senha
        tvEsqueciSenha.setOnClickListener {
            val intent = Intent(this, RecuperarSenhaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginNoFirebase(email: String, senha: String) {
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // VERIFICAÇÃO DE STATUS NO FIRESTORE (A trava que incluímos)
                val db = FirebaseFirestore.getInstance()
                db.collection("usuarios").whereEqualTo("email", email.lowercase()).get()
                    .addOnSuccessListener { docs ->
                        val isAtivo = docs.documents.firstOrNull()?.getBoolean("acessoAtivo") ?: true

                        if (isAtivo) {
                            // ACESSO PERMITIDO: Chama a função de redirecionamento abaixo
                            Toast.makeText(this, "Bem-vindo!", Toast.LENGTH_SHORT).show()
                            redirecionarUsuario(email.lowercase())
                        } else {
                            // ACESSO NEGADO: Inativa o login
                            auth.signOut()
                            Toast.makeText(this, "Acesso negado: sua conta está inativa.", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                val erro = task.exception?.message ?: "Usuário ou senha incorretos"
                Toast.makeText(this, "Falha no login: $erro", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // MANTIDO: Função que decide para qual tela o usuário vai baseada no e-mail
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
}
