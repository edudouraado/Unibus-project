package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.firestore.FirebaseFirestore

class InicialAdminActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicial_admin)

        // Mapeando os campos do XML
        val tvTripsCountValue = findViewById<TextView>(R.id.tvTripsCountValue)
        val tvDriversCountValue = findViewById<TextView>(R.id.tvDriversCountValue)
        val btnManageRoutes = findViewById<CardView>(R.id.btnManageRoutes)

        btnManageRoutes?.setOnClickListener {
            startActivity(Intent(this, GerenciarRotasActivity::class.java))
        }

        // Ligar os contadores em tempo real
        ouvirContagemFirebase("rotas", "ativa", true, tvTripsCountValue)
        ouvirContagemFirebase("usuarios", "perfil", "motorista", tvDriversCountValue)
    }

    private fun ouvirContagemFirebase(colecao: String, campo: String, valor: Any, textView: TextView?) {
        db.collection(colecao)
            .whereEqualTo(campo, valor)
            .addSnapshotListener { snapshots, error ->
                if (error == null && snapshots != null) {
                    textView?.text = snapshots.size().toString()
                }
            }
    }
}