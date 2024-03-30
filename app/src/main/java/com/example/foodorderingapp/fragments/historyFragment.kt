package com.example.foodorderingapp.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapters.historyAdapter
import com.example.foodorderingapp.databinding.FragmentHistoryBinding
import com.example.foodorderingapp.datamodel.orderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class historyFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: historyAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userId: String
    private var listOfOrderItem: MutableList<orderDetails> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        retrieveBuyHistory()
        return binding.root
    }

    private fun retrieveBuyHistory() {
        binding.CurrentBuyCardView.visibility = View.INVISIBLE
        userId = firebaseAuth.currentUser?.uid ?: ""

        val buyItemReference : DatabaseReference = database.reference.child("Customer").child(userId).child("Buy History")
        val sortingQuery = buyItemReference.orderByChild("currentTime")

        sortingQuery.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (buySnapshot in snapshot.children){
                    val buyHistoryItem = buySnapshot.getValue(orderDetails::class.java)
                    buyHistoryItem?.let {
                        listOfOrderItem.add(it)
                    }
                }
                listOfOrderItem.reverse()
                if (listOfOrderItem.isNotEmpty()){
                    setDataInCUrrentBuy()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun setDataInCUrrentBuy() {
        binding.CurrentBuyCardView.visibility = View.VISIBLE
        val recentOrderItem = listOfOrderItem.firstOrNull()
        recentOrderItem?.let {
            binding.CurrentBuyFoodName.text = it.foodNames?.firstOrNull()?:""
            binding.CurrentFoodPrice.text = it.foodPrice?.firstOrNull()?:""
            val image = it.foodImage?.firstOrNull()?:""
            val uri = Uri.parse(image)
            Glide.with(requireContext()).load(uri).into(binding.currentBuyFoodImage)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val PopularFoodName = listOf("burger", "sandwich", "fry", "cake")
        val PopulearFoodPrice = listOf("Rs110", "Rs60", "Rs65", "Rs60")
        val PopulearFoodImage =
            listOf(R.drawable.burger, R.drawable.sandwich, R.drawable.frys, R.drawable.cake)
        adapter = historyAdapter(
            PopularFoodName as MutableList<String>,
            PopulearFoodPrice as MutableList<String>,
            PopulearFoodImage as MutableList<Int>
        )
        binding.RecyclierHistoryFragment.layoutManager = LinearLayoutManager(requireContext())
        binding.RecyclierHistoryFragment.adapter = adapter

        binding.currentBuyFoodImage.setImageResource(R.drawable.burger)
        binding.CurrentFoodPrice.text = "Rs100"
        binding.CurrentBuyFoodName.text = "Burger"
    }

    companion object {
    }
}