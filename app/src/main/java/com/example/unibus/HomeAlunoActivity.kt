package com.example.unibus

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeAlunoActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var llAvisosPreview: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicial_aluno)

        db = FirebaseFirestore.getInstance()
        llAvisosPreview = findViewById(R.id.llAvisosPreviewAluno)

        val btnRotas = findViewById<CardView>(R.id.btnRotasAluno)
        val navRotas = findViewById<TextView>(R.id.navRotasAluno)
        val navSuporte = findViewById<TextView>(R.id.navSuporteAluno)
        val cardAvisos = findViewById<CardView>(R.id.cardAvisosAluno)

        btnRotas.setOnClickListener {
            val intent = Intent(this, RotasActivity::class.java)
            startActivity(intent)
        }

        navRotas.setOnClickListener {
            val intent = Intent(this, RotasActivity::class.java)
            startActivity(intent)
        }

        navSuporte.setOnClickListener {
            val intent = Intent(this, SuporteActivity::class.java)
            startActivity(intent)
        }

        cardAvisos.setOnClickListener {
            val intent = Intent(this, AvisosActivity::class.java)
            startActivity(intent)
        }

        carregarAvisosPreview()
    }

    private fun carregarAvisosPreview() {
        db.collection("avisos")
            .orderBy("data", Query.Direction.DESCENDING)
            .limit(2)
            .addSnapshotListener { snapshots, e ->
                if (e != null || snapshots == null) return@addSnapshotListener

                llAvisosPreview.removeAllViews()
                
                val tvTitulo = TextView(this).apply {
                    text = "Avisos"
                    setTextColor(ContextCompat.getColor(context, R.color.text_gray))
                    textSize = 18f
                    setTypeface(null, Typeface.BOLD)
                    setPadding(0, 0, 0, (12 * resources.displayMetrics.density).toInt())
                }
                llAvisosPreview.addView(tvTitulo)

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
        }

        val header = RelativeLayout(this)

        val tvAssunto = TextView(this).apply {
            text = "Unibus - Comunicado"
            setTypeface(null, Typeface.BOLD)
            setTextColor(ContextCompat.getColor(context, R.color.text_gray))
            textSize = 13f
        }

        val tvData = TextView(this).apply {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            text = if (timeMillis > 0) sdf.format(Date(timeMillis)) else "--/--/----"
            setTextColor(ContextCompat.getColor(context, R.color.text_gray))
            textSize = 13f
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                addRule(RelativeLayout.ALIGN_PARENT_END)
            }
        }

        header.addView(tvAssunto)
        header.addView(tvData)

        val tvCorpo = TextView(this).apply {
            text = texto
            maxLines = 1
            ellipsize = android.text.TextUtils.TruncateAt.END
            setTextColor(ContextCompat.getColor(context, R.color.text_gray))
            textSize = 13f
            setPadding(0, (4 * resources.displayMetrics.density).toInt(), 0, 0)
        }

        val divisor = View(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
            ).apply {
                setMargins(0, (12 * resources.displayMetrics.density).toInt(), 0, (12 * resources.displayMetrics.density).toInt())
            }
            setBackgroundColor(ContextCompat.getColor(context, R.color.divider_gray))
        }

        container.addView(header)
        container.addView(tvCorpo)
        container.addView(divisor)
        llAvisosPreview.addView(container)
    }
}
