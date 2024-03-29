package com.example.foodorderingapp.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderingapp.adapters.cartAdapter.MyCartViewHolder
import com.example.foodorderingapp.databinding.CartItemLayoutBinding
import com.example.foodorderingapp.datamodel.cartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class cartAdapter(private val CartItemName: MutableList<String>,
                  private val CartItemPrice:MutableList<String>,
                  private val CartItemImage: MutableList<String>,
                  private val CartDescription:MutableList<String>,
                  private val CartIngedeient:MutableList<String>,
                  private val CartQuienty:MutableList<Int>,
                  private val context:Context,)
    :RecyclerView.Adapter<MyCartViewHolder>() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    init {
        val database =  FirebaseDatabase.getInstance()
        val UserId = firebaseAuth.currentUser?.uid?:""
        val cartItemNumber = CartItemName.size

        quantityOfItem =  IntArray(cartItemNumber){1}
        databaseReference =  database.reference.child("Customer").child(UserId).child("cartItem")
    }
    companion object{
        private var quantityOfItem : IntArray =  intArrayOf()
        private lateinit var databaseReference: DatabaseReference
    }
    inner class MyCartViewHolder(private val binding: CartItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
                binding.CartItemNameTextView.text = CartItemName[position]
                binding.CartItemPriceTextView.text = CartItemPrice[position]
                binding.quantityCartItem.text = quantityOfItem[position].toString()
                //loading image using Glide
                val uriString = CartItemImage[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(binding.CartItemImageView)
                binding.reduseItemCart.setOnClickListener() {
                    reduseQuantity()
                }
                binding.AddItemCart.setOnClickListener() {
                    addQuantity()
                }
                binding.DeleteItemCart.setOnClickListener() {
                        deleteQuantity(position)
            }
        }
        fun reduseQuantity(){
            if (quantityOfItem[adapterPosition]>1){
                quantityOfItem[adapterPosition]--
                CartQuienty[adapterPosition] = quantityOfItem[adapterPosition]
                binding.quantityCartItem.text = quantityOfItem[adapterPosition].toString()
            }
        }
        fun deleteQuantity(position: Int){
            val positionRetrive =  position
            getUniqueKeyAtPostion(positionRetrive){uniqueKey ->
                if (uniqueKey!=null){
                    removeItem(position,uniqueKey)
                }
            }
        }
        fun addQuantity(){
            if (quantityOfItem[adapterPosition]<10) {
                quantityOfItem[adapterPosition]++
                CartQuienty[adapterPosition] = quantityOfItem[adapterPosition]
            }
            binding.quantityCartItem.text = quantityOfItem[adapterPosition].toString()
        }
    }

    private fun removeItem(position: Int, uniqueKey: String) {
        if (uniqueKey !=null){
            databaseReference.child(uniqueKey).removeValue()
                .addOnSuccessListener {
                CartItemName.removeAt(position)
                CartItemImage.removeAt(position)
                CartDescription.removeAt(position)
                CartQuienty.removeAt(position)
                CartItemPrice.removeAt(position)
                CartIngedeient.removeAt(position)
                Toast.makeText(context,"Item Delete",Toast.LENGTH_SHORT).show()
                quantityOfItem=  quantityOfItem.filterIndexed {index, i -> index!=position }.toIntArray()
                notifyItemRemoved(position)
                notifyItemRangeChanged(position,CartItemName.size)
            }.addOnFailureListener{
                Toast.makeText(context,"Failed to Delete",Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun getUniqueKeyAtPostion(positionRetrive: Int,onComplete:(String?)-> Unit) {
        databaseReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var UniqueKey:String?=null
                snapshot.children.forEachIndexed { index, dataSnapshot ->
                    if (index == positionRetrive){
                        UniqueKey =  dataSnapshot.key
                        return@forEachIndexed
                    }
                }
                onComplete(UniqueKey)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
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
    fun getUpdatedItemQuantity(): MutableList<Int> {
        val itemQuantity =  mutableListOf<Int>()
        itemQuantity.addAll(CartQuienty)
        return itemQuantity
    }
}