package com.example.scheduly

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var rvHariIni: RecyclerView
    private lateinit var rvNext: RecyclerView

    private lateinit var adapterHariIni: JadwalAdapter
    private lateinit var adapterNext: JadwalAdapter

    private val localeID = Locale("id", "ID")
    private val sdfFull = SimpleDateFormat("EEEE, dd MMMM yyyy", localeID)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val tvNama = view.findViewById<TextView>(R.id.tvNama)
        val tvTanggal = view.findViewById<TextView>(R.id.tvTanggal)
        val btnTambahJadwal = view.findViewById<LinearLayout>(R.id.btnTambahJadwal)

        rvHariIni = view.findViewById(R.id.rvHariIni)
        rvNext = view.findViewById(R.id.rvNext)

        tvNama.text = "Hallo Pandu Putra!!"
        tvTanggal.text = sdfFull.format(Date())

        rvHariIni.layoutManager = LinearLayoutManager(requireContext())
        rvNext.layoutManager = LinearLayoutManager(requireContext())

        adapterHariIni = JadwalAdapter(
            mutableListOf(),
            emptyMap(),
            onDelete = { id ->
                Storage.deleteJadwalObj(requireContext(), id)
                loadJadwal()
            },
            onEdit = { raw ->
                startActivity(
                    Intent(requireContext(), TambahJadwalActivity::class.java)
                        .putExtra("editData", raw)
                )
            }
        )

        adapterNext = JadwalAdapter(
            mutableListOf(),
            emptyMap(),
            onDelete = { id ->
                Storage.deleteJadwalObj(requireContext(), id)
                loadJadwal()
            },
            onEdit = { raw ->
                startActivity(
                    Intent(requireContext(), TambahJadwalActivity::class.java)
                        .putExtra("editData", raw)
                )
            }
        )

        rvHariIni.adapter = adapterHariIni
        rvNext.adapter = adapterNext

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
        val listObj = Storage.getJadwalList(requireContext())
        val listRaw = Storage.getJadwal(requireContext())

        val rawById = mutableMapOf<Long, String>()
        listRaw.forEach { raw ->
            raw.split("#").firstOrNull()?.toLongOrNull()?.let { id ->
                rawById[id] = raw
            }
        }

        val today = sdfFull.parse(sdfFull.format(Date())) ?: return

        val hariIniList = mutableListOf<Jadwal>()
        val nextList = mutableListOf<Jadwal>()

        listObj.forEach { jadwal ->
            val d = parseDate(jadwal.hari) ?: return@forEach
            when {
                isSameDay(d, today) -> hariIniList.add(jadwal)
                d.after(today) -> nextList.add(jadwal)
            }
        }

        hariIniList.sortBy { it.jamMulai }
        nextList.sortBy { it.jamMulai }

        adapterHariIni.updateRawMap(rawById)
        adapterNext.updateRawMap(rawById)

        adapterHariIni.submitData(hariIniList)
        adapterNext.submitData(nextList)
    }

    private fun parseDate(text: String): Date? =
        try { sdfFull.parse(text) } catch (e: Exception) { null }

    private fun isSameDay(d1: Date, d2: Date): Boolean {
        val c1 = Calendar.getInstance().apply { time = d1 }
        val c2 = Calendar.getInstance().apply { time = d2 }
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)
    }
}