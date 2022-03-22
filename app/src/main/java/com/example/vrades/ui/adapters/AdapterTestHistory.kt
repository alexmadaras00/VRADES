package com.example.vrades.ui.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.vrades.R
import com.example.vrades.databinding.ItemTestBinding
import com.example.vrades.enums.TestState
import com.example.vrades.models.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AdapterTestHistory() : RecyclerView.Adapter<AdapterTestHistory.ViewHolder>() {
    private var test = ArrayList<Test>()

    @SuppressLint("WeekBasedYear")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun currentTime(time: LocalDate): String? {

        val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("DD:MM:YY")
        return dtf.format(time)
    }

    inner class ViewHolder(private val binding: ItemTestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: Test) {
            binding.item = item
            binding.tvDate.text = currentTime(item.date).toString()
            when (item.isCompleted) {
                TestState.FACE_DETECTION_COMPLETED ->
                    binding.ivFace.setColorFilter(R.color.background)
                TestState.AUDIO_DETECTION_COMPLETED ->
                    binding.ivAudio.setColorFilter(R.color.background)
                TestState.WRITING_DETECTION_COMPLETED ->
                    binding.ivWriting.setColorFilter(R.color.background)
                else -> {
                    binding.ivFace.setColorFilter(R.color.white)
                    binding.ivAudio.setColorFilter(R.color.white)
                    binding.ivWriting.setColorFilter(R.color.white)
                }
            }
            binding.executePendingBindings()
        }
    }

    fun setDataSource(items: ArrayList<Test>) {
        this.test = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTestBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: AdapterTestHistory.ViewHolder, position: Int) {
        holder.bind(test[position])
    }

    override fun getItemCount(): Int {
        return test.size
    }

}
