package com.example.unibus

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.Locale

class RelatoriosActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relatorios)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val containerLogs = findViewById<LinearLayout>(R.id.containerLogs)

        // CardViews (Fundo)
        val tabInvalid = findViewById<CardView>(R.id.tabInvalid)
        val tabPeaks = findViewById<CardView>(R.id.tabPeaks)
        val tabAccessibility = findViewById<CardView>(R.id.tabAccessibility)

        // TextViews (Letras)
        val tvTabInvalid = findViewById<TextView>(R.id.tvTabInvalid)
        val tvTabPeaks = findViewById<TextView>(R.id.tvTabPeaks)
        val tvTabAccessibility = findViewById<TextView>(R.id.tvTabAccessibility)

        btnBack?.setOnClickListener { finish() }

        // 1. Filtro padrão ao abrir (Tentativas Inválidas)
        destacarAba(tabInvalid, tvTabInvalid, tabPeaks, tvTabPeaks, tabAccessibility, tvTabAccessibility)
        carregarDadosRelatorio("tentativas_invalidas", containerLogs, "FALHA")

        tabInvalid?.setOnClickListener {
            destacarAba(tabInvalid, tvTabInvalid, tabPeaks, tvTabPeaks, tabAccessibility, tvTabAccessibility)
            carregarDadosRelatorio("tentativas_invalidas", containerLogs, "FALHA")
        }

        tabPeaks?.setOnClickListener {
            destacarAba(tabPeaks, tvTabPeaks, tabInvalid, tvTabInvalid, tabAccessibility, tvTabAccessibility)
            carregarDadosRelatorio("acessos", containerLogs, "ACESSO")
        }

        tabAccessibility?.setOnClickListener {
            destacarAba(tabAccessibility, tvTabAccessibility, tabInvalid, tvTabInvalid, tabPeaks, tvTabPeaks)
            carregarDadosRelatorio("qr_codes_lidos", containerLogs, "QR CODE")
        }
    }

    private fun carregarDadosRelatorio(colecao: String, container: LinearLayout?, prefixo: String) {
        if (container == null) return

        db.collection(colecao)
            .orderBy("data", Query.Direction.DESCENDING)
            .limit(50)
            .get()
            .addOnSuccessListener { documents ->
                container.removeAllViews()

                if (documents.isEmpty) {
                    exibirMensagemVazia(container)
                    return@addOnSuccessListener
                }

                val contagemPorHora = mutableMapOf<String, Int>()
                val sdfHora = SimpleDateFormat("dd/MM - HH'h'", Locale.getDefault())
                val sdfCompleto = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

                for (doc in documents) {
                    val timestamp = doc.getTimestamp("data")
                    if (timestamp != null) {
                        val horaChave = sdfHora.format(timestamp.toDate())
                        contagemPorHora[horaChave] = contagemPorHora.getOrDefault(horaChave, 0) + 1

                        if (colecao != "acessos" && colecao != "qr_codes_lidos") {
                            val email = doc.getString("email") ?: "N/A"
                            val motivo = doc.getString("motivo") ?: ""
                            criarItemLista(container, sdfCompleto.format(timestamp.toDate()), "$prefixo: $email\n$motivo")
                        }
                    }
                }

                if (colecao == "acessos" || colecao == "qr_codes_lidos") {
                    for ((hora, qtd) in contagemPorHora) {
                        criarItemLista(container, hora, "$prefixo: $qtd registros nesta hora")
                    }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun criarItemLista(container: LinearLayout, data: String, info: String) {
        val itemView = layoutInflater.inflate(R.layout.item_relatorio_falha, container, false)
        val tvData = itemView.findViewById<TextView>(R.id.tvDataFalha)
        val tvDesc = itemView.findViewById<TextView>(R.id.tvDescricaoFalha)

        tvData.text = data
        tvDesc.text = info
        container.addView(itemView)
    }

    private fun exibirMensagemVazia(container: LinearLayout) {
        val tv = TextView(this)
        tv.text = "Nenhum registro encontrado para este filtro."
        tv.setPadding(0, 100, 0, 0)
        tv.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        container.addView(tv)
    }

    // FUNÇÃO CORRIGIDA PARA AS CORES
    private fun destacarAba(ativa: CardView?, tvAtivo: TextView?,
                            inativa1: CardView?, tvInativo1: TextView?,
                            inativa2: CardView?, tvInativo2: TextView?) {

        val corAzulUnibus = Color.parseColor("#134B70")
        val corBranca = Color.WHITE

        // Aba Ativa: Fundo Azul e Letra Branca
        ativa?.setCardBackgroundColor(corAzulUnibus)
        tvAtivo?.setTextColor(corBranca)

        // Aba Inativa 1: Fundo Branco e Letra Azul (Para ficar visível)
        inativa1?.setCardBackgroundColor(corBranca)
        tvInativo1?.setTextColor(corAzulUnibus)

        // Aba Inativa 2: Fundo Branco e Letra Azul (Para ficar visível)
        inativa2?.setCardBackgroundColor(corBranca)
        tvInativo2?.setTextColor(corAzulUnibus)
    }
}