package com.shantanu_ramu.finalproject

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class Result : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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


    }
}