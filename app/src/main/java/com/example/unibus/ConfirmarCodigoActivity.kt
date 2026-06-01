package com.example.unibus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ConfirmarCodigoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_codigo)

        val editCodigo = findViewById<EditText>(R.id.editCodigo)
        val btnConfirmar = findViewById<Button>(R.id.btnConfirmar)
        val txtSubtitulo = findViewById<TextView>(R.id.txtSubtitulo)

        // Recupera o e-mail vindo da tela de RecuperarSenha para exibir na tela
        val emailRecebido = intent.getStringExtra("email_digitado")
        if (emailRecebido != null) {
            txtSubtitulo.text = "Enviamos um código de recuperação para:\n$emailRecebido"
        }

        btnConfirmar.setOnClickListener {
            val codigoDigitado = editCodigo.text.toString().trim()

            // Lógica de simulação: aceita o código 123456
            if (codigoDigitado == "123456") {
                Toast.makeText(this, "Código validado!", Toast.LENGTH_SHORT).show()

                // Vai para a tela de Alterar Senha que você já tem criada
                val intent = Intent(this, AlterarSenhaActivity::class.java)
                startActivity(intent)
            } else {
                // Dica amigável para o seu teste
                Toast.makeText(this, "Código inválido! Use 123456 para testar.", Toast.LENGTH_LONG).show()
            }
        }
    }
}