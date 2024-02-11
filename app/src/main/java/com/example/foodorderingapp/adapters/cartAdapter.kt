package com.example.foodorderingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.adapters.cartAdapter.MyCartViewHolder
import com.example.foodorderingapp.databinding.CartItemLayoutBinding

class cartAdapter(private val CartItemName: MutableList<String>,
                  private val CartItemPrice:MutableList<String>,
                  private val CartItemImage: MutableList<Int>)
    :RecyclerView.Adapter<MyCartViewHolder>() {

    private val quantityOfItem = IntArray(CartItemName.size){1}
    inner class MyCartViewHolder(private val binding: CartItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
        private val Image = binding.CartItemImageView
        fun bind(position: Int) {
            binding.apply {
                binding.CartItemNameTextView.text = CartItemName[position]
                binding.CartItemPriceTextView.text = CartItemPrice[position]
                binding.quantityCartItem.text = quantityOfItem[position].toString()
                Image.setImageResource(CartItemImage[position])
                binding.reduseItemCart.setOnClickListener() {
                    reduseQuantity()
                }
                binding.AddItemCart.setOnClickListener() {
                    addQuantity()
                }
                binding.DeleteItemCart.setOnClickListener() {
                        deleteQuantity(adapterPosition)
                }
            }
        }
        fun reduseQuantity(){
            if (quantityOfItem[adapterPosition]>1) {
                quantityOfItem[adapterPosition]--
                binding.quantityCartItem.text = quantityOfItem[adapterPosition].toString()
            }
            else{
                val itemPostion = adapterPosition
                if (itemPostion != RecyclerView.NO_POSITION)
                deleteQuantity(itemPostion)
            }
        }
        fun deleteQuantity(Position: Int){
            CartItemImage.removeAt(Position)
            CartItemName.removeAt(Position)
            CartItemPrice.removeAt(Position)
            notifyItemRemoved(Position)
            notifyItemChanged(Position,CartItemName.size)
        }
        fun addQuantity(){
            if (quantityOfItem[adapterPosition]<10)
            quantityOfItem[adapterPosition]++
            binding.quantityCartItem.text = quantityOfItem[adapterPosition].toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCartViewHolder {
            val binding = CartItemLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false)
            return MyCartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return CartItemName.size
    }

    override fun onBindViewHolder(holder: MyCartViewHolder, position: Int) {
        holder.bind(position)
    }
}