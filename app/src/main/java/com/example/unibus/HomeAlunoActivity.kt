package com.example.unibus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class HomeAlunoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // AQUI: coloque o nome do arquivo XML que você criou para o aluno
        setContentView(R.layout.activity_inicial_aluno)
    }
}