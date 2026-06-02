package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class RelatoriosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relatorios)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val tabInvalid = findViewById<CardView>(R.id.tabInvalid)
        val tabPeaks = findViewById<CardView>(R.id.tabPeaks)
        val tabAccessibility = findViewById<CardView>(R.id.tabAccessibility)

        btnBack.setOnClickListener {
            val intent = Intent(this, InicialAdminActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        tabInvalid.setOnClickListener {
            // Já está nesta tela, opcionalmente pode recarregar ou ignorar
        }

        tabPeaks.setOnClickListener {
            startActivity(Intent(this, RelatoriosPicosActivity::class.java))
            finish()
        }

        tabAccessibility.setOnClickListener {
            startActivity(Intent(this, RelatoriosSolicitacaoActivity::class.java))
            finish()
        }
    }
}
