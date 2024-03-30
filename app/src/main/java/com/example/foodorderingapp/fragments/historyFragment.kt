package com.example.foodorderingapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapters.historyAdapter
import com.example.foodorderingapp.databinding.FragmentHistoryBinding
import com.example.foodorderingapp.datamodel.orderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class historyFragment : Fragment() {
    private lateinit var binding:FragmentHistoryBinding
    private lateinit var adapter:historyAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userId:String
    private var listOfOrderItem:MutableList<orderDetails> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater,container,false)

        retrieveBuyHistory()
        return binding.root
    }

    private fun retrieveBuyHistory() {
        binding.CurrentBuyCardView.visibility = View.INVISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val PopularFoodName = listOf("burger","sandwich","fry","cake")
        val PopulearFoodPrice = listOf("Rs110","Rs60","Rs65","Rs60")
        val PopulearFoodImage = listOf(R.drawable.burger, R.drawable.sandwich, R.drawable.frys, R.drawable.cake)
        adapter = historyAdapter(PopularFoodName as MutableList<String>,PopulearFoodPrice as MutableList<String>,PopulearFoodImage as MutableList<Int>)
        binding.RecyclierHistoryFragment.layoutManager = LinearLayoutManager(requireContext())
        binding.RecyclierHistoryFragment.adapter = adapter

        binding.currentBuyFoodImage.setImageResource(R.drawable.burger)
        binding.CurrentFoodPrice.text = "Rs100"
        binding.CurrentBuyFoodName.text = "Burger"
    }

    companion object {
    }
}