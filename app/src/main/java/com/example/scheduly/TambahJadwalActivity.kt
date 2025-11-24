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

        // Tombol X (close) - sesuaikan dengan ID di XML: btnCloseJadwal
        val btnClose = findViewById<ImageButton>(R.id.btnCloseJadwal)
        btnClose.setOnClickListener {
            finish()
        }

        // Tombol Save - ID di XML: btnSaveJadwal
        val btnSave = findViewById<Button>(R.id.btnSaveJadwal)
        btnSave.setOnClickListener {
            // TODO: simpan data jadwal (ke DB / list) kalau sudah ada logic-nya
            finish()
        }
        }
}