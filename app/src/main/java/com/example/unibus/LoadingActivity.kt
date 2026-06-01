package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoadingActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        auth = Firebase.auth

        // Força o logout toda vez que o app é aberto,
        // garantindo que o usuário tenha que logar novamente.
        auth.signOut()

        Handler(Looper.getMainLooper()).postDelayed({
            // Como deslogamos acima, ele sempre irá para a LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 3000)
    }
}
