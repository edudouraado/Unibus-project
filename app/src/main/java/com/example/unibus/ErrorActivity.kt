package com.example.unibus

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ErrorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error)

        val btnBack: ImageView = findViewById(R.id.btnBack)
        val btnOk: Button = findViewById(R.id.btnOk)

        btnBack.setOnClickListener {
            finish()
        }

        btnOk.setOnClickListener {
            finish()
        }
    }
}
