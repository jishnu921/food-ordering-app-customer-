package com.example.foodorderingapp.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderingapp.databinding.CurentItemBinding

class currentBuyAdapter(private var context: Context,
                        private var foodNameList:ArrayList<String>,
                        private var foodPriceList:ArrayList<String>,
                        private var foodImageList:ArrayList<String>,
                        private var foodQuantityList:ArrayList<Int>)
    :RecyclerView.Adapter<currentBuyAdapter.ViewHolder>(){
    inner class ViewHolder(private val binding : CurentItemBinding)
        :RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                FoodName.text = foodNameList[position]
                FoodPrice.text = foodPriceList[position]
                //total amount price * quantity
                totalAmount.text = "Rs"+(foodPriceList[position].drop(2).toInt()*foodQuantityList[position]).toString()
                Quanity.text = "Quanity: "+foodQuantityList[position].toString()
                val uriString = foodImageList[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(FoodImage)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CurentItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: currentBuyAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return foodNameList.size
    }

}