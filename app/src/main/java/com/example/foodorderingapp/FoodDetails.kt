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

        val FoodName = intent.getStringExtra("FoodName")
        val FoodImage = intent.getIntExtra("FoodImage",0)

        binding.foodImage.setImageResource(FoodImage)
        binding.foodname.text = FoodName

        binding.backButtonFoodDetails.setOnClickListener(){
            finish()
        }
    }
}