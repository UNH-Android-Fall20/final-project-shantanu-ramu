package com.shantanu_ramu.finalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class LoginActivity : AppCompatActivity() {

    private lateinit var signup: Button
    private lateinit var loginBtn: Button
    private lateinit var lemail : EditText
    private lateinit var lpassword :EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = FirebaseFirestore.getInstance()
        setContentView(R.layout.activity_login)
        signup = findViewById(R.id.signUp)
        loginBtn = findViewById(R.id.loginBtn)
        lemail = findViewById(R.id.lemail)
        lpassword = findViewById(R.id.lpassword)
        signup.setOnClickListener(){
            onSignUpClick()
        }
        loginBtn.setOnClickListener {
            val query: Query = db
                .collection("user")
                .orderBy("email")
                .limit(50)
            print(query)
        }
    }

    private fun onSignUpClick() {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
        finish()
    }
}