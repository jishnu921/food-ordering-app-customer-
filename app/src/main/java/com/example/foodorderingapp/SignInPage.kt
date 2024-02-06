package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodorderingapp.databinding.ActivitySignInPageBinding

class SignInPage : AppCompatActivity() {
    private val binding:ActivitySignInPageBinding by lazy{
        ActivitySignInPageBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.GoLoginPage.setOnClickListener(){
            startActivity(Intent(this,LoginPage::class.java))
        }
    }
}