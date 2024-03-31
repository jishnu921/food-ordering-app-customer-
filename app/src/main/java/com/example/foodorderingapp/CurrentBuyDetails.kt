package com.example.foodorderingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.adapters.currentBuyAdapter
import com.example.foodorderingapp.databinding.ActivityCurrentBuyDetailsBinding
import com.example.foodorderingapp.datamodel.orderDetails

class CurrentBuyDetails : AppCompatActivity() {
    private val binding:ActivityCurrentBuyDetailsBinding by lazy {
        ActivityCurrentBuyDetailsBinding.inflate(layoutInflater)
    }
    private lateinit var allfoodName:ArrayList<String>
    private lateinit var allfoodPrice:ArrayList<String>
    private lateinit var allfoodImage:ArrayList<String>
    private lateinit var allfoodQuanity:ArrayList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backButton.setOnClickListener(){
            finish()
        }

        val recentOrderItem = intent.getSerializableExtra("RecentBuyOrderItem") as ArrayList<orderDetails>
        recentOrderItem?.let { orderDetails->
            if (orderDetails.isNotEmpty()){
                val recentOrderItem = orderDetails[0]

                allfoodName = recentOrderItem.foodNames as ArrayList<String>
                allfoodPrice = recentOrderItem.foodPrice as ArrayList<String>
                allfoodQuanity = recentOrderItem.foodQuantity as ArrayList<Int>
                allfoodImage= recentOrderItem.foodImage as ArrayList<String>
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
        val recyclerView = binding.RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = currentBuyAdapter(this,allfoodName,allfoodPrice,allfoodImage,allfoodQuanity)
        recyclerView.adapter = adapter
    }
}