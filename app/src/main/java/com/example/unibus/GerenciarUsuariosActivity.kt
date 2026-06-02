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

        val container = findViewById<LinearLayout>(R.id.llUserListContainer)
        findViewById<ImageView>(R.id.btnBack)?.setOnClickListener { finish() }

        carregarUsuarios(container)
    }

    private fun carregarUsuarios(container: LinearLayout?) {
        db.collection("usuarios").get().addOnSuccessListener { documents ->
            container?.removeAllViews()
            for (doc in documents) {
                val nome = doc.getString("nome") ?: "Sem nome"
                val email = doc.getString("email") ?: ""
                val perfil = doc.getString("perfil") ?: ""
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
            text = "$nome\n$email ($perfil)"
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            setTextColor(Color.DKGRAY)
            setTypeface(null, android.graphics.Typeface.BOLD)
        }

        // TEXTO DE STATUS (ATIVO/INATIVO)
        val tvStatus = TextView(this).apply {
            text = if (status) "STATUS: ATIVO" else "STATUS: INATIVO"
            setTextColor(if (status) Color.parseColor("#4CAF50") else Color.RED)
            setTypeface(null, android.graphics.Typeface.BOLD)
            setPadding(0, 10, 0, 20)
        }

        // BOTÃO DE ALTERNAR STATUS
        val btnToggle = Button(this).apply {
            text = if (status) "INATIVAR ACESSO" else "ATIVAR ACESSO"
            setBackgroundColor(if (status) Color.RED else Color.parseColor("#4CAF50"))
            setTextColor(Color.WHITE)

            setOnClickListener {
                // Inverte o valor do Firebase
                db.collection("usuarios").document(docId).update("acessoAtivo", !status)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Status atualizado!", Toast.LENGTH_SHORT).show()
                        carregarUsuarios(container) // Recarrega a lista
                    }
            }
        }

        layout.addView(tvInfo)
        layout.addView(tvStatus)
        layout.addView(btnToggle)
        card.addView(layout)
        container.addView(card)
    }
}