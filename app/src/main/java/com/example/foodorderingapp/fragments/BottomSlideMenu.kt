package com.example.foodorderingapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapters.populearAdapter
import com.example.foodorderingapp.databinding.FragmentBottomSlideMenuBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSlideMenu : BottomSheetDialogFragment() {
    lateinit var binding:FragmentBottomSlideMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      binding = FragmentBottomSlideMenuBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val PopularFoodName = listOf("burger","sandwich","fry","cake","burger","sandwich","fry","cake")
        val PopulearFoodPrice = listOf("Rs110","Rs60","Rs65","Rs60","Rs110","Rs60","Rs65","Rs60")
        val PopulearFoodImage = listOf(R.drawable.burger, R.drawable.sandwich, R.drawable.frys, R.drawable.cake,R.drawable.burger, R.drawable.sandwich, R.drawable.frys, R.drawable.cake)
        val adapter = populearAdapter(PopularFoodName as MutableList<String>,PopulearFoodPrice as MutableList<String>,PopulearFoodImage as MutableList<Int>,requireContext())
        binding.reyclerViewBottomSlid.layoutManager = LinearLayoutManager(requireContext())
        binding.reyclerViewBottomSlid.adapter = adapter
    }
    companion object {
    }
}