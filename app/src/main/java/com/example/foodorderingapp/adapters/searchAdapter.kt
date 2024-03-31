package com.example.foodorderingapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.FoodDetails
import com.example.foodorderingapp.databinding.PopulearitemlayoutBinding

class searchAdapter(private val SearchItemName:MutableList<String>,
                    private  val SearchItemPrice:MutableList<String>,
                    private val SearchItemImage: MutableList<Int>,
                    private val requireContext: Context
):RecyclerView.Adapter<searchAdapter.SerachViewHolder>() {
    private val itemClickLisner : OnClickListener ?= null
    inner class SerachViewHolder(private val binding: PopulearitemlayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener(){
                val postion = adapterPosition
                if (postion != RecyclerView.NO_POSITION ){
                    itemClickLisner?.onItemClick(postion)
                }

                val intent = Intent(requireContext, FoodDetails::class.java)
                intent.putExtra("FoodName", SearchItemName.get(position))
                intent.putExtra("FoodImage", SearchItemImage.get(position))
                requireContext.startActivity(intent)
            }
        }
        fun bind(position: Int) {
            binding.apply {
                FoodName.text = SearchItemName[position]
                FoodPrice.text = SearchItemPrice[position]
                PopulearItemImageView.setImageResource(SearchItemImage[position])

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerachViewHolder {

        return SerachViewHolder(
            PopulearitemlayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int = SearchItemName.size

    override fun onBindViewHolder(holder: SerachViewHolder, position: Int) {
        holder.bind(position)
    }
    interface OnClickListener {
        fun onItemClick(postion: Int)
        {

        }
    }
}

