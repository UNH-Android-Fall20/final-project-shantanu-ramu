package com.shantanu_ramu.finalproject.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shantanu_ramu.finalproject.R

class ProductViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    var res_image: ImageView = view.findViewById(R.id.res_image)
    var res_name: TextView = view.findViewById(R.id.res_name)
    var res_price: TextView = view.findViewById(R.id.res_price)
    var res_source: TextView = view.findViewById(R.id.res_source)
}