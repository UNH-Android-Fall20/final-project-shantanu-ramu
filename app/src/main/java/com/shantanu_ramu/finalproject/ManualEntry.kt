package com.shantanu_ramu.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

private lateinit var manual_scan: Button
private lateinit var manual_entry : EditText
class ManualEntry : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_entry)

        manual_scan = findViewById(R.id.manual_scan1)
        manual_entry = findViewById(R.id.manual_entry_button)

        val res = Result()

        manual_scan.setOnClickListener {
            var barcode_value = manual_entry.text
            res.append_res_value(barcode_value.toString())
            val i = Intent(this, Result::class.java)
            startActivity(i)
        }
    }
}