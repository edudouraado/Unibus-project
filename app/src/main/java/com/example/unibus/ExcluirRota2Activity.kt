package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ExcluirRota2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excluir_rota_2)

        findViewById<View>(R.id.btnSim).setOnClickListener {
            val intent = Intent(this, SucessoExclusaoParangabaActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<View>(R.id.btnNao).setOnClickListener {
            finish()
        }

        findViewById<View>(R.id.btnClosePopup).setOnClickListener {
            finish()
        }
    }
}
