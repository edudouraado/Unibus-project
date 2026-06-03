package com.example.unibus

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class QrCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        // Lógica para o botão voltar
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack?.setOnClickListener {
            // O comando finish() encerra a tela atual
            // e volta para a tela que estava aberta por baixo (o Mapa)
            finish()
        }

        // Aqui virá sua lógica de gerar o QR Code depois...
    }
}