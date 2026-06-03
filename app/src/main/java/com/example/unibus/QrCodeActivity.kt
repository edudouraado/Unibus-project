package com.example.unibus

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class QrCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        // Referenciando os componentes do XML
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val ivQrCode = findViewById<ImageView>(R.id.ivQrCode)

        // Botão para voltar para a tela anterior
        btnBack?.setOnClickListener {
            finish()
        }

        // Pegamos o UID do aluno logado para gerar o QR Code
        val user = FirebaseAuth.getInstance().currentUser
        val uidAluno = user?.uid

        if (uidAluno != null) {
            gerarQrCodeReal(uidAluno, ivQrCode)
        } else {
            Toast.makeText(this, "Erro ao recuperar dados do aluno", Toast.LENGTH_SHORT).show()
        }
    }

    private fun gerarQrCodeReal(texto: String, imageView: ImageView) {
        try {
            val barcodeEncoder = BarcodeEncoder()
            // Gera a imagem do QR Code baseada no UID do aluno
            val bitmap: Bitmap = barcodeEncoder.encodeBitmap(
                texto,
                BarcodeFormat.QR_CODE,
                500, // Largura
                500  // Altura
            )
            imageView.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Erro ao gerar QR Code", Toast.LENGTH_SHORT).show()
        }
    }
}