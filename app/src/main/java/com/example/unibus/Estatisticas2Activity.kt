package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Estatisticas2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estatisticas_2)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val btnOpenCalendar = findViewById<android.widget.LinearLayout>(R.id.btnOpenCalendar)

        btnBack.setOnClickListener {
            val intent = Intent(this, InicialAdminActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        btnOpenCalendar.setOnClickListener {
            val intent = Intent(this, EstatisticasCalendarActivity::class.java)
            startActivity(intent)
        }
    }
}
