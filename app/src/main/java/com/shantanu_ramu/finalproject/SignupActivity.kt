package com.shantanu_ramu.finalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore


class SignupActivity : AppCompatActivity() {
    private lateinit var signupBtn : Button
    private lateinit var userName : EditText
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var cPassword : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        val db = FirebaseFirestore.getInstance()
        val TAG = "xyz"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signupBtn = findViewById(R.id.signUpsign)
        userName = findViewById(R.id.userName)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        cPassword = findViewById(R.id.cPassword)


        signupBtn.setOnClickListener(){
            if(userName.text.toString().isEmpty()){
                userName.error = "Please enter Name"
                userName.requestFocus()
                return@setOnClickListener
            }
            if(email.text.toString().isEmpty()){
                email.error = "Please enter Email"
                email.requestFocus()
                return@setOnClickListener
            }else if(!Patterns.EMAIL_ADDRESS.matcher(email?.text.toString()).matches()){
                email.error = "Please enter valid email address"
                email.requestFocus()
                return@setOnClickListener
            }

            if(password.text.toString().isEmpty()){
                password.error = "Please enter Password"
                password.requestFocus()
                return@setOnClickListener
            }
            if(cPassword.text.toString().isEmpty()){
                cPassword.error = "Please enter Password"
                cPassword.requestFocus()
                return@setOnClickListener
            }else if( cPassword.text.toString() != password.text.toString()){
                cPassword.error = "Password doesn't match"
                cPassword.requestFocus()
                return@setOnClickListener
            }

            val user = hashMapOf(
                "userName" to userName.text.toString(),
                "email" to email.text.toString(),
                "password" to password.text.toString(),
                "cPassword" to cPassword.text.toString()
            )


            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        TAG,
                        "DocumentSnapshot added with ID: " + documentReference.id
                    )
                }
                .addOnFailureListener { e ->
                    Log.w(
                        TAG,
                        "Error adding document",
                        e
                    )
                }



            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()


        }
    }
}