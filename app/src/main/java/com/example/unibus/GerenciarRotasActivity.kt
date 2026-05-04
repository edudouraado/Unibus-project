package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class GerenciarRotasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gerenciar_rotas)

        // Configura o botão de excluir da Rota 1 (Parangaba)
        findViewById<View>(R.id.btnDelete1).setOnClickListener {
            startActivity(Intent(this, ExcluirRota2Activity::class.java))
        }

        // Configura o botão de excluir da Rota 2 (Papicu)
        findViewById<View>(R.id.btnDelete2).setOnClickListener {
            startActivity(Intent(this, ExcluirRotaActivity::class.java))
        }

        findViewById<View>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}
