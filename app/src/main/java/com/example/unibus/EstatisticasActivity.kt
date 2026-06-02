package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class EstatisticasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estatisticas)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val btnOpenCalendar = findViewById<LinearLayout>(R.id.btnOpenCalendar)

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
