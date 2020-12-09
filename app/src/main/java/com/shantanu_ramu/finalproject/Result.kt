package com.shantanu_ramu.finalproject

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shantanu_ramu.finalproject.ui.pojo.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_result.*
import java.net.URL


private const val TAG = "Result"

var product : Product = Product()


var price = ""
var image_url = ""
var name = ""
var productName: TextView? = null
var website = ""
var website_name = ""
var web_url = ""
var item_website_id: Button? = null
var item_price_id: Button? = null


class Result : AppCompatActivity() {

    var context : Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val key : String = intent.extras?.getString("key","") ?: "718103230759"
        append_res_value(key)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@Result, MainActivity::class.java)
                startActivity(intent)
            }
        }
        MainActivity().onBackPressedDispatcher.addCallback(this, callback)

        val actionBar = supportActionBar
        actionBar!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))





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
        productName = findViewById(R.id.item_name)
//        web_url = findViewById(R.id.)

        //context = (R.id.



    }



    public fun append_res_value(key: String) {


//        val docRef = Utils.barValueComparision(key)
        val db = Firebase.firestore
        val docRef = db.collection("products").document(key)
        val ma = MainActivity()
        var secondSource : Boolean = false
        val source2 : TextView = findViewById(R.id.secondSource)
        val avilable : TextView = findViewById(R.id.avilable)

        // Get the document, forcing the SDK to use the offline cache
        docRef.get().addOnCompleteListener { task ->
//            val userInfo = task.toObject(Product::class.java)
            if (task.isSuccessful) {


                // Document found in the offline cache
                val document = task.result
                if (document != null) {

                    if (document.data != null) {
                        Log.d(TAG, "Cached document data: ${document.data}")

                        /*product = document.toObject(Product::class.java)!!*/
                        product.price = document.data?.get("price").toString()
                        product.imageUrl = document.data?.get("imageUrl").toString()
                        product.productName = document.data?.get("productName").toString()
                        product.url = document.data?.get("url").toString()
                        product.source = document.data?.get("source").toString()
                        secondSource = document.data?.get("secondSource") as Boolean
                        if(secondSource){
                            callForSecondSource(key)
                        }else{
                            source2.text = " "
                            avilable.text = " "
                        }

                        productFound()


                    } else {
                        productNotFound()
            //                    ma.itemNotPresentFirestore("Item Not Present in Database")
            //                    Toast.makeText(MainActivity@this, "Item Not Present in Database", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                productNotFound()
//                ma.itemNotPresentFirestore("Item Not Present in Database")
//                Toast.makeText(MainActivity@this, "Item Not Present in Database", Toast.LENGTH_LONG).show()
                Log.d(TAG, "Cached get failed: ", task.exception)
            }
//                  need to comment it
                    Log.d(TAG, "Cached document data: ${document.data}")
                    price = document.data?.get("price").toString()
                    image_url = document.data?.get("imageUrl").toString()
                    name = document.data?.get("productName").toString()
                    website = document.data?.get("url").toString()
                    website_name = document.data?.get("source").toString()


        }.addOnFailureListener {
            productNotFound()
//            Toast.makeText(this, "Item Not Present in Database", Toast.LENGTH_LONG).show()
        }

    }

    private fun productFound(){
        context = this

        val prodImage : ImageView = findViewById(R.id.item_image)
        val itemName : TextView = findViewById(R.id.item_name)
        val itemPrice : TextView = findViewById(R.id.item_price)
        val itemWebsite : TextView = findViewById(R.id.item_website)


        Glide.with(prodImage.context).load(product.imageUrl).into(prodImage)
        item_website.text = product.source
        item_price.text = product.price
        prodImage.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(product.url))
            startActivity(i)
        }
        itemName.text = product.productName.toString()
        itemPrice?.text = "$" + product.price.toString()
        itemWebsite?.text = "Available in "+product.source.toString()
    }

                    item_price_id?.setText("$" + price.toString())
                    web_url = website
                    item_website_id?.setText(website_name.toString())
                    productName?.setText(name.toString())


    private fun productNotFound(){
        val prodImage : ImageView = findViewById(R.id.item_image)
        val itemName : TextView = findViewById(R.id.item_name)
        val itemPrice : TextView = findViewById(R.id.item_price)
        val itemWebsite : TextView = findViewById(R.id.item_website)
        val source2 : TextView = findViewById(R.id.secondSource)
        val avilable : TextView = findViewById(R.id.avilable)

        Glide.with(prodImage.context)
            .load("https://www.mag-manager.com/wp-content/gallery/magento-product-export-to-ebay/no-magento-product-found.jpg")
            .into(prodImage)

        itemName.text = "No Product Available"
        itemPrice.text = " "
        itemWebsite.text = " "
        source2.text = " "
        avilable.text = " "
    }

    private fun callForSecondSource(key: String){

        val db = Firebase.firestore
        val docRef = db.collection("source").document(key)
        val source2 : TextView = findViewById(R.id.secondSource)
        val avilable : TextView = findViewById(R.id.avilable)
        var price : String = ""
        var source: String = ""
        var url: String = ""

        docRef.get().addOnCompleteListener { task ->
//            val userInfo = task.toObject(Product::class.java)
            if (task.isSuccessful) {
                val document = task.result
                if (document != null) {
                    price = document.data?.get("price").toString()
                    url = document.data?.get("url").toString()
                    source = document.data?.get("source").toString()
                    avilable.text = " ".plus("Other Deals")
                    source2.text = "$".plus(price).plus("            ")
                        .plus("Available in ").plus(source)
                    source2.setOnClickListener{
                        val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(i)
                    }
                }

            }}

    }
}
