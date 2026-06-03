package com.example.unibus

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class InicialMotoristaActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var llAvisosPreview: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicial_motorista)

        db = FirebaseFirestore.getInstance()
        llAvisosPreview = findViewById(R.id.llAvisosPreview)

        // --- FUNÇÃO 1: Abrir todos os avisos pelo botão COMUNICADOS ---
        val cvNotices = findViewById<CardView>(R.id.cvNotices)
        cvNotices?.setOnClickListener {
            val intent = Intent(this, AvisosMotoristaActivity::class.java)
            startActivity(intent)
        }

        // --- FUNÇÃO 2: Iniciar Rota (RESTAURADA E ATIVA) ---
        val btnIniciarRota = findViewById<CardView>(R.id.btnIniciarRota)
        btnIniciarRota?.setOnClickListener {
            startActivity(Intent(this, RotasMotoristaActivity::class.java))
        }

        // --- FUNÇÃO 3: Navegação Suporte ---
        findViewById<TextView>(R.id.btnSuporteNav)?.setOnClickListener {
            startActivity(Intent(this, SuporteActivity::class.java))
        }

        // --- FUNÇÃO 4: Carregar avisos em tempo real ---
        carregarAvisosPreview()
    }

    private fun carregarAvisosPreview() {
        db.collection("avisos")
            .orderBy("data", Query.Direction.DESCENDING)
            .limit(5)
            .addSnapshotListener { snapshots, e ->
                if (e != null || snapshots == null) return@addSnapshotListener
                llAvisosPreview.removeAllViews()
                for (doc in snapshots) {
                    val texto = doc.getString("texto") ?: ""
                    val timestamp = doc.getTimestamp("data")
                    adicionarAvisoPreview(texto, timestamp?.toDate()?.time ?: 0L)
                }
            }
    }

    private fun adicionarAvisoPreview(texto: String, timeMillis: Long) {
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 8, 0, 8)

            // --- RESTAURAÇÃO DA FUNÇÃO DE CLIQUE NO AVISO ---
            isClickable = true
            isFocusable = true
            setOnClickListener {
                val intent = Intent(this@InicialMotoristaActivity, AvisosMotoristaActivity::class.java)
                startActivity(intent)
            }
        }

        val header = RelativeLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(-1, -2)
        }

        val tvTitulo = TextView(this).apply {
            text = "Unibus - Aviso"
            setTypeface(null, Typeface.BOLD)
            setTextColor(Color.parseColor("#134B70"))
            textSize = 14f
        }

        val tvData = TextView(this).apply {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            text = if (timeMillis > 0) sdf.format(Date(timeMillis)) else "00/00/0000"
            setTextColor(Color.parseColor("#4D4D4D"))
            textSize = 12f
            layoutParams = RelativeLayout.LayoutParams(-2, -2).apply {
                addRule(RelativeLayout.ALIGN_PARENT_END)
            }
        }

        header.addView(tvTitulo)
        header.addView(tvData)

        val tvCorpo = TextView(this).apply {
            text = texto
            maxLines = 2
            setTextColor(Color.parseColor("#4D4D4D"))
            textSize = 14f
            setPadding(0, 4, 0, 8)
        }

        val divisor = View(this).apply {
            layoutParams = LinearLayout.LayoutParams(-1, 1).apply { setMargins(0, 8, 0, 8) }
            setBackgroundColor(Color.LTGRAY)
        }

        container.addView(header)
        container.addView(tvCorpo)
        container.addView(divisor)
        llAvisosPreview.addView(container)
    }
}