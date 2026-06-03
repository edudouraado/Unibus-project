package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapaActivity : AppCompatActivity() {

    private lateinit var map: MapView
    private val db = FirebaseFirestore.getInstance()
    private val listaDeMarcadores = mutableListOf<Marker>() // Lista para controle da pesquisa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().userAgentValue = packageName
        setContentView(R.layout.activity_mapa)

        // 1. Pega a rota que o aluno selecionou na tela anterior
        val nomeRotaSelecionada = intent.getStringExtra("NOME_ROTA") ?: "Parangaba > Campus"
        findViewById<TextView>(R.id.tvTituloMapa).text = nomeRotaSelecionada

        // 2. Inicializa o Mapa
        map = findViewById(R.id.mapView)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.controller.setZoom(15.0)
        map.controller.setCenter(GeoPoint(-3.7668, -38.4795)) // Foco inicial UNIFOR

        // 3. Botão Voltar
        findViewById<ImageView>(R.id.btnBack)?.setOnClickListener { finish() }

        // 4. Barra de Pesquisa
        val etSearch = findViewById<EditText>(R.id.etSearchPonto)
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarPontosNoMapa(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // 5. Botões Nav Bar
        findViewById<TextView>(R.id.navQrCode)?.setOnClickListener {
            startActivity(Intent(this, QrCodeActivity::class.java))
        }
        findViewById<TextView>(R.id.navInicio)?.setOnClickListener {
            val intent = Intent(this, HomeAlunoActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        // 6. Carrega os pontos FILTRADOS por rota
        carregarPontosDaRota(nomeRotaSelecionada)
    }

    private fun carregarPontosDaRota(nomeDaRota: String) {
        // Converte o nome legível para o ID do Firebase (Ex: "Parangaba > Campus" vira "rota_parangaba")
        val rotaId = when {
            nomeDaRota.contains("Parangaba") -> "rota_parangaba"
            nomeDaRota.contains("Papicu") -> "rota_papicu"
            else -> "rota_messejana"
        }

        db.collection("pontos")
            .whereEqualTo("rota", rotaId) // FILTRO REAL DO FIREBASE
            .get()
            .addOnSuccessListener { documents ->
                map.overlays.clear()
                listaDeMarcadores.clear()

                for (doc in documents) {
                    val nome = doc.getString("nome") ?: "Parada"
                    val firePos = doc.getGeoPoint("posicao")

                    if (firePos != null) {
                        val marker = Marker(map)
                        marker.position = GeoPoint(firePos.latitude, firePos.longitude)
                        marker.title = nome
                        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                        map.overlays.add(marker)
                        listaDeMarcadores.add(marker) // Salva na lista local para pesquisa
                    }
                }
                map.invalidate()
            }
    }

    private fun filtrarPontosNoMapa(texto: String) {
        for (marker in listaDeMarcadores) {
            // Se o nome do ponto contém o que foi digitado, mostra. Senão, esconde.
            if (marker.title.lowercase().contains(texto.lowercase())) {
                if (!map.overlays.contains(marker)) map.overlays.add(marker)
            } else {
                map.overlays.remove(marker)
            }
        }
        map.invalidate()
    }

    override fun onResume() { super.onResume(); map.onResume() }
    override fun onPause() { super.onPause(); map.onPause() }
}