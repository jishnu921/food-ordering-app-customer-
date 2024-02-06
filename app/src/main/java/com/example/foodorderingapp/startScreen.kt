package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.foodorderingapp.databinding.ActivityStartScreenBinding

class startScreen : AppCompatActivity() {

    private val binding:ActivityStartScreenBinding by lazy {
        ActivityStartScreenBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.nextButtonStartScreen.setOnClickListener(){
            startActivity(Intent(this,LoginPage::class.java));
        }
    }
}