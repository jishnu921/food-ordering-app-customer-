package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodorderingapp.databinding.ActivityFoodDetailsBinding

class FoodDetails : AppCompatActivity() {
    private val binding : ActivityFoodDetailsBinding by lazy {
        ActivityFoodDetailsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val FoodName = intent.getStringExtra("foodName")
        val FoodPrice = intent.getStringExtra("foodPrice")
        val foodDescription = intent.getStringExtra("foodDescription")
        val foodIngredent = intent.getStringExtra("foodIngredent")
        //setting food details
        binding.foodname.text = FoodName
        binding.FoodDescription.text = foodDescription
        binding.Ingredients.text = foodIngredent

        binding.backButtonFoodDetails.setOnClickListener(){
            finish()
        }
    }

}