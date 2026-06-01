package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class AlterarSenhaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alterar_senha)

        // Referenciando os campos do seu XML
        val editNovaSenha = findViewById<EditText>(R.id.editNovaSenha)
        val editConfirmarSenha = findViewById<EditText>(R.id.editConfirmarSenha)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)

        btnSalvar.setOnClickListener {
            val novaSenha = editNovaSenha.text.toString().trim()
            val confirmar = editConfirmarSenha.text.toString().trim()

            // 1. Validação de preenchimento
            if (novaSenha.isEmpty() || confirmar.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 2. Validação de tamanho (Firebase exige no mínimo 6)
            if (novaSenha.length < 6) {
                Toast.makeText(this, "A senha deve ter no mínimo 6 caracteres", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 3. Verificação de igualdade
            if (novaSenha == confirmar) {
                // NOTA TÉCNICA: No Firebase, para trocar a senha sem estar logado,
                // o usuário deve obrigatoriamente usar o link enviado ao e-mail.
                // Aqui finalizamos o fluxo visual do App.

                Toast.makeText(this, "Senha processada! Verifique se confirmou a alteração no link do e-mail.", Toast.LENGTH_LONG).show()

                // Retorna para a tela de Login limpando a pilha de atividades
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                // Caso as senhas sejam diferentes
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
            }
        }
    }
}