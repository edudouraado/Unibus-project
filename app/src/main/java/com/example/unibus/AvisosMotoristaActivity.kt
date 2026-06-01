package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AvisosMotoristaActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var llAvisosLista: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avisos_motorista)

        db = FirebaseFirestore.getInstance()
        llAvisosLista = findViewById(R.id.llAvisosLista)

        val btnAdd = findViewById<Button>(R.id.btnAddMessage)
        val btnBack = findViewById<ImageView>(R.id.btnBackAvisos)

        btnAdd.setOnClickListener {
            exibirPopupAdicionar()
        }

        btnBack.setOnClickListener {
            finish()
        }

        carregarAvisosDoFirebase()
    }

    private fun exibirPopupAdicionar() {
        val factory = LayoutInflater.from(this)
        val popupView = factory.inflate(R.layout.dialog_add_aviso, null)
        val etAvisoInput = popupView.findViewById<EditText>(R.id.etAvisoInput)
        val btnEnviar = popupView.findViewById<Button>(R.id.btnEnviarAviso)

        val dialog = AlertDialog.Builder(this)
            .setView(popupView)
            .create()

        btnEnviar.setOnClickListener {
            val textoAviso = etAvisoInput.text.toString()
            if (textoAviso.isNotEmpty()) {
                salvarAvisoNoFirebase(textoAviso, dialog)
            } else {
                Toast.makeText(this, "Por favor, digite um aviso.", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun salvarAvisoNoFirebase(texto: String, dialog: AlertDialog) {
        val aviso = hashMapOf(
            "texto" to texto,
            "data" to Timestamp.now(), // Salva como Timestamp oficial
            "respondido" to false
        )

        db.collection("avisos")
            .add(aviso)
            .addOnSuccessListener {
                dialog.dismiss()
                val intent = Intent(this, ConfirmacaoAvisoActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao salvar aviso: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun carregarAvisosDoFirebase() {
        db.collection("avisos")
            .orderBy("data", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, e ->
                if (e != null) return@addSnapshotListener

                llAvisosLista.removeAllViews()

                for (doc in snapshots!!) {
                    val texto = doc.getString("texto") ?: ""
                    // Lê como Timestamp (suporta o formato da sua imagem)
                    val timestamp = doc.getTimestamp("data")
                    adicionarAvisoNaTela(texto, timestamp?.toDate()?.time ?: 0L)
                }
            }
    }

    private fun adicionarAvisoNaTela(texto: String, timeMillis: Long) {
        val containerAviso = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 0, 0, 32)
        }

        val headerLayout = android.widget.RelativeLayout(this)
        
        val tvAssunto = TextView(this).apply {
            text = "Comunicado"
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(getColor(R.color.text_gray))
        }

        val tvData = TextView(this).apply {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            text = if (timeMillis > 0) sdf.format(Date(timeMillis)) else "--/--/----"
            setTextColor(getColor(R.color.text_gray))
            val params = android.widget.RelativeLayout.LayoutParams(
                android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT,
                android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                addRule(android.widget.RelativeLayout.ALIGN_PARENT_END)
            }
            layoutParams = params
        }

        headerLayout.addView(tvAssunto)
        headerLayout.addView(tvData)

        val tvCorpo = TextView(this).apply {
            text = texto
            setTextColor(getColor(R.color.text_gray))
            setPadding(0, 8, 0, 0)
        }

        val divisor = View(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (resources.displayMetrics.density * 1).toInt()
            ).apply {
                topMargin = (resources.displayMetrics.density * 12).toInt()
            }
            setBackgroundColor(getColor(R.color.divider_gray))
        }

        containerAviso.addView(headerLayout)
        containerAviso.addView(tvCorpo)
        containerAviso.addView(divisor)

        llAvisosLista.addView(containerAviso)
    }
}
