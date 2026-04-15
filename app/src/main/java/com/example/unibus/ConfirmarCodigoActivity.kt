package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmarCodigoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_codigo)

        val btnConfirmar = findViewById<Button>(R.id.btnConfirmar)
        btnConfirmar.setOnClickListener {
            val intent = Intent(this, AlterarSenhaActivity::class.java)
            startActivity(intent)
        }

        val btnReenviar = findViewById<TextView>(R.id.btnReenviar)
        btnReenviar.setOnClickListener {
            // Lógica para reenviar código
        }
    }
}
