package com.example.unibus

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.firestore.FirebaseFirestore

class GerenciarUsuariosActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gerenciar_usuarios)

        val llContainer = findViewById<LinearLayout>(R.id.llUserListContainer)
        findViewById<ImageView>(R.id.btnBack)?.setOnClickListener { finish() }

        ouvirUsuarios(llContainer)
    }

    private fun ouvirUsuarios(container: LinearLayout?) {
        db.collection("usuarios").addSnapshotListener { snapshots, e ->
            if (e != null) return@addSnapshotListener
            container?.removeAllViews()
            for (doc in snapshots!!) {
                val nome = doc.getString("nome") ?: "Sem nome"
                val email = doc.getString("email") ?: ""
                val perfil = doc.getString("perfil") ?: "aluno"
                val status = doc.getBoolean("acessoAtivo") ?: true
                adicionarCard(container!!, nome, email, perfil, status, doc.id)
            }
        }
    }

    private fun adicionarCard(container: LinearLayout, nome: String, email: String, perfil: String, status: Boolean, docId: String) {
        val card = CardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(-1, -2).apply { setMargins(0, 0, 0, 40) }
            radius = 20f
            cardElevation = 10f
            setCardBackgroundColor(Color.WHITE)
        }

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 40, 40, 40)
        }

        val tvInfo = TextView(this).apply {
            text = "$nome ($perfil)\n$email"
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            setTextColor(Color.parseColor("#1A618D"))
            setTypeface(null, android.graphics.Typeface.BOLD)
        }

        val tvStatus = TextView(this).apply {
            text = if (status) "STATUS: ATIVO" else "STATUS: INATIVO"
            setTextColor(if (status) Color.parseColor("#4CAF50") else Color.RED)
            setTypeface(null, android.graphics.Typeface.BOLD)
            setPadding(0, 10, 0, 20)
        }

        val btnToggle = Button(this).apply {
            text = if (status) "INATIVAR" else "ATIVAR"
            setBackgroundColor(if (status) Color.RED else Color.parseColor("#4CAF50"))
            setTextColor(Color.WHITE)
            setOnClickListener {
                db.collection("usuarios").document(docId).update("acessoAtivo", !status)
            }
        }

        layout.addView(tvInfo)
        layout.addView(tvStatus)
        layout.addView(btnToggle)
        card.addView(layout)
        container.addView(card)
    }
}