package com.example.scheduly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class TugasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tugas, container, false)

        val containerList = view.findViewById<LinearLayout>(R.id.containerListTugas)
        val btnTambah = view.findViewById<Button>(R.id.btnTambahTugas)

        tambahItemTugas(
            containerList,
            judul = "Tugas Algoritma - Program Game",
            matkul = "Algoritma",
            desk = "Mengerjakan coding game",
            deadline = "12 November 2025"
        )

        tambahItemTugas(
            containerList,
            judul = "Tugas Pancasila - Demokrasi",
            matkul = "Pancasila",
            desk = "Membuat makalah",
            deadline = "15 November 2025"
        )

        btnTambah.setOnClickListener {
        }

        return view
    }

    private fun tambahItemTugas(
        container: LinearLayout,
        judul: String,
        matkul: String,
        desk: String,
        deadline: String
    ) {
        val item = LayoutInflater.from(requireContext())
            .inflate(R.layout.item_tugas, container, false)

        item.findViewById<TextView>(R.id.tvJudulTugas).text = judul
        item.findViewById<TextView>(R.id.tvMataKuliah).text = matkul
        item.findViewById<TextView>(R.id.tvDeskripsiTugas).text = desk
        item.findViewById<TextView>(R.id.tvDeadline).text = deadline

        container.addView(item)
    }
}