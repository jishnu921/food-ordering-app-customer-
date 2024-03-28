package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodorderingapp.databinding.ActivityBuyDetailsFillingBinding
import com.example.foodorderingapp.fragments.cartfragment
import com.example.foodorderingapp.fragments.congrats_bottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class buy_details_filling : AppCompatActivity() {

    val binding:ActivityBuyDetailsFillingBinding by lazy {
        ActivityBuyDetailsFillingBinding.inflate(layoutInflater)
    }
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var Name:String
    private lateinit var Address:String
    private lateinit var Phone:String
    private lateinit var TotalAmount:String
    private lateinit var foodName:ArrayList<String>
    private lateinit var foodPrice:ArrayList<String>
    private lateinit var foodDescribtion:ArrayList<String>
    private lateinit var foodIngredient:ArrayList<String>
    private lateinit var foodImage:ArrayList<String>
    private lateinit var foodItemQuanity:ArrayList<Int>
    private lateinit var userId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firebaseAuth =  FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference()

        //set userdata
        setUserData()

        binding.backButtonButDetails.setOnClickListener(){
            finish()
        }
        binding.placeOrderButton.setOnClickListener(){
            val orderStatus = congrats_bottomSheet()
            orderStatus.show(supportFragmentManager,"Test")
        }
    }

    private fun setUserData() {
        val user =  firebaseAuth.currentUser
        if (user!=null){
            val userId = user.uid
            val userReference = databaseReference.child("Customer").child(userId)

            userReference.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val name =  snapshot.child("name").getValue(String::class.java)?:""
                        val address = snapshot.child("address").getValue(String::class.java)?:""
                        val phone = snapshot.child("address").getValue(String::class.java)?:""
                        binding.apply {
                            nameEditText.setText(name)
                            addressEditText.setText(address)
                            phoneEditText.setText(phone)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
    }
}