package com.example.foodorderingapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.foodorderingapp.databinding.ActivitySignInPageBinding
import com.example.foodorderingapp.datamodel.userdata
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignInPage : AppCompatActivity() {
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var name:String
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database:DatabaseReference
    private lateinit var googleSignInClient:GoogleSignInClient

    private val binding:ActivitySignInPageBinding by lazy{
        ActivitySignInPageBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        firebaseAuth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions)

        binding.GoLoginPage.setOnClickListener(){
            startActivity(Intent(this,LoginPage::class.java))
        }
        binding.CreateButton.setOnClickListener(){
            name = binding.UserNameSignPage.text.toString().trim()
            email = binding.EmailSignInPage.text.toString().trim()
            password = binding.PasswordSignInPage.text.toString().trim()

            if (name.isBlank()||email.isBlank()||password.isBlank()){
                Toast.makeText(this,"fill all the details",Toast.LENGTH_SHORT).show()
            }else{
                createAccount(email,password)
            }
        }
        binding.GoogleSignIn.setOnClickListener(){
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }
    }

    private fun createAccount(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
            task->
            if(task.isSuccessful){
                Toast.makeText(this,"account created",Toast.LENGTH_SHORT).show()
                saveUserData()
                startActivity(Intent(this,LoginPage::class.java))
                finish()
            }else{
                Toast.makeText(this,"unexpected error try again",Toast.LENGTH_SHORT).show()
                Log.d("Account", "createAccount: Failure",task.exception)
            }
        }
    }

    private fun saveUserData() {
        val user = userdata(name,email,password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        //saving data in firebase database
        database.child("Customer").child(userId).child("user details").push().setValue(user)
    }
    //launcher for google signIn
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result->
        if(result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful){
                val account:GoogleSignInAccount? = task.result
                val creditial = GoogleAuthProvider.getCredential(account?.idToken,null)
                firebaseAuth.signInWithCredential(creditial).addOnCompleteListener(){
                    task->
                    if (task.isSuccessful){
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this,"sign In Failed",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }else{
            Toast.makeText(this,"sign In Failed",Toast.LENGTH_SHORT).show()
        }
    }
}