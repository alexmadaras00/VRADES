package com.example.vrades.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vrades.databinding.ItemQuestionBinding
import com.example.vrades.model.Question

class AdapterQuestions() : RecyclerView.Adapter<AdapterQuestions.ViewHolder>() {

    private var questions = ArrayList<Question>()

    inner class ViewHolder(private val binding: ItemQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Question) {
            binding.item = item
            binding.tvQuestionText.text = item.text
            binding.executePendingBindings()
        }
    }

    fun setDataSource(items: ArrayList<Question>) {
        this.questions = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemQuestionBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterQuestions.ViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    override fun getItemCount(): Int {
        return questions.size
    }

}