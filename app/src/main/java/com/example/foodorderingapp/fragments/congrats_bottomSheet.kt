package com.example.foodorderingapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodorderingapp.MainActivity
import com.example.foodorderingapp.R
import com.example.foodorderingapp.databinding.FragmentCongratsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class congrats_bottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentCongratsBottomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCongratsBottomSheetBinding.inflate(inflater,container,false)

        binding.GohomeCongratsFragment.setOnClickListener(){
            dismiss()
            startActivity(Intent(context,MainActivity::class.java))
        }
        return binding.root
    }

    companion object {
    }
}