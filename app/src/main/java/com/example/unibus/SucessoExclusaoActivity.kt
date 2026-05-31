package com.example.unibus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SucessoExclusaoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ajustado para um layout existente para corrigir o erro de build
        setContentView(R.layout.activity_sucesso_aviso)
    }
}
