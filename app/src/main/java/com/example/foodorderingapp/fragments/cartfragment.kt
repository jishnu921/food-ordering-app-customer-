package com.example.foodorderingapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.FoodDetails
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapters.cartAdapter
import com.example.foodorderingapp.buy_details_filling
import com.example.foodorderingapp.databinding.FragmentCartfragmentBinding
import com.example.foodorderingapp.datamodel.cartItem
import com.example.foodorderingapp.locationSelection
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.ktx.Firebase


class cartfragment : Fragment() {
    private lateinit var binding:FragmentCartfragmentBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var FoodName:MutableList<String>
    private lateinit var FoodPrice:MutableList<String>
    private lateinit var FoodDescribtion:MutableList<String>
    private lateinit var FoodIngredients:MutableList<String>
    private lateinit var quantity:MutableList<Int>
    private lateinit var FoodImageUri : MutableList<String>
    private lateinit var CartAdapter:cartAdapter
    private lateinit var UserId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentCartfragmentBinding.inflate(inflater,container,false)

        firebaseAuth =  FirebaseAuth.getInstance()
        reteriveCartItems()
        return binding.root
    }

    private fun reteriveCartItems() {
        database =  FirebaseDatabase.getInstance()
        UserId = firebaseAuth.currentUser?.uid?:""
        val databaseReference = database.reference.child("Customer").child(UserId).child("cartItem")

        FoodName = mutableListOf()
        FoodPrice = mutableListOf()
        FoodDescribtion = mutableListOf()
        FoodIngredients = mutableListOf()
        FoodImageUri = mutableListOf()
        quantity =  mutableListOf()

        //fetch data from fireBase
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapShot in snapshot.children){
                    val cartItems =  foodSnapShot.getValue(cartItem::class.java)
                    cartItems?.foodname?.let {FoodName.add(it)}
                    cartItems?.foodPrice?.let {FoodPrice.add(it)}
                    cartItems?.foodDescription?.let {FoodDescribtion.add(it)}
                    cartItems?.foodIngradent?.let {FoodIngredients.add(it)}
                    cartItems?.foodQuanity?.let {quantity.add(it)}
                    cartItems?.foodimage?.let {FoodImageUri.add(it)}
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(),"data not featch",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setAdapter() {
        CartAdapter = cartAdapter(FoodName,FoodPrice,FoodImageUri,FoodDescribtion,FoodIngredients,quantity,requireContext())
        binding.RecyclerViewCardFragment.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.RecyclerViewCardFragment.adapter = CartAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buyCartFragment.setOnClickListener() {
            getOrderItemDetails()
            val intent = Intent(context, buy_details_filling::class.java)
            startActivity(intent)
        }
    }

    private fun getOrderItemDetails() {
        val databaseReference = database.reference.child("Customer").child(UserId).child("cartItem")
        val FoodName = mutableListOf<String>()
        val FoodImage = mutableListOf<String>()
        val FoodPrice = mutableListOf<String>()
        val FoodDescribtion = mutableListOf<String>()
        val FoodIngredient = mutableListOf<String>()
        //get item quantity
        val FoodItemQuanity = CartAdapter.getUpdatedItemQuantity()

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapShot in snapshot.children){
                    val orderItem =  foodSnapShot.getValue(cartItem::class.java)
                    orderItem?.foodimage?.let { FoodImage.add(it) }
                    orderItem?.foodname?.let { FoodName.add(it) }
                    orderItem?.foodDescription?.let { FoodDescribtion.add(it) }
                    orderItem?.foodIngradent?.let { FoodIngredient.add(it) }
                    orderItem?.foodPrice?.let { FoodPrice.add(it) }
                }
                order(FoodName,FoodPrice,FoodImage,FoodDescribtion,FoodIngredient,FoodItemQuanity)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(),"ordering failed please try again",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun order(
        foodName: MutableList<String>,
        foodPrice: MutableList<String>,
        foodImage: MutableList<String>,
        foodDescribtion: MutableList<String>,
        foodIngredient: MutableList<String>,
        foodItemQuanity: MutableList<Int>
    ) {
        if (isAdded && context!=null){
            val intent = Intent(requireContext(),buy_details_filling::class.java)
            intent.putExtra("foodName",foodName as ArrayList<String>)
            intent.putExtra("foodPrice",foodPrice as ArrayList<String>)
            intent.putExtra("foodDescribtion",foodDescribtion as ArrayList<String>)
            intent.putExtra("foodIngredient",foodIngredient as ArrayList<String>)
            intent.putExtra("foodImage", foodImage as ArrayList<String>)
            intent.putExtra("foodItemQuanity",foodItemQuanity as ArrayList<Int>)
            startActivity(intent)
        }
    }

    companion object {
    }
}