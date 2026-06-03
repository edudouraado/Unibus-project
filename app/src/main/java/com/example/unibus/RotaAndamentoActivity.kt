package com.example.unibus

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class RotaAndamentoActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private var rotaId: String = "rota_parangaba"

    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            db.collection("rotas").document(rotaId)
                .update("lotacao_atual", FieldValue.increment(1))
                .addOnSuccessListener {
                    Toast.makeText(this, "Embarque validado!", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rota_andamento)

        rotaId = intent.getStringExtra("ROTA_ID") ?: "rota_parangaba"

        val btnScan = findViewById<CardView>(R.id.btnScanQrCode)
        btnScan?.setOnClickListener {
            val options = ScanOptions()
            options.setPrompt("Aproxime o QR Code do Aluno")
            options.setBeepEnabled(true)
            options.setOrientationLocked(true)
            barcodeLauncher.launch(options)
        }

        findViewById<android.view.View>(R.id.btnBack)?.setOnClickListener { finish() }

        ouvirLotacaoRealTime()
    }

    private fun ouvirLotacaoRealTime() {
        val pbCircle = findViewById<ProgressBar>(R.id.pbCircle)
        val tvPorcentagem = findViewById<TextView>(R.id.tvPorcentagem)
        val tvOcupados = findViewById<TextView>(R.id.tvOcupadosValue)
        val tvLivres = findViewById<TextView>(R.id.tvLivresValue)

        db.collection("rotas").document(rotaId).addSnapshotListener { snapshot, error ->
            if (error != null) return@addSnapshotListener

            if (snapshot != null && snapshot.exists()) {
                val ocupados = snapshot.getDouble("lotacao_atual") ?: 0.0
                val capacidade = snapshot.getDouble("capacidade_maxima") ?: 40.0

                val livres = capacidade - ocupados
                val porcentagem = if (capacidade > 0) ((ocupados / capacidade) * 100).toInt() else 0

                tvOcupados?.text = ocupados.toInt().toString()
                tvLivres?.text = livres.toInt().toString()
                tvPorcentagem?.text = "$porcentagem%"
                pbCircle?.progress = porcentagem
            }
        }
    }
}