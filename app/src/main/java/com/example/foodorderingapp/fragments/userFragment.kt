package com.example.foodorderingapp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.foodorderingapp.LoginPage
import com.example.foodorderingapp.R
import com.example.foodorderingapp.databinding.FragmentHomeBinding
import com.example.foodorderingapp.databinding.FragmentUserBinding
import com.example.foodorderingapp.datamodel.userdata
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class userFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var databaseReference = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firebaseAuth = FirebaseAuth.getInstance();

        binding.logout.setOnClickListener(){
            firebaseAuth.signOut()
            startActivity(Intent(requireContext(),LoginPage::class.java))
        }
        setUserData()

        binding.SaveInformation.setOnClickListener(){
            binding.apply {
                val name = nameEditText.text.toString()
                val address = addressEditText.text.toString()
                val email = emailEditText.text.toString()
                val phone = phoneEditText.text.toString()

                updateUserData(name,address,email,phone)
            }
        }
    }

    private fun updateUserData(name: String, address: String, email: String, phone: String) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId!=null){
            val databaseReference = databaseReference.getReference("Customer").child(userId).child("sign in details")

                val userData = hashMapOf(
                "name" to name,
                "address" to address,
                "email" to email,
                "phone" to phone,
            )
            databaseReference.setValue(userData).addOnSuccessListener {
                Toast.makeText(requireContext(),"done",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(requireContext(),"unexpected error",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUserData() {
        val userId = firebaseAuth.currentUser?.uid
        if (userId!=null){
            val databaseReference = databaseReference.getReference("Customer").child(userId).child("sign in details")
            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val userProfile = snapshot.getValue(userdata::class.java)
                        if (userProfile !=null){
                            binding.apply {
                                nameEditText.setText(userProfile.name)
                                addressEditText.setText(userProfile.address)
                                phoneEditText.setText(userProfile.phone)
                                emailEditText.setText(userProfile.email)
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    companion object {
    }
}