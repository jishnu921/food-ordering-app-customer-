package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.foodorderingapp.databinding.ActivityLoginPageBinding
import com.example.foodorderingapp.databinding.ActivityStartScreenBinding

class LoginPage : AppCompatActivity() {
    private val binding: ActivityLoginPageBinding by lazy {
        ActivityLoginPageBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.GoSignUp.setOnClickListener(){
            startActivity(Intent(this,SignInPage::class.java))
        }
        binding.LoginButton.setOnClickListener(){
            startActivity(Intent(this,locationSelection::class.java))
        }
    }
}