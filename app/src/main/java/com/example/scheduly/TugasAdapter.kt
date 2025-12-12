package com.example.scheduly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TugasAdapter(
    private val list: MutableList<Tugas>,
    private val onDelete: (Long) -> Unit,
    private val onEdit: (Tugas) -> Unit
) : RecyclerView.Adapter<TugasAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvJudul: TextView = view.findViewById(R.id.tvJudulTugas)
        val matakuliah : TextView =view.findViewById(R.id.tvMataKuliah)
        val tvDeadline: TextView = view.findViewById(R.id.tvDeadline)
        val tvCatatan: TextView = view.findViewById(R.id.tvDeskripsiTugas)
        val btnHapus: TextView = view.findViewById(R.id.btnHapusTugas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tugas, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tugas = list[position]

        holder.tvJudul.text = tugas.namaTugas
        holder.tvDeadline.text = "Deadline: ${tugas.hari}"
        holder.tvCatatan.text = tugas.catatan

        holder.btnHapus.setOnClickListener {
            onDelete(tugas.id)
        }

        holder.itemView.setOnClickListener {
            onEdit(tugas)
        }
    }

    override fun getItemCount(): Int = list.size

    fun submitData(newList: List<Tugas>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}
