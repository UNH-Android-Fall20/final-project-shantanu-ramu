package com.shantanu_ramu.finalproject

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.shantanu_ramu.finalproject.ui.pojo.Product


class ProductAdapter(options: FirestoreRecyclerOptions<Product>) :
    FirestoreRecyclerAdapter<Product, ProductAdapter.ProductViewHolder>(options){

    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_produts, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int, model: Product) {
        holder.tvProduct.text = model.productName
        holder.tvPrice.text = "$"+model.price
        holder.tvSource.text = model.source
        Glide.with(holder.ivLogo.context).load(model.imageUrl).into(holder.ivLogo)
        holder.itemView.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(model.url))
            holder.itemView.context.startActivity(intent)
        }

    }



    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
       var ivLogo: ImageView = itemView.findViewById(R.id.iv_logo)
        var tvProduct: TextView = itemView.findViewById(R.id.tvProductName)
        var tvPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
        var tvSource: TextView = itemView.findViewById(R.id.tvProductSource)
    }
}

