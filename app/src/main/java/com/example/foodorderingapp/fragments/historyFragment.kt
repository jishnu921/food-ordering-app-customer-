package com.example.foodorderingapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodorderingapp.CurrentBuyDetails
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

        binding.CurrentBuyCardView.setOnClickListener(){
            seeCurrentBuy()
        }
        binding.RecivedButton.setOnClickListener{
            updateOrderStatus()
        }
        return binding.root
    }

    private fun updateOrderStatus() {
        val itemPuchKey = listOfOrderItem[0].itemPushKey
        val completeOrderReference = database.reference.child("CompletedOrder").child(itemPuchKey!!)
        completeOrderReference.child("orderRecived").setValue(true)
    }

    private fun seeCurrentBuy() {
        listOfOrderItem.firstOrNull()?.let {recentBuy ->
            val intent = Intent(requireContext(),CurrentBuyDetails::class.java)
            intent.putExtra("RecentBuyOrderItem",listOfOrderItem as ArrayList<orderDetails>)
            startActivity(intent)
        }
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
                    setDataInCurrentBuy()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun setDataInCurrentBuy() {
        binding.CurrentBuyCardView.visibility = View.VISIBLE
        val recentOrderItem = listOfOrderItem.firstOrNull()
        recentOrderItem?.let {
            binding.CurrentBuyFoodName.text = it.foodNames?.firstOrNull()?:""
            binding.CurrentFoodPrice.text = it.foodPrice?.firstOrNull()?:""
            val image = it.foodImage?.firstOrNull()?:""
            val uri = Uri.parse(image)
            Glide.with(requireContext()).load(uri).into(binding.currentBuyFoodImage)

            val isOrderAccepted = listOfOrderItem[0].orderAccepted
            if (isOrderAccepted){
                binding.OrderStatusTextView.visibility = View.VISIBLE
                binding.OrderStatusTextView.text = "order accepted"
                binding.RecivedButton.visibility = View.VISIBLE
            }

            //listOfOrderItem.reverse()
            if (listOfOrderItem.isNotEmpty()){
                setPreviousBuyRecyclerView()
            }
        }
    }

    private fun setPreviousBuyRecyclerView() {
        val oldBuyFoodName = mutableListOf<String>()
        val oldBuyFoodPrice = mutableListOf<String>()
        val oldBuyFoodImage = mutableListOf<String>()

        for (i in 1 until listOfOrderItem.size){
            listOfOrderItem[i].foodNames?.firstOrNull()?.let { oldBuyFoodName.add(it) }
            listOfOrderItem[i].foodPrice?.firstOrNull()?.let { oldBuyFoodPrice.add(it) }
            listOfOrderItem[i].foodImage?.firstOrNull()?.let { oldBuyFoodImage.add(it) }
        }
        val RecyclierView = binding.RecyclierHistoryFragment
        RecyclierView.layoutManager = LinearLayoutManager(requireContext())
        adapter = historyAdapter(oldBuyFoodName,oldBuyFoodPrice,oldBuyFoodImage,requireContext())
        RecyclierView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
    }
}