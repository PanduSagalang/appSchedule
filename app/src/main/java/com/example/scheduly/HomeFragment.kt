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

    private lateinit var llHariIni: LinearLayout
    private lateinit var llNext: LinearLayout

    private val localeID = Locale("id", "ID")
    private val sdfFull = SimpleDateFormat("EEEE, dd MMMM yyyy", localeID)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val tvNama = view.findViewById<TextView>(R.id.tvNama)
        val tvTanggal = view.findViewById<TextView>(R.id.tvTanggal)
        val btnTambahJadwal = view.findViewById<LinearLayout>(R.id.btnTambahJadwal)

        llHariIni = view.findViewById(R.id.llJadwalHariIniContainer)
        llNext = view.findViewById(R.id.llJadwalNextContainer)

        tvNama.text = "Hallo Pandu Putra!!"

        val locale = Locale("id", "ID")
        val sdf = SimpleDateFormat("EEEE, dd MMMM yyyy", locale)
        tvTanggal.text = sdf.format(Date())


        btnTambahJadwal.setOnClickListener {
            startActivity(Intent(requireContext(), TambahJadwalActivity::class.java))
        }
        loadJadwal()

        return view
    }

    override fun onResume() {
        super.onResume()
        loadJadwal()
    }

    private fun loadJadwal() {
        llHariIni.removeAllViews()
        llNext.removeAllViews()

        val listObj = Storage.getJadwalList(requireContext())
        val listRaw = Storage.getJadwal(requireContext())

        val rawById = mutableMapOf<Long, String>()
        for (raw in listRaw) {
            val sp = raw.split("#")
            if (sp.isNotEmpty()) {
                val id = sp[0].toLongOrNull()
                if (id != null) rawById[id] = raw
            }
        }

        val todayStr = sdfFull.format(Date())
        val todayDate = parseDate(todayStr)

        val hariIniList = mutableListOf<Jadwal>()
        val nextList = mutableListOf<Jadwal>()

        for (j in listObj) {
            val d = parseDate(j.hari)
            if (d == null || todayDate == null) continue

            if (isSameDay(d, todayDate)) {
                hariIniList.add(j)
            } else if (d.after(todayDate)) {
                nextList.add(j)
            }
        }

        hariIniList.sortWith(compareBy({ parseDate(it.hari) }, { it.jamMulai }))
        nextList.sortWith(compareBy({ parseDate(it.hari) }, { it.jamMulai }))

        for (jadwal in hariIniList) {
            val itemView = layoutInflater.inflate(R.layout.item_jadwal, llHariIni, false)

            val tvNamaKelas = itemView.findViewById<TextView>(R.id.tvNamaKelas)
            val tvDetail = itemView.findViewById<TextView>(R.id.tvDetail)
            val tvCatatan = itemView.findViewById<TextView>(R.id.tvCatatan)
            val btnHapus = itemView.findViewById<TextView>(R.id.btnHapusJadwal)

            tvNamaKelas.text = jadwal.namaKelas
            tvDetail.text = "Ruang ${jadwal.ruang}, ${jadwal.jamMulai}–${jadwal.jamSelesai}"
            tvCatatan.text = "Dosen : ${jadwal.catatan}"

            btnHapus.setOnClickListener {
                Storage.deleteJadwalObj(requireContext(), jadwal.id)
                loadJadwal()
            }

            itemView.setOnClickListener {
                val raw = rawById[jadwal.id]
                val intent = Intent(requireContext(), TambahJadwalActivity::class.java)
                intent.putExtra("editData", raw) // TambahJadwalActivity kamu sudah support ini
                startActivity(intent)
            }

            llHariIni.addView(itemView)
        }

        for (jadwal in nextList) {
            val itemView = layoutInflater.inflate(R.layout.item_jadwal, llNext, false)

            val tvNamaKelas = itemView.findViewById<TextView>(R.id.tvNamaKelas)
            val tvDetail = itemView.findViewById<TextView>(R.id.tvDetail)
            val tvCatatan = itemView.findViewById<TextView>(R.id.tvCatatan)
            val btnHapus = itemView.findViewById<TextView>(R.id.btnHapusJadwal)

            tvNamaKelas.text = jadwal.namaKelas
            tvDetail.text = "(${jadwal.hari}) • Ruang ${jadwal.ruang}, ${jadwal.jamMulai}–${jadwal.jamSelesai}"
            tvCatatan.text = "Dosen : ${jadwal.catatan}"

            btnHapus.setOnClickListener {
                Storage.deleteJadwalObj(requireContext(), jadwal.id)
                loadJadwal()
            }

            itemView.setOnClickListener {
                val raw = rawById[jadwal.id]
                val intent = Intent(requireContext(), TambahJadwalActivity::class.java)
                intent.putExtra("editData", raw)
                startActivity(intent)
            }

            llNext.addView(itemView)
        }
    }

    private fun parseDate(text: String): Date? {
        return try { sdfFull.parse(text) } catch (e: Exception) { null }
    }

    private fun isSameDay(d1: Date, d2: Date): Boolean {
        val c1 = Calendar.getInstance().apply { time = d1 }
        val c2 = Calendar.getInstance().apply { time = d2 }
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)
    }
}