package com.example.unibus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class SucessoFinalizacaoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.popup_sucesso_rota)

        val rotaId = intent.getStringExtra("ROTA_ID") ?: ""

        // RESETA FIREBASE (Mantendo sua lógica)
        if (rotaId.isNotEmpty()) {
            FirebaseFirestore.getInstance().collection("rotas")
                .document(rotaId).update("lotacao_atual", 0.0)
        }

        // Botão X ou Botão OK para fechar
        findViewById<android.view.View>(R.id.btnFecharSucesso)?.setOnClickListener {
            val intent = Intent(this, InicialMotoristaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}