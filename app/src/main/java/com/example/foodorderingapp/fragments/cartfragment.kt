package com.example.foodorderingapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapters.cartAdapter
import com.example.foodorderingapp.databinding.FragmentCartfragmentBinding


class cartfragment : Fragment() {
    private lateinit var binding:FragmentCartfragmentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentCartfragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val CartFoodName = listOf("burger","sandwich","fry","cake","burger","sandwich","fry","cake")
        val CartFoodPrice = listOf("Rs110","Rs60","Rs65","Rs60","Rs110","Rs60","Rs65","Rs60")
        val CartFoodImage = listOf(R.drawable.burger, R.drawable.sandwich, R.drawable.frys, R.drawable.cake,R.drawable.burger, R.drawable.sandwich, R.drawable.frys, R.drawable.cake)
        val adapter = cartAdapter(ArrayList(CartFoodName)
            ,ArrayList(CartFoodPrice),
            ArrayList(CartFoodImage)
        )
        binding.RecyclerViewCardFragment.layoutManager = LinearLayoutManager(requireContext())
        binding.RecyclerViewCardFragment.adapter =adapter
    }
    companion object {
    }
}