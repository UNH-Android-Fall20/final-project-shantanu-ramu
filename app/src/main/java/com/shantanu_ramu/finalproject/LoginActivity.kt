package com.shantanu_ramu.finalproject

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.shantanu_ramu.finalproject.ui.pojo.User


class LoginActivity : AppCompatActivity() {
    private val TAG = javaClass.name
    private lateinit var signup: Button
    private lateinit var loginBtn: Button
    private lateinit var lemail : EditText
    private lateinit var lpassword :EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar
        actionBar!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val db = FirebaseFirestore.getInstance()
        var userFound = false
        var passCorrect = false
        var userVerified = false

        setContentView(R.layout.activity_login)
        signup = findViewById(R.id.signUp)
        loginBtn = findViewById(R.id.loginBtn)
        lemail = findViewById(R.id.lemail)
        lpassword = findViewById(R.id.lpassword)
        signup.setOnClickListener(){
            onSignUpClick()
        }
        var userList: ArrayList<User> = arrayListOf()

        var sp: SharedPreferences = getSharedPreferences("login_details",MODE_PRIVATE);

        if(sp.getBoolean("logged",false)){
            goToMainActivity()
        }

        db.collection("users")
            .get()
            .addOnSuccessListener {
                    result ->
                for (document in result) {

                    val user =User(document["userName"] as String, document["email"] as String
                        , document["password"] as String, document["cPassword"] as String)
                    userList.add(user)
                    Log.d(TAG, "DocumentSnapshot read with ID: " + "${document.id} => ${document.data} ")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
        loginBtn.setOnClickListener {

            if(lemail.text.toString().isEmpty()){
                lemail.error = "Please enter Email"
                lemail.requestFocus()
                return@setOnClickListener
            }else if(!Patterns.EMAIL_ADDRESS.matcher(lemail?.text.toString()).matches()){
                lemail.error = "Please enter valid email address"
                lemail.requestFocus()
                return@setOnClickListener
            }

            if(lpassword.text.toString().isEmpty()){
                lpassword.error = "Please enter Password"
                lpassword.requestFocus()
                return@setOnClickListener
            }

            for(user in userList){
               Log.d(TAG, "User loop enter")
               if(lemail.text.toString() == user.email){
                   userFound= true
                   Log.d(TAG, "User found")
                   if(lpassword.text.toString() == user.password){
                       sp.edit().putString("userEmail", user.email).commit()
                       sp.edit().putString("userName", user.userName).commit()
                       userVerified = true

                   }else{
                       passCorrect = false
                       break
                   }
               }else{
                   userFound =false;
               }
           }

            if(userVerified){
                sp.edit().putBoolean("logged",true).apply();
                Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()
                goToMainActivity()
            }else if(!userFound){
                lemail.error = "User Does not found"
                lemail.requestFocus()
                return@setOnClickListener
            }else if(!passCorrect){
                lpassword.error = "Password is inCorrect"
                lpassword.requestFocus()
                return@setOnClickListener
            }



        }
    }

    private fun onSignUpClick() {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToMainActivity() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }

}