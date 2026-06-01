package com.example.unibus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

// Mude o nome aqui para SucessoExclusaoParangabaActivity
class SucessoExclusaoParangabaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sucesso_exclusao_parangaba)

        // Verificando o ID correto do seu XML (que é btnOk)
        val btnOk = findViewById<CardView>(R.id.btnOk)

        btnOk?.setOnClickListener {
            val intent = Intent(this, GerenciarRotasActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}