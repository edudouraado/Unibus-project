package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class GerenciarRotas2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gerenciar_rotas_2)

        // Botão excluir Parangaba nesta tela leva para a confirmação de exclusão
        findViewById<View>(R.id.btnDelete1).setOnClickListener {
            startActivity(Intent(this, ExcluirRota2Activity::class.java))
        }

        findViewById<View>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}
