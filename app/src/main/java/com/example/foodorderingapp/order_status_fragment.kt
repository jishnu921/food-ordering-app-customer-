package com.example.foodorderingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.adapters.orderStatusAdapter
import com.example.foodorderingapp.databinding.FragmentHomeBinding
import com.example.foodorderingapp.databinding.FragmentOrderStatusFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class order_status_fragment : BottomSheetDialogFragment() {


    lateinit var binding : FragmentOrderStatusFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderStatusFragmentBinding.inflate(inflater,container,false)

        val OrderStatusImage = listOf(R.drawable.sademoji,R.drawable.truck,R.drawable.congrats)
        val OrderStatusName = listOf("Your order has been Canceled Successfully","Order has been taken by the driver","Congrats Your Order Placed")

        val adapter = orderStatusAdapter(OrderStatusName as MutableList<String>,OrderStatusImage as MutableList<Int>)
        binding.recyclierViewOrderStatus.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclierViewOrderStatus.adapter = adapter

        binding.backButtonOrderStatus.setOnClickListener(){
            dismiss()
        }
        return binding.root
    }

    companion object {

    }
}