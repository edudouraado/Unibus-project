package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EstatisticasCalendarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estatisticas_calendar)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val btnDia19 = findViewById<android.view.View>(R.id.btnDia19)
        val btnDia30 = findViewById<android.view.View>(R.id.btnDia30)
        val btnClose = findViewById<ImageView>(R.id.btnClose)

        btnBack.setOnClickListener {
            finish()
        }

        btnClose.setOnClickListener {
            finish()
        }

        btnDia19.setOnClickListener {
            val intent = Intent(this, Estatisticas2Activity::class.java)
            startActivity(intent)
        }

        btnDia30.setOnClickListener {
            val intent = Intent(this, EstatisticasActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
    }
}
