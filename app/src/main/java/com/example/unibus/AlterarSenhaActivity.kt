package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AlterarSenhaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alterar_senha)

        val btnSalvar = findViewById<Button>(R.id.btnSalvar)
        btnSalvar.setOnClickListener {
            // Lógica para salvar a nova senha
            Toast.makeText(this, "Senha alterada com sucesso!", Toast.LENGTH_SHORT).show()
            
            // Navega para a tela de erro (ou sucesso) do Leandro
            val intent = Intent(this, ErrorActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
