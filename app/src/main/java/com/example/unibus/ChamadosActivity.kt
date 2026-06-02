package com.example.unibus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import androidx.cardview.widget.CardView

class ChamadosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chamados)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val cvSeeMore = findViewById<CardView>(R.id.cvSeeMore)

        btnBack.setOnClickListener {
            finish()
        }

        cvSeeMore.setOnClickListener {
            startActivity(Intent(this, ChamadosDetalhadosActivity::class.java))
        }
    }
}
