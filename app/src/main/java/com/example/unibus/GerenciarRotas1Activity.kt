package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class GerenciarRotas1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gerenciar_rotas_1)

        // Botão excluir Papicu nesta tela leva para a confirmação de exclusão
        findViewById<View>(R.id.btnDelete2).setOnClickListener {
            startActivity(Intent(this, ExcluirRotaActivity::class.java))
        }

        findViewById<View>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}
