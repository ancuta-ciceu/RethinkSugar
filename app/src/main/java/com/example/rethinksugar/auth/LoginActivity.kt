package com.example.rethinksugar.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rethinksugar.WelcomeActivity
import com.example.rethinksugar.databinding.ActivityLogin2Binding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity(){
    private lateinit var binding:ActivityLogin2Binding
    private  lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupRedirect.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener{
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                        if(it.isSuccessful){
                            val intent = Intent(this, WelcomeActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
            }
            else{
                Toast.makeText(this,"All fields must be completed", Toast.LENGTH_SHORT).show()

            }
        }
    }
/*
//pentru cand deja exista un utilizator pe acest dispozitiv conectat
    override fun onStart() {
        super.onStart()

        if(firebaseAuth.currentUser !== null){
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        }
    }
    */
}