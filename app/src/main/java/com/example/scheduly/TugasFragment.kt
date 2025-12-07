package com.example.scheduly

import android.content.Intent
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
        val btnTambahTugasFragment = view.findViewById<Button>(R.id.btnTambahTugas)

        btnTambahTugasFragment.setOnClickListener {
            startActivity(Intent(requireContext(), TambahTugasActivity::class.java))
        }

        loadTugas(containerList)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadTugas(view.findViewById(R.id.containerListTugas))
    }

    override fun onResume() {
        super.onResume()
        view?.findViewById<LinearLayout>(R.id.containerListTugas)?.let {
            loadTugas(it)
        }
    }


    private fun loadTugas(container: LinearLayout) {
        container.removeAllViews()

        val listTugas = Storage.getTugasList(requireContext())

        for (t in listTugas) {
            val itemView = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_tugas, container, false)

            itemView.findViewById<TextView>(R.id.tvJudulTugas).text = t.namaTugas
            itemView.findViewById<TextView>(R.id.tvMataKuliah).text = t.mataKuliah
            itemView.findViewById<TextView>(R.id.tvDeadline).text = t.hari
            itemView.findViewById<TextView>(R.id.tvDeskripsiTugas).text = t.catatan

            itemView.setOnClickListener {
                val data = "${t.id}#${t.namaTugas}#${t.mataKuliah}#${t.hari}#${t.catatan}"

                val intent = Intent(requireContext(), TambahTugasActivity::class.java)
                intent.putExtra("editData", data)
                startActivity(intent)
            }

            val btnHapus = itemView.findViewById<Button>(R.id.btnHapusTugas)
            btnHapus.setOnClickListener {
                Storage.deleteTugasObj(requireContext(), t.id)
                loadTugas(container)
            }

            container.addView(itemView)
        }
    }

}
