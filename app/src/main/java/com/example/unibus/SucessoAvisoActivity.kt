package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class SucessoAvisoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sucesso_aviso)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val btnReturn = findViewById<CardView>(R.id.btnReturn)

        btnBack.setOnClickListener {
            finish()
        }

        btnReturn.setOnClickListener {
            val intent = Intent(this, InicialAdminActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}
