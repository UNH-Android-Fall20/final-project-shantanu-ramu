package com.shantanu_ramu.finalproject

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_result.*

private const val TAG = "Result"

class Result : AppCompatActivity() {

/*    fun itemNotPresentFirestore() {
        Toast.makeText(this, "The Give Item is not Present in Our Database", Toast.LENGTH_LONG).show()
    }*/
    var context : Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

/*            onBackPressedDispatcher.onBackPressed()
        fun onBackPressed() {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }*/
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@Result, MainActivity::class.java)
                startActivity(intent)
            }
        }
        MainActivity().onBackPressedDispatcher.addCallback(this, callback)

        val actionBar = supportActionBar
        actionBar!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        context = this

        var web_url = "https://www.walmart.com/ip/Apple-AirPods-with-Charging-Case-Latest-Model/604342441"
        item_image.setImageResource(R.drawable.pricebaba_logo)
        item_website.setText("Amazon")
//        item_price.setText("$" + "9")
        item_website.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(web_url))
            startActivity(i)
        }
        //context = (R.id.


    }

    fun assignValues() {
/*        val ma = MainActivity()
        ma.intentToResult()*/
/*        val i = Intent(this, Result::class.java)
        startActivity(i)*/

/*        val intent = intent
        finish()
        startActivity(intent)*/

    }


    public fun append_res_value(key: String) {
//        val docRef = Utils.barValueComparision(key)
        val db = Firebase.firestore
        val docRef = db.collection("products").document(key)
        // Source can be CACHE, SERVER, or DEFAULT.
        val source = Source.CACHE

        val ma = MainActivity()

        // Get the document, forcing the SDK to use the offline cache
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Document found in the offline cache
                val document = task.result
                if (document != null) {
                    Log.d(TAG, "Cached document data: ${document.data}")
                    var price = document.data?.get("price")
                    var image_url = document.data?.get("image_url")
                    var name = document.data?.get("name")
                    var website = document.data?.get("source_url")

                    Log.d(TAG, "Price is $price")
//                    item_price.setText(price.toString())
/*                    item_image.setImageURI(image_url as Uri?)
//                    item_website.setText(website.toString())
                    item_website.setOnClickListener {
                        val i = Intent(Intent.ACTION_VIEW, Uri.parse(website as String?))
                        startActivity(i)
                        Log.d(TAG, "Price is $price")
                    }*/
//                    ma.intentToResult()
//                    assignValues()

                } else {
                    ma.itemNotPresentFirestore("Item Not Present in Database")
                }
            } else {
                ma.itemNotPresentFirestore("Item Not Present in Database")
//                Toast.makeText(MainActivity@this, "Item Not Present in Database", Toast.LENGTH_LONG).show()
                Log.d(TAG, "Cached get failed: ", task.exception)
            }
        }

/*        val data = docRef
            .get()
            .addOnSuccessListener { res ->
//                val result = res.get(key)

                Log.d(TAG, "The price of given object $key is $res")*/
    }

/*        val data_res = data.getResult()
        if (data_res != null) {
            Log.d(TAG, "The value of data is ${data_res.getString("price")}")
        }*/
}
//}
