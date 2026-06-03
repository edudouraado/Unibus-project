package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class ConfirmarFinalizacaoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.popup_confirma_rota)

        val rotaId = intent.getStringExtra("ROTA_ID") ?: ""

        // Botão SIM
        findViewById<CardView>(R.id.btnSimConfirmar)?.setOnClickListener {
            val intent = Intent(this, SucessoFinalizacaoActivity::class.java)
            intent.putExtra("ROTA_ID", rotaId)
            startActivity(intent)
            finish()
        }

        // Botão NÃO
        findViewById<CardView>(R.id.btnNaoConfirmar)?.setOnClickListener {
            finish()
        }

        // Botão X (Fechar) - agora o ID existe no XML
        findViewById<View>(R.id.btnFecharConfirma)?.setOnClickListener {
            finish()
        }
    }
}
