package com.example.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.foodorderingapp.databinding.ActivityBuyDetailsFillingBinding
import com.example.foodorderingapp.datamodel.orderDetails
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

        //get user details from fire base
        val intent = intent
        foodName = intent.getStringArrayListExtra("foodName") as ArrayList<String>
        foodPrice = intent.getStringArrayListExtra("foodPrice") as ArrayList<String>
        foodDescribtion = intent.getStringArrayListExtra("foodDescribtion") as ArrayList<String>
        foodIngredient = intent.getStringArrayListExtra("foodIngredient") as ArrayList<String>
        foodImage = intent.getStringArrayListExtra("foodImage") as ArrayList<String>
        foodItemQuanity = intent.getIntegerArrayListExtra("foodItemQuanity") as ArrayList<Int>

        //setting total amount
        TotalAmount = "Rs"+calculateTotalAmount().toString()
        binding.totalAmountEditText.isEnabled = false
        binding.totalAmountEditText.text = TotalAmount

        binding.backButtonButDetails.setOnClickListener(){
            finish()
        }
        binding.placeOrderButton.setOnClickListener(){
            Name = binding.nameEditText.text.toString().trim()
            Phone = binding.phoneEditText.text.toString().trim()
            Address = binding.addressEditText.text.toString().trim()

            if (Name.isBlank()||Phone.isBlank()||Address.isBlank()){
                Toast.makeText(this,"Fill out the details",Toast.LENGTH_SHORT).show()
            }else{
                placeOrder()
            }
        }
    }

    private fun placeOrder() {
        userId = firebaseAuth.currentUser?.uid?:""
        val time = System.currentTimeMillis()
        val itemPushKey = databaseReference.child("order details").push().key
        val orderDetails = orderDetails(userId,Name,Address,TotalAmount,Phone,itemPushKey,foodName,foodImage,foodPrice,foodItemQuanity,false,false,time)
        val orderReference = databaseReference.child("order details").child(itemPushKey!!)
        orderReference.setValue(orderDetails).addOnSuccessListener {
            val orderStatus = congrats_bottomSheet()
            orderStatus.show(supportFragmentManager,"Test" )
            removeItemFromCart()
            addOrderToHistory(orderDetails)
        }.addOnFailureListener{
            Toast.makeText(this,"error",Toast.LENGTH_SHORT).show()
        }
    }

    private fun addOrderToHistory(orderDetails: orderDetails){
        databaseReference.child("Customer").child(userId).child("Buy History")
            .child(orderDetails.itemPushKey!!).setValue(orderDetails).addOnSuccessListener {

            }
    }

    private fun removeItemFromCart() {
        val cartItemDatabaseReference = databaseReference.child("Customer").child(userId).child("cartItem")
        cartItemDatabaseReference.removeValue()
    }

    private fun calculateTotalAmount(): Int {
        var totalAmount = 0
        for (i in 0 until foodPrice.size){
            var price = foodPrice[i].toInt()
            var quantity = foodItemQuanity[i]
            totalAmount+= price * quantity
        }
        return totalAmount
    }

    private fun setUserData() {
        val user =  firebaseAuth.currentUser
        if (user!=null){
            val userId = user.uid
            val userReference = databaseReference.child("Customer").child(userId).child("sign in details")

            userReference.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val name =  snapshot.child("name").getValue(String::class.java)?:""
                        val address = snapshot.child("address").getValue(String::class.java)?:""
                        val phone = snapshot.child("phone").getValue(String::class.java)?:""
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