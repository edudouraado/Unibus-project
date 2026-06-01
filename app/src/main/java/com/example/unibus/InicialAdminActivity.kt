package com.example.unibus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class InicialAdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicial_admin)

        // Localiza o CardView de Gerenciar Rotas pelo ID do seu XML
        val btnManageRoutes = findViewById<CardView>(R.id.btnManageRoutes)

        // Localiza o CardView de Gerenciar Usuários (caso queira configurar também)
        val btnManageUsers = findViewById<CardView>(R.id.btnManageUsers)

        // Configura o clique para abrir a tela de Gerenciar Rotas
        btnManageRoutes.setOnClickListener {
            val intent = Intent(this, GerenciarRotasActivity::class.java)
            startActivity(intent)
        }

        // Configura o clique para abrir a tela de Gerenciar Usuários
        btnManageUsers.setOnClickListener {
            // Se você tiver uma activity chamada GerenciarUsuariosActivity (ou similar)
            // val intent = Intent(this, GerenciarUsuariosActivity::class.java)
            // startActivity(intent)
        }
    }
}