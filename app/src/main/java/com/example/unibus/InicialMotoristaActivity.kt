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

class InicialMotoristaActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var llAvisosPreview: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicial_motorista)

        db = FirebaseFirestore.getInstance()
        llAvisosPreview = findViewById(R.id.llAvisosPreview)

        val cvNotices = findViewById<CardView>(R.id.cvNotices)
        cvNotices.setOnClickListener {
            val intent = Intent(this, AvisosMotoristaActivity::class.java)
            startActivity(intent)
        }

        carregarAvisosPreview()
    }

    private fun carregarAvisosPreview() {
        // Busca os últimos 5 avisos para mostrar na home
        db.collection("avisos")
            .orderBy("data", Query.Direction.DESCENDING)
            .limit(5)
            .addSnapshotListener { snapshots, e ->
                if (e != null || snapshots == null) return@addSnapshotListener

                llAvisosPreview.removeAllViews()

                for (doc in snapshots) {
                    val texto = doc.getString("texto") ?: ""
                    // Ajuste para ler o Timestamp do Firebase
                    val timestamp = doc.getTimestamp("data")
                    adicionarAvisoPreview(texto, timestamp?.toDate()?.time ?: 0L)
                }
            }
    }

    private fun adicionarAvisoPreview(texto: String, timeMillis: Long) {
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val header = RelativeLayout(this).apply {
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val tvAssunto = TextView(this).apply {
            text = "Comunicado"
            setTypeface(null, Typeface.BOLD)
            setTextColor(ContextCompat.getColor(context, R.color.text_gray))
        }

        val tvData = TextView(this).apply {
            val sdf = SimpleDateFormat("dd/MM", Locale.getDefault())
            text = if (timeMillis > 0) sdf.format(Date(timeMillis)) else "--/--"
            setTextColor(ContextCompat.getColor(context, R.color.text_gray))
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
            setPadding(0, 4, 0, 0)
        }

        val divisor = View(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
            ).apply {
                setMargins(0, 12, 0, 12)
            }
            setBackgroundColor(ContextCompat.getColor(context, R.color.divider_gray))
        }

        container.addView(header)
        container.addView(tvCorpo)
        container.addView(divisor)
        llAvisosPreview.addView(container)
    }
}
