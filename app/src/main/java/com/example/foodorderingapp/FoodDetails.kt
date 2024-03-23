package com.example.foodorderingapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.foodorderingapp.databinding.ActivityFoodDetailsBinding
import com.example.foodorderingapp.datamodel.cartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FoodDetails : AppCompatActivity() {
    private lateinit var FoodName:String
    private lateinit var foodDescription:String
    private lateinit var FoodPrice:String
    private lateinit var foodIngredent:String
    private lateinit var foodImage:String
    private lateinit var firebaseAuth:FirebaseAuth
    private val binding : ActivityFoodDetailsBinding by lazy {
        ActivityFoodDetailsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firebaseAuth =  FirebaseAuth.getInstance()

        FoodName = intent.getStringExtra("foodName").toString()
        FoodPrice = intent.getStringExtra("foodPrice").toString()
        foodDescription = intent.getStringExtra("foodDescription").toString()
        foodIngredent = intent.getStringExtra("foodIngredent").toString()
        foodImage = intent.getStringExtra("foodImage").toString()
        //setting food details
        setFoodDetails()

        binding.backButtonFoodDetails.setOnClickListener(){
            finish()
        }
        binding.addToCart.setOnClickListener(){
            addItemToCart()
        }
    }

    private fun addItemToCart() {
        val database =  FirebaseDatabase.getInstance().reference
        val userId =  firebaseAuth.currentUser?.uid?:""

        //create a cart data model
        val CartItem = cartItem(FoodName,FoodPrice,foodDescription,foodIngredent,foodImage,1)
        // save crt item data in firebase
        database.child("user").child(userId).child("cartItem").push().setValue(CartItem)
            .addOnSuccessListener {
            makeToast("item added")
        }.addOnFailureListener(){
            makeToast("item adding failed")
        }
    }

    private fun makeToast(s: String) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show()
    }

    private fun setFoodDetails() {
        binding.foodname.text = FoodName
        binding.FoodDescription.text = foodDescription
        binding.Ingredients.text = foodIngredent
        Glide.with(this).load(Uri.parse(foodImage)).into(binding.foodImage)
    }

}