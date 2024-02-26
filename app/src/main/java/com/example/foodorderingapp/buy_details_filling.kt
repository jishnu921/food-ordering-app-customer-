package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodorderingapp.databinding.ActivityBuyDetailsFillingBinding
import com.example.foodorderingapp.fragments.cartfragment
import com.example.foodorderingapp.fragments.congrats_bottomSheet

class buy_details_filling : AppCompatActivity() {

    val binding:ActivityBuyDetailsFillingBinding by lazy {
        ActivityBuyDetailsFillingBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backButtonButDetails.setOnClickListener(){
            finish()
        }
        binding.placeOrderButton.setOnClickListener(){
                val orderStatus = congrats_bottomSheet()
                orderStatus.show(supportFragmentManager,"Test")
        }
    }
}