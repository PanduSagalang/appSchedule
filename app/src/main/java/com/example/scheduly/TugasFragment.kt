package com.example.scheduly

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TugasFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_tugas)

        val container = findViewById<LinearLayout>(R.id.containerListTugas)
        val btnTambah = findViewById<Button>(R.id.btnTambahTugas)

        tambahItemTugas(
            container,
            judul = "Tugas Algoritma - Program Game",
            matkul = "Algoritma",
            desk = "Mengerjakan coding game",
            deadline = "12 November 2025"
        )

        tambahItemTugas(
            container,
            judul = "Tugas Pancasila - Demokrasi",
            matkul = "Pancasila",
            desk = "Membuat makalah",
            deadline = "15 November 2025"
        )

        btnTambah.setOnClickListener {
        }
    }

    private fun tambahItemTugas(
        container: LinearLayout,
        judul: String,
        matkul: String,
        desk: String,
        deadline: String
    ) {
        val item = LayoutInflater.from(this)
            .inflate(R.layout.item_tugas, container, false)

        item.findViewById<TextView>(R.id.tvJudulTugas).text = judul
        item.findViewById<TextView>(R.id.tvMataKuliah).text = matkul
        item.findViewById<TextView>(R.id.tvDeskripsiTugas).text = desk
        item.findViewById<TextView>(R.id.tvDeadline).text = deadline

        container.addView(item)
    }
}
