package com.shantanu_ramu.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_result.*
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.shantanu_ramu.finalproject.ui.pojo.Products

private lateinit var productAdapter: ProductAdapter

class Result : AppCompatActivity() {

    private val TAG = javaClass.name

    private val db = FirebaseFirestore.getInstance()

//    private lateinit var pizzeriaAdapter: PizzeriaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val query: Query = db
            .collection("topSearch")
            .orderBy("price")
            .limit(50)


        /*val query: Query = (
            "logo" to "null",
            "name" to "name",
            "price" to "20$",
            "source" to "Amazon"
        )*/


        val options: FirestoreRecyclerOptions<Products> =
            FirestoreRecyclerOptions.Builder<Products>()
                .setQuery(query, Products::class.java)
                .build()

        productAdapter = ProductAdapter(options)

        res_products_view.adapter = productAdapter
        res_products_view.layoutManager = LinearLayoutManager(this)
    }



    /*override fun onStart() {
        super.onStart()
        ProductAdapter.startListening()
    }*/

    /*override fun onStop() {
        super.onStop()
        ProductAdapter.stopListening()
    }*/






/*    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)




        var web_url = "https://www.walmart.com/ip/Apple-AirPods-with-Charging-Case-Latest-Model/604342441"
        item_image.setImageResource(R.drawable.pricebaba_logo)
        item_website.setText("Amazon")
        item_price.setText("$"+"9")
        item_website.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(web_url))
            startActivity(i)
        }
        //context = (R.id.


    }*/
}