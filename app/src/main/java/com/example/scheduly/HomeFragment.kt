package com.example.scheduly

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val tvNama = view.findViewById<TextView>(R.id.tvNama)
        val tvTanggal = view.findViewById<TextView>(R.id.tvTanggal)
        val btnTambahJadwal = view.findViewById<LinearLayout>(R.id.btnTambahJadwal)

        tvNama.text = "Hallo Pandu Putra!!"

        val locale = Locale("id", "ID")
        val sdf = SimpleDateFormat("EEEE, dd MMMM yyyy", locale)
        tvTanggal.text = sdf.format(Date())


        btnTambahJadwal.setOnClickListener {
            startActivity(Intent(requireContext(), TambahJadwalActivity::class.java))
        }

        return view
    }
}
