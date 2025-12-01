package com.example.scheduly

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TambahJadwalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_jadwal)

        val btnClose = findViewById<ImageButton>(R.id.btnCloseJadwal)
        btnClose.setOnClickListener {
            finish()
        }

        val btnSave = findViewById<Button>(R.id.btnSaveJadwal)
        btnSave.setOnClickListener {
            finish()
        }
        }
}