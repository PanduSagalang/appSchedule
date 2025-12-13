package com.example.scheduly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JadwalAdapter(
    private val list: MutableList<Jadwal>,
    private var rawById: Map<Long, String>,
    private val onDelete: (Long) -> Unit,
    private val onEdit: (String?) -> Unit
) : RecyclerView.Adapter<JadwalAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNamaKelas: TextView = view.findViewById(R.id.tvNamaKelas)
        val tvTanggal: TextView = view.findViewById(R.id.tvTanggal)
        val tvDetail: TextView = view.findViewById(R.id.tvDetail)
        val tvCatatan: TextView = view.findViewById(R.id.tvCatatan)
        val btnHapus: TextView = view.findViewById(R.id.btnHapusJadwal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_jadwal, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jadwal = list[position]

        holder.tvNamaKelas.text = jadwal.namaKelas
        holder.tvTanggal.text = jadwal.hari
        holder.tvDetail.text =
            "Ruang ${jadwal.ruang}, ${jadwal.jamMulai}–${jadwal.jamSelesai}"
        holder.tvCatatan.text = "Dosen : ${jadwal.dosen}"

        holder.btnHapus.setOnClickListener {
            onDelete(jadwal.id)
        }

        holder.itemView.setOnClickListener {
            onEdit(rawById[jadwal.id])
        }
    }

    override fun getItemCount(): Int = list.size

    fun submitData(newList: List<Jadwal>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun updateRawMap(newMap: Map<Long, String>) {
        rawById = newMap
    }
}
