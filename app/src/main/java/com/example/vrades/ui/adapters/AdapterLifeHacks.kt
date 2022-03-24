package com.example.vrades.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vrades.databinding.ItemLifehackBinding
import com.example.vrades.databinding.ItemQuestionBinding
import com.example.vrades.models.LifeHack
import com.example.vrades.models.Question

class AdapterLifeHacks() : RecyclerView.Adapter<AdapterLifeHacks.ViewHolder>() {

    private var lifeHacks = ArrayList<LifeHack>()

    inner class ViewHolder(private val binding: ItemLifehackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LifeHack) {
            binding.item = item
            binding.tvLifeHackName.text = item.name
            binding.ivLifeHackIcon.setImageResource(item.icon)
            if (item.details != "")
                binding.tvLifeHackDescription.text = item.details
            binding.executePendingBindings()
        }
    }

    fun setDataSource(items: ArrayList<LifeHack>) {
        this.lifeHacks= items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLifehackBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterLifeHacks.ViewHolder, position: Int) {
        holder.bind(lifeHacks[position])
    }

    override fun getItemCount(): Int {
        return lifeHacks.size
    }

}