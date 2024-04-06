package com.example.foodorderingapp.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderingapp.FoodDetails
import com.example.foodorderingapp.databinding.PopulearitemlayoutBinding
import com.example.foodorderingapp.datamodel.cartItem
import com.example.foodorderingapp.datamodel.menuitemModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class populearAdapter(private val menuItem:List<menuitemModel>,private val requireCotext:Context,private val firebaseAuth: FirebaseAuth)
    : RecyclerView.Adapter<populearAdapter.populearViewHolder>(){
        inner class populearViewHolder(private val binnding:PopulearitemlayoutBinding) : RecyclerView.ViewHolder(binnding.root) {
        fun bind(position: Int) {
            binnding.FoodName.text = menuItem[position].foodName
            binnding.FoodPrice.text = menuItem[position].foodPrice
            val menuImageUri = Uri.parse(menuItem[position].foodImage)
            Glide.with(requireCotext).load(menuImageUri).into(binnding.PopulearItemImageView)
            binnding.AddToCart.setOnClickListener{
                menuItem[position].foodIngradent?.let { it1 ->
                    addItemToCart(menuItem[position].foodName.toString(),
                        menuItem[position].foodPrice.toString(),
                        menuItem[position].foodDiscribtion.toString(),
                        menuItem[position].foodIngradent.toString(),
                        menuItem[position].foodImage.toString()
                    )
                }
            }
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
    private fun addItemToCart(FoodName:String,FoodPrice:String,foodDescription:String,foodIngredent:String,foodImage:String) {
        val database =  FirebaseDatabase.getInstance().reference
        val userId =  firebaseAuth.currentUser?.uid?:""

        //create a cart data model
        val CartItem = cartItem(FoodName,FoodPrice,foodDescription,foodIngredent,foodImage,1)
        // save crt item data in firebase
        database.child("Customer").child(userId).child("cartItem").push().setValue(CartItem)
            .addOnSuccessListener {
                makeToast("item added")
            }.addOnFailureListener(){
                makeToast("item adding failed")
            }
    }

    private fun makeToast(s: String) {
        Toast.makeText(requireCotext,s, Toast.LENGTH_SHORT).show()
    }
}