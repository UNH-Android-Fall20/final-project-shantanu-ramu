package com.shantanu_ramu.finalproject

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_result.*


private const val TAG = "Result"
var price = ""
var image_url = ""
var name = ""
var website = ""
var website_name = ""
var web_url = ""
var item_website_id: Button? = null
var item_price_id: Button? = null

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


/*        var img1 = "https://i5.walmartimages.com/asr/5b7b8108-6b52-49d4-a521-88b2a49df6b2_1.eb84a7184763e078f3f4c0bdc1288e7a.jpeg"
        var image1: Bitmap? = null
        val `in` = java.net.URL(img1).openStream()
        image1 = BitmapFactory.decodeStream(`in`)*/



//        var web_url = "https://www.walmart.com/ip/Apple-AirPods-with-Charging-Case-Latest-Model/604342441"
//        var web_url = website
        item_image.setImageResource(R.drawable.pricebaba_logo)
//        item_image.setImageBitmap(image1)
//        item_website.setText("Amazon")
        item_website.setText(name)
//        item_price.setText("$" + "9")
        item_price.setText(price)
        item_website.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(web_url))
            startActivity(i)
        }
        item_price_id = findViewById(R.id.item_price)
        item_website_id = findViewById(R.id.item_website)
//        web_url = findViewById(R.id.)

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

    fun addText(price: String?, image_url: String?, name: String?, website: String?) {
        item_price.setText(price)
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
                    price = document.data?.get("price").toString()
                    image_url = document.data?.get("image_url").toString()
                    name = document.data?.get("name").toString()
                    website = document.data?.get("source_url").toString()
                    website_name = document.data?.get("source").toString()


                    Log.d(TAG, "Price is $price")
/*                    addText(
                        price, image_url as String?, name as String?,
                        website as String?
                    )*/



                    item_price_id?.setText("$" + price.toString())
                    web_url = website
                    item_website_id?.setText(website_name.toString())




/*                    item_image.setImageURI(image_url as Uri?)
                    item_website_id.setText(website.toString())
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
