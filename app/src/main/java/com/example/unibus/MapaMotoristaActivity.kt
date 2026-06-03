package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

class MapaMotoristaActivity : AppCompatActivity() {

    private lateinit var map: MapView
    private val db = FirebaseFirestore.getInstance()
    private var rotaId: String = ""

    // 1. Configura o Scanner de QR Code para contar passageiros
    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            // Toda vez que ler um QR Code, aumenta a lotação no Firebase
            db.collection("rotas").document(rotaId)
                .update("lotacao_atual", FieldValue.increment(1))
                .addOnSuccessListener {
                    Toast.makeText(this, "Passageiro validado!", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuração obrigatória para o mapa carregar
        Configuration.getInstance().userAgentValue = packageName

        setContentView(R.layout.activity_mapa_motorista)

        // Dentro do onCreate da MapaMotoristaActivity
        findViewById<CardView>(R.id.btnFinalizarRota).setOnClickListener {
            val intent = Intent(this, ConfirmarFinalizacaoActivity::class.java)
            intent.putExtra("ROTA_ID", rotaId)
            startActivity(intent)
            // NÃO COLOQUE finish() AQUI!
        }

        // Recupera o ID da rota vindo da tela de seleção
        rotaId = intent.getStringExtra("ROTA_ID") ?: "rota_parangaba"

        // 2. Inicializar Mapa (Fortaleza / UNIFOR)
        map = findViewById(R.id.mapView)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.controller.setZoom(16.0)
        map.controller.setCenter(GeoPoint(-3.7668, -38.4795))

        // DENTRO DO onCreate da MapaMotoristaActivity
        val btnFinalizar = findViewById<androidx.cardview.widget.CardView>(R.id.btnFinalizarRota)

        btnFinalizar?.setOnClickListener {
            // 1. Criamos a intenção para abrir o popup de confirmação
            val intent = Intent(this, ConfirmarFinalizacaoActivity::class.java)

            // 2. Passamos o ID da rota para ser resetado depois
            intent.putExtra("ROTA_ID", rotaId)

            // 3. Abrimos a tela de confirmação
            startActivity(intent)

            // ATENÇÃO: NÃO COLOQUE finish() AQUI!
            // Se você colocar finish(), o mapa fecha e você volta para a tela de seleção.
        }

        // 4. Botão Quantidade de Vagas (Abre o gráfico circular)
        findViewById<android.view.View>(R.id.btnVerVagas).setOnClickListener {
            val intent = Intent(this, RotaAndamentoActivity::class.java)
            intent.putExtra("ROTA_ID", rotaId)
            startActivity(intent)
        }

        // 5. Botão QR Code (Abre o Scanner)
        findViewById<android.view.View>(R.id.btnAbrirScanner).setOnClickListener {
            val options = ScanOptions()
            options.setPrompt("Aproxime o QR Code do Aluno")
            options.setBeepEnabled(true)
            options.setOrientationLocked(true)
            barcodeLauncher.launch(options)
        }

        // 6. Botão Voltar (Seta)
        findViewById<android.view.View>(R.id.btnBack).setOnClickListener { finish() }

        // 7. Escutar Previsão de Chegada do Firebase em Tempo Real
        db.collection("rotas").document(rotaId).addSnapshotListener { snapshot, _ ->
            if (snapshot != null && snapshot.exists()) {
                val previsao = snapshot.getString("previsao_chegada") ?: "Calculando..."
                findViewById<TextView>(R.id.tvPrevisaoMapa).text = "Previsão de chegada: $previsao"
            }
        }
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }
}