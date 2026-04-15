package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RecuperarSenhaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_senha)

        val btnEnviar = findViewById<Button>(R.id.btnEnviar)
        btnEnviar.setOnClickListener {
            // Aqui iria a lógica de envio de e-mail
            val intent = Intent(this, ConfirmarCodigoActivity::class.java)
            startActivity(intent)
        }

        val btnVoltar = findViewById<TextView>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish()
        }
    }
}
