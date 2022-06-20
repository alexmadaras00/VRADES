package com.example.vrades.ui.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.example.vrades.R
import com.example.vrades.databinding.ItemLifehackBinding
import com.example.vrades.model.LifeHack
import com.example.vrades.ui.binding.loadForegroundImageUrl
import com.example.vrades.ui.binding.loadIconImageUrl
import com.example.vrades.ui.binding.loadImageUrl
import javax.sql.DataSource


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

    override fun onBindViewHolder(holder: AdapterLifeHacks.ViewHolder, position: Int) {
        holder.bind(lifeHacks[position])
    }

    override fun getItemCount(): Int {
        return lifeHacks.size
    }

}