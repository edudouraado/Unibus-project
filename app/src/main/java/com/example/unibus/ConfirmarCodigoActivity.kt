package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ConfirmarCodigoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_codigo)

        val editCodigo = findViewById<EditText>(R.id.editCodigo)
        val btnConfirmar = findViewById<Button>(R.id.btnConfirmar)

        btnConfirmar.setOnClickListener {
            val codigoInserido = editCodigo.text.toString()

            // Define o código padrão "123456"
            val codigoPadrao = "123456"

            if (codigoInserido == codigoPadrao) {
                // Se o código for igual ao padrão, vai para Alterar Senha
                val intent = Intent(this, AlterarSenhaActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Caso contrário, mostra um erro
                Toast.makeText(this, "Código inválido. Use 123456 para testes.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
