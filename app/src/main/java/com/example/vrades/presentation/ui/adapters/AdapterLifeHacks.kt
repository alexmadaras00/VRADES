package com.example.vrades.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vrades.databinding.ItemLifehackBinding
import com.example.vrades.domain.model.LifeHack
import com.example.vrades.presentation.ui.binding.loadForegroundImageUrl
import com.example.vrades.presentation.ui.binding.loadImageUrl


class AdapterLifeHacks() : RecyclerView.Adapter<AdapterLifeHacks.ViewHolder>() {

    private var lifeHacks = ArrayList<LifeHack>()

    inner class ViewHolder(private val binding: ItemLifehackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LifeHack) {
            binding.apply {
                binding.item = item
                tvLifeHackName.text = item.name
                loadForegroundImageUrl(ivLifeHackIcon, item.icon)
                loadImageUrl(ivLifeHackIcon, item.icon)
                if (item.details != "")
                    tvLifeHackDescription.text = item.details
                binding.executePendingBindings()
            }
        }
    }

    fun setDataSource(items: List<LifeHack>) {
        this.apply {
            if (lifeHacks.isNotEmpty()) {
                lifeHacks.clear()
            }
            lifeHacks.addAll(items)
            notifyDataSetChanged()
        }
       
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLifehackBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lifeHacks[position])
    }

    override fun getItemCount(): Int {
        return lifeHacks.size
    }

}