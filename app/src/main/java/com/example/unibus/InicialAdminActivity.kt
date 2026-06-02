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
        // Dentro do onCreate
        val tvActiveStudentsValue = findViewById<TextView>(R.id.tvActiveStudentsValue)
        val tvTripsCountValue = findViewById<TextView>(R.id.tvTripsCountValue)
        val tvDriversCountValue = findViewById<TextView>(R.id.tvDriversCountValue)
        val tvTicketsCount = findViewById<TextView>(R.id.tvTicketsCount)

        val cvTicketsCount = findViewById<CardView>(R.id.cvTicketsCount)
        val btnManageRoutes = findViewById<CardView>(R.id.btnManageRoutes)
        val btnGeneralStats = findViewById<CardView>(R.id.btnGeneralStats)
        val btnReports = findViewById<CardView>(R.id.btnReports)

        cvTicketsCount?.setOnClickListener {
            startActivity(Intent(this, ChamadosDetalhadosActivity::class.java))
        }

        btnManageRoutes?.setOnClickListener {
            startActivity(Intent(this, GerenciarRotasActivity::class.java))
        }

        btnGeneralStats?.setOnClickListener {
            startActivity(Intent(this, EstatisticasActivity::class.java))
        }

        btnReports?.setOnClickListener {
            startActivity(Intent(this, RelatoriosActivity::class.java))
        }

        // Ligar os contadores em tempo real
        ouvirContagemFirebase("usuarios", "perfil", "aluno", tvActiveStudentsValue)
        ouvirContagemFirebase("rotas", "ativa", true, tvTripsCountValue)
        ouvirContagemFirebase("usuarios", "perfil", "motorista", tvDriversCountValue)
        ouvirContagemFirebase("avisos", "respondido", false, tvTicketsCount)
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