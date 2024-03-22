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
import com.example.foodorderingapp.datamodel.menuitemModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BottomSlideMenu : BottomSheetDialogFragment() {
    lateinit var binding:FragmentBottomSlideMenuBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems:MutableList<menuitemModel>
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

        retriveMenuItem()
    }

    private fun retriveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()

        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    val menuItem = foodSnapshot.getValue(menuitemModel::class.java)
                    menuItem?.let { menuItems.add(it) }
                }
                //set data to adapter
                setAdapter()
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    private fun setAdapter() {
        val adapter = populearAdapter(menuItems,requireContext())
        binding.reyclerViewBottomSlid.layoutManager = LinearLayoutManager(requireContext())
        binding.reyclerViewBottomSlid.adapter = adapter
    }

    companion object {
    }
}