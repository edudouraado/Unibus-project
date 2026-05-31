package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AlterarSenhaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alterar_senha)

        val editNovaSenha = findViewById<EditText>(R.id.editNovaSenha)
        val editConfirmarSenha = findViewById<EditText>(R.id.editConfirmarSenha)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)

        btnSalvar.setOnClickListener {
            val novaSenha = editNovaSenha.text.toString()
            val confirmarSenha = editConfirmarSenha.text.toString()

            if (novaSenha.isNotEmpty() && confirmarSenha.isNotEmpty()) {
                if (novaSenha == confirmarSenha) {
                    // Aqui você implementaria a lógica real de atualização no Firebase se necessário.
                    // Para este teste, apenas simulamos o sucesso.
                    Toast.makeText(this, "Senha alterada com sucesso!", Toast.LENGTH_SHORT).show()

                    // Redireciona para a tela de Login
                    val intent = Intent(this, LoginActivity::class.java)
                    // Limpa a pilha de atividades para que o usuário não volte para a tela de alteração
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "As senhas não coincidem.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
