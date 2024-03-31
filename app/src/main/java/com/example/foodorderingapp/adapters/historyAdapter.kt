package com.example.foodorderingapp.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderingapp.databinding.HistoryRecyclearviewLayoutBinding

class historyAdapter(private val HistoryItemName:MutableList<String>,
                     private  val HistoryItemPrice:MutableList<String>,
                     private val HistoryItemImage: MutableList<String>,
                     private val context: Context):
    RecyclerView.Adapter<historyAdapter.HistoryViewHolder>() {
    inner class HistoryViewHolder(private val binding:HistoryRecyclearviewLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        private val Image = binding.historyItemImageView
        fun bind(position: Int) {
            binding.historyItemNameTextView.text = HistoryItemName[position]
            binding.historyItemPriceTextView.text = HistoryItemPrice[position]
            val uriString = HistoryItemImage[position]
            val uri = Uri.parse(uriString)
            Glide.with(context).load(uri).into(binding.historyItemImageView)
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