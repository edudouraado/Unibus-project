package com.example.unibus

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AvisosActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var llAvisosLista: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avisos)

        db = FirebaseFirestore.getInstance()
        llAvisosLista = findViewById(R.id.llAvisosListaAluno)

        val btnBack = findViewById<ImageView>(R.id.btnBackAvisos)
        btnBack.setOnClickListener {
            finish()
        }

        // Configuração de navegação básica para os itens do bottom nav (se necessário)
        // val navHome = ...

        carregarAvisosDoFirebase()
    }

    private fun carregarAvisosDoFirebase() {
        db.collection("avisos")
            .orderBy("data", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null || snapshots == null) return@addSnapshotListener

                llAvisosLista.removeAllViews()

                for (doc in snapshots) {
                    val texto = doc.getString("texto") ?: ""
                    val timestamp = doc.getTimestamp("data")
                    adicionarAvisoNaTela(texto, timestamp?.toDate()?.time ?: 0L)
                }
            }
    }

    private fun adicionarAvisoNaTela(texto: String, timeMillis: Long) {
        val containerAviso = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 0, 0, (16 * resources.displayMetrics.density).toInt())
        }

        val headerLayout = RelativeLayout(this)

        val tvAssunto = TextView(this).apply {
            text = "Comunicado"
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(ContextCompat.getColor(context, R.color.text_gray))
        }

        val tvData = TextView(this).apply {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            text = if (timeMillis > 0) sdf.format(Date(timeMillis)) else "--/--/----"
            setTextColor(ContextCompat.getColor(context, R.color.text_gray))
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                addRule(RelativeLayout.ALIGN_PARENT_END)
            }
        }

        headerLayout.addView(tvAssunto)
        headerLayout.addView(tvData)

        val tvCorpo = TextView(this).apply {
            text = texto
            setTextColor(ContextCompat.getColor(context, R.color.text_gray))
            setPadding(0, (8 * resources.displayMetrics.density).toInt(), 0, 0)
        }

        val divisor = View(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (1 * resources.displayMetrics.density).toInt()
            ).apply {
                topMargin = (16 * resources.displayMetrics.density).toInt()
            }
            setBackgroundColor(ContextCompat.getColor(context, R.color.divider_gray))
        }

        containerAviso.addView(headerLayout)
        containerAviso.addView(tvCorpo)
        containerAviso.addView(divisor)

        llAvisosLista.addView(containerAviso)
    }
}
