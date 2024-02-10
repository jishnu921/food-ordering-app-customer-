package com.example.foodorderingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.databinding.PopulearitemlayoutBinding

class populearAdapter(private val PopulearItemName:List<String>,
                      private  val PopulearItemPrice:List<String>,
                      private val PopulearItemImage: List<Int> )
    : RecyclerView.Adapter<populearAdapter.populearViewHolder>(){
    class populearViewHolder(private val binnding:PopulearitemlayoutBinding) : RecyclerView.ViewHolder(binnding.root) {

        private val Image = binnding.PopulearItemImageView
        fun bind(populearItemNames: String, populearItemPrice: String, populearItemImage: Int) {
            binnding.PopulearItemNameTextView.text = populearItemNames
            Image.setImageResource(populearItemImage)
            binnding.PopulearItemPriceTextView.text = populearItemPrice
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): populearViewHolder {
        return populearViewHolder(PopulearitemlayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return PopulearItemName.size
    }

    override fun onBindViewHolder(holder: populearViewHolder, position: Int) {
        val PopulearItemNames = PopulearItemName[position]
        val PopulearItemPrice = PopulearItemPrice[position]
        val PopulearItemImage = PopulearItemImage[position]
        holder.bind(PopulearItemNames,PopulearItemPrice,PopulearItemImage)
    }
}