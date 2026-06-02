package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChamadosDetalhadosActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var llChamadosLista: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chamados_detalhados)

        db = FirebaseFirestore.getInstance()
        llChamadosLista = findViewById(R.id.llChamadosLista)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        carregarChamadosDoFirebase()
    }

    private fun carregarChamadosDoFirebase() {
        db.collection("avisos")
            .whereEqualTo("respondido", false)
            .orderBy("data", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null || snapshots == null) return@addSnapshotListener

                llChamadosLista.removeAllViews()

                for (doc in snapshots) {
                    val texto = doc.getString("texto") ?: ""
                    val timestamp = doc.getTimestamp("data")
                    val timeMillis = timestamp?.toDate()?.time ?: 0L
                    val docId = doc.id
                    
                    adicionarItemChamado(texto, timeMillis, docId)
                }
            }
    }

    private fun adicionarItemChamado(texto: String, timeMillis: Long, docId: String) {
        val factory = LayoutInflater.from(this)
        val itemView = factory.inflate(R.layout.item_chamado_admin, llChamadosLista, false)

        val tvSubject = itemView.findViewById<TextView>(R.id.tvSubject1)
        val tvDate = itemView.findViewById<TextView>(R.id.tvDate1)
        val tvBody = itemView.findViewById<TextView>(R.id.tvBody1)
        val cvArrow = itemView.findViewById<CardView>(R.id.cvArrow1)

        tvSubject.text = "Unibus - Comunicado"
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        tvDate.text = if (timeMillis > 0) sdf.format(Date(timeMillis)) else "--/--/----"
        tvBody.text = texto

        // Ao clicar na setinha branca, vai para o detalhe
        cvArrow.setOnClickListener {
            val intent = Intent(this, ChamadoEspecificadoActivity::class.java)
            intent.putExtra("texto", texto)
            intent.putExtra("data", tvDate.text.toString())
            intent.putExtra("docId", docId)
            startActivity(intent)
        }

        llChamadosLista.addView(itemView)
    }
}
