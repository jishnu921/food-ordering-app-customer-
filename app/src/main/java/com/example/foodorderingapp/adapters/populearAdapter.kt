package com.example.foodorderingapp.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderingapp.FoodDetails
import com.example.foodorderingapp.databinding.PopulearitemlayoutBinding
import com.example.foodorderingapp.datamodel.menuitemModel

class populearAdapter(private val menuItem:List<menuitemModel>,private val requireCotext:Context)
    : RecyclerView.Adapter<populearAdapter.populearViewHolder>(){
    inner class populearViewHolder(private val binnding:PopulearitemlayoutBinding) : RecyclerView.ViewHolder(binnding.root) {
        fun bind(position: Int) {
            binnding.FoodName.text = menuItem[position].foodName
            binnding.FoodPrice.text = menuItem[position].foodPrice
            val menuImageUri = Uri.parse(menuItem[position].foodImage)
            Glide.with(requireCotext).load(menuImageUri).into(binnding.PopulearItemImageView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): populearViewHolder {
        return populearViewHolder(PopulearitemlayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return menuItem.size
    }

    override fun onBindViewHolder(holder: populearViewHolder, position: Int) {
        holder.bind(position)

        holder.itemView.setOnClickListener() {
            openItemDetails(position)
        }
    }

    private fun openItemDetails(position: Int) {
        val menuItem = menuItem[position]

        val intent = Intent(requireCotext,FoodDetails::class.java).apply {
            putExtra("foodName",menuItem.foodName)
            putExtra("foodPrice",menuItem.foodPrice)
            putExtra("foodDescription",menuItem.foodDiscribtion)
            putExtra("foodIngredent",menuItem.foodIngradent)
            putExtra("foodImage",menuItem.foodImage)
        }
        requireCotext.startActivity(intent)
    }
}