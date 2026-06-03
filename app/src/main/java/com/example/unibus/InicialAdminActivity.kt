package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.firestore.FirebaseFirestore

class InicialAdminActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicial_admin)

        // 1. Mapeando os campos de texto (Contadores)
        val tvActiveStudentsValue = findViewById<TextView>(R.id.tvActiveStudentsValue)
        val tvTripsCountValue = findViewById<TextView>(R.id.tvTripsCountValue)
        val tvDriversCountValue = findViewById<TextView>(R.id.tvDriversCountValue)
        val tvTicketsCount = findViewById<TextView>(R.id.tvTicketsCount)

        // 2. Mapeando os Cards Superiores (Cinzas)
        val cvTicketsCount = findViewById<CardView>(R.id.cvTicketsCount)

        // 3. Mapeando os Botões Inferiores (Ações)
        val btnGeneralStats = findViewById<CardView>(R.id.btnGeneralStats)
        val btnManageRoutes = findViewById<CardView>(R.id.btnManageRoutes)
        val btnManageUsers = findViewById<CardView>(R.id.btnManageUsers)
        val btnReports = findViewById<CardView>(R.id.btnReports)

        // --- FLUXOS DE NAVEGAÇÃO SEPARADOS ---

        // CLIQUE NO CHAMADO (Card Cinza): Abre a tela de visualizar e responder
        cvTicketsCount?.setOnClickListener {
            val intent = Intent(this, ChamadosActivity::class.java)
            startActivity(intent)
        }

        // BOTÃO RELATÓRIOS (Lista inferior): Abre a tela de relatórios
        btnReports?.setOnClickListener {
            val intent = Intent(this, RelatoriosActivity::class.java)
            startActivity(intent)
        }

        // ESTATÍSTICAS GERAIS: Abre Estatisticas2
        btnGeneralStats?.setOnClickListener {
            startActivity(Intent(this, Estatisticas2Activity::class.java))
        }

        // GERENCIAR ROTAS: Abre GerenciarRotas
        btnManageRoutes?.setOnClickListener {
            startActivity(Intent(this, GerenciarRotasActivity::class.java))
        }

        // GERENCIAR USUÁRIOS: Abre GerenciarUsuarios
        btnManageUsers?.setOnClickListener {
            startActivity(Intent(this, GerenciarUsuariosActivity::class.java))
        }

        // --- MANUTENÇÃO DOS CONTADORES EM TEMPO REAL ---

        ouvirContagemUsuariosAtivos("aluno", tvActiveStudentsValue)
        ouvirContagemUsuariosAtivos("motorista", tvDriversCountValue)
        ouvirContagemFirebase("rotas", "ativa", true, tvTripsCountValue)
        ouvirContagemFirebase("avisos", "respondido", false, tvTicketsCount)
    }

    private fun ouvirContagemUsuariosAtivos(perfil: String, textView: TextView?) {
        db.collection("usuarios")
            .whereEqualTo("perfil", perfil)
            .whereEqualTo("acessoAtivo", true)
            .addSnapshotListener { snapshots, error ->
                if (error == null && snapshots != null) {
                    textView?.text = snapshots.size().toString()
                }
            }
    }

    private fun ouvirContagemFirebase(colecao: String, campo: String, valor: Any, textView: TextView?) {
        db.collection(colecao)
            .whereEqualTo(campo, valor)
            .addSnapshotListener { snapshots, error ->
                if (error == null && snapshots != null) {
                    textView?.text = snapshots.size().toString()
                }
            }
    }
}