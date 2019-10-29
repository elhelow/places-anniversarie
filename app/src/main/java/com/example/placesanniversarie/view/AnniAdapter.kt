package com.example.Anniversariesanniversarie.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.placesanniversarie.R
import com.example.placesanniversarie.data.model.Anniversarie

class AnniAdapter(private val list: List<Anniversarie>) :
    RecyclerView.Adapter<AnniAdapter.AnniversarieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnniversarieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_item_anni, parent, false)
        return AnniversarieViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnniversarieViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class AnniversarieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var date: TextView
        internal var name: TextView

        init {
            date = itemView.findViewById(R.id.date)
            name = itemView.findViewById(R.id.name)
        }

        fun bind(Anniversarie: Anniversarie) {

            name.text = Anniversarie.name
            date.text = Anniversarie.date
        }
    }

}
