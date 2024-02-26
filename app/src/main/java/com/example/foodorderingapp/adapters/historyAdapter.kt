package com.example.foodorderingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.databinding.HistoryRecyclearviewLayoutBinding

class historyAdapter(private val HistoryItemName:MutableList<String>,
                     private  val HistoryItemPrice:MutableList<String>,
                     private val HistoryItemImage: MutableList<Int> ):
    RecyclerView.Adapter<historyAdapter.HistoryViewHolder>() {
    inner class HistoryViewHolder(private val binding:HistoryRecyclearviewLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        private val Image = binding.historyItemImageView
        fun bind(position: Int) {
            binding.historyItemNameTextView.text = HistoryItemName[position]
            Image.setImageResource(HistoryItemImage[position])
            binding.historyItemPriceTextView.text = HistoryItemPrice[position]
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryViewHolder(HistoryRecyclearviewLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        return binding
    }

    override fun getItemCount(): Int {
        return HistoryItemName.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(position)
    }
}