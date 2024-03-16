package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.Word
import com.example.myapplication.databinding.ListItemBinding

class RecyclerViewAdapter(
    private val words: List<Word>,
    private val clickListener: (Word) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapter.DictViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DictViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_item, parent, false
        )
    )

    override fun onBindViewHolder(holder: DictViewHolder, position: Int) =
        holder.bind(words[position], clickListener)

    override fun getItemCount() = words.size

    inner class DictViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word, clickListener: (Word) -> Unit) = with(binding) {
            wordTextView.text = word.word
            translateTextView.text = word.translate
            listItemLayout.setOnClickListener { clickListener(word) }
        }
    }

}