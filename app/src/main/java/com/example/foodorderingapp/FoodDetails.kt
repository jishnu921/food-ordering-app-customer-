package com.example.foodorderingapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.foodorderingapp.databinding.ActivityFoodDetailsBinding

class FoodDetails : AppCompatActivity() {
    private lateinit var FoodName:String
    private lateinit var foodDescription:String
    private lateinit var FoodPrice:String
    private lateinit var foodIngredent:String
    private lateinit var foodImage:String
    private val binding : ActivityFoodDetailsBinding by lazy {
        ActivityFoodDetailsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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
    }

    private fun setFoodDetails() {
        binding.foodname.text = FoodName
        binding.FoodDescription.text = foodDescription
        binding.Ingredients.text = foodIngredent
        Glide.with(this).load(Uri.parse(foodImage)).into(binding.foodImage)
    }

}