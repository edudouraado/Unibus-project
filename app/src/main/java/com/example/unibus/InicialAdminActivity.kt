package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.firestore.FirebaseFirestore

class InicialAdminActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicial_admin)

        db = FirebaseFirestore.getInstance()

        val btnManageRoutes = findViewById<CardView>(R.id.btnManageRoutes)
        val cvTicketsCount = findViewById<CardView>(R.id.cvTicketsCount)
        val tvTicketsCount = findViewById<TextView>(R.id.tvTicketsCount)

        btnManageRoutes.setOnClickListener {
            val intent = Intent(this, GerenciarRotasActivity::class.java)
            startActivity(intent)
        }

        cvTicketsCount.setOnClickListener {
            val intent = Intent(this, ChamadosDetalhadosActivity::class.java)
            startActivity(intent)
        }

        atualizarContadorDeAvisos(tvTicketsCount)
    }

    private fun atualizarContadorDeAvisos(tvCount: TextView) {
        db.collection("avisos")
            .whereEqualTo("respondido", false)
            .addSnapshotListener { snapshots, e ->
                if (e != null) return@addSnapshotListener
                val count = snapshots?.size() ?: 0
                tvCount.text = count.toString()
            }
    }
}
