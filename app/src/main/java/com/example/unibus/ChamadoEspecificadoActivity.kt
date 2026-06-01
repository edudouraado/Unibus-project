package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.firestore.FirebaseFirestore

class ChamadoEspecificadoActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private var docId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chamado_especificado)

        db = FirebaseFirestore.getInstance()

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val tvSubject = findViewById<TextView>(R.id.tvSubject)
        val tvDate = findViewById<TextView>(R.id.tvDate)
        val tvBody = findViewById<TextView>(R.id.tvBody)
        val btnResponder = findViewById<CardView>(R.id.btnResponder)

        val texto = intent.getStringExtra("texto") ?: ""
        val data = intent.getStringExtra("data") ?: ""
        docId = intent.getStringExtra("docId")

        tvSubject.text = "Unibus - Comunicado"
        tvDate.text = data
        tvBody.text = texto

        btnBack.setOnClickListener {
            finish()
        }

        btnResponder.setOnClickListener {
            showResponderDialog()
        }
    }

    private fun showResponderDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_responder_chamado, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val btnClose = dialogView.findViewById<ImageView>(R.id.btnClose)
        val etMessage = dialogView.findViewById<EditText>(R.id.etMessage)
        val btnSend = dialogView.findViewById<CardView>(R.id.btnSend)

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        btnSend.setOnClickListener {
            val message = etMessage.text.toString()
            if (message.isNotEmpty()) {
                if (docId != null) {
                    db.collection("avisos").document(docId!!)
                        .update("respondido", true)
                        .addOnSuccessListener {
                            dialog.dismiss()
                            val intent = Intent(this, SucessoAvisoActivity::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Erro ao atualizar chamado.", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    // Caso não tenha docId (ex: dados antigos), apenas navega
                    dialog.dismiss()
                    val intent = Intent(this, SucessoAvisoActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        dialog.show()
    }
}
