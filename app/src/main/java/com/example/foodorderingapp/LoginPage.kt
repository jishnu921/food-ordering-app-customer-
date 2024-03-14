package com.example.foodorderingapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.foodorderingapp.databinding.ActivityLoginPageBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase

class LoginPage : AppCompatActivity() {
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseDatabase: FirebaseDatabase
    private lateinit var googleSignInClient: GoogleSignInClient

    private val binding: ActivityLoginPageBinding by lazy {
        ActivityLoginPageBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        firebaseAuth = FirebaseAuth.getInstance()
        databaseDatabase = FirebaseDatabase.getInstance()
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions)


        binding.GoSignUp.setOnClickListener(){
            startActivity(Intent(this,SignInPage::class.java))
        }
        binding.LoginButton.setOnClickListener(){
            email = binding.EmailLoginPage.text.toString().trim()
            password = binding.PasswordLoginPage.text.toString().trim()

            if (email.isBlank()||password.isBlank()){
                Toast.makeText(this,"fill all the details",Toast.LENGTH_SHORT).show()
            }else{
                loginUser()
            }
        }
        binding.googleButton.setOnClickListener(){
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }
    }

    private fun loginUser() {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val user = firebaseAuth.currentUser
                Toast.makeText(this,"login successful",Toast.LENGTH_SHORT).show()
                updateUi(user)
            }else{
                Toast.makeText(this,"no account with the given details",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUi(user: FirebaseUser?) {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        if(result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful){
                val account: GoogleSignInAccount? = task.result
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