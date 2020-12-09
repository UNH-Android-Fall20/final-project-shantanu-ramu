package com.shantanu_ramu.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.shantanu_ramu.finalproject.ui.pojo.Product
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerViewActivity : AppCompatActivity() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collectRef: CollectionReference = db.collection("topSearch")

    private var productAdapter:ProductAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){

        val query : Query = collectRef

        val options: FirestoreRecyclerOptions<Product> =
            FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query, Product::class.java)
                .build()

        productAdapter = ProductAdapter(options)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = productAdapter
    }

    override fun onStart() {
        super.onStart()
        productAdapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        productAdapter!!.stopListening()
    }


}
