package com.example.foodorderingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.databinding.OrderStatusItemlayoutBinding

class orderStatusAdapter(private val OrderStatusItemName:MutableList<String>,
                        private val OrderStatusItemImage:MutableList<Int>)
    : RecyclerView.Adapter<orderStatusAdapter.OrderViewHolder>() {
    inner class OrderViewHolder(private val binding:OrderStatusItemlayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        private val Image = binding.orderItemImage
        fun bind(position: Int) {
            Image.setImageResource(OrderStatusItemImage[position])
            binding.orderItemName.text = OrderStatusItemName[position]
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = OrderViewHolder(OrderStatusItemlayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        return binding
    }

    override fun getItemCount(): Int = OrderStatusItemName.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(position)
    }
}