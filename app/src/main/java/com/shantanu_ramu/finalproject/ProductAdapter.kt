package com.shantanu_ramu.finalproject

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.shantanu_ramu.finalproject.ui.ProductViewHolder
import com.shantanu_ramu.finalproject.ui.pojo.Products


class ProductAdapter(options: FirestoreRecyclerOptions<Products>) :
    FirestoreRecyclerAdapter<Products, ProductViewHolder>(options) {

    /*interface OnDataChanged {
        fun dataChanged()
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_result, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int, model: Products) {
        // this will fire when the user clicks a row in the recycler view
        holder.itemView.setOnClickListener {
            var web_url = "https://www.walmart.com/ip/Apple-AirPods-with-Charging-Case-Latest-Model/604342441"

                val i = Intent(Intent.ACTION_VIEW, Uri.parse(web_url))
                //startActivity()
                //startActivity(i)
        }


    }

    /*override fun onDataChanged() {
        super.onDataChanged()
        onDataChanged.dataChanged()
    }*/
}
