package com.shantanu_ramu.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

private lateinit var manual_scan: Button
private lateinit var manual_entry_button : EditText
private const val TAG = "ManualEntry"
class ManualEntry : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_entry)

        manual_scan = findViewById(R.id.manual_scan1)
        manual_entry_button = findViewById(R.id.manual_entry_button)

        val res = Result()

        manual_scan.setOnClickListener {


            //var barcode_value = manual_entry.text
            //res.append_res_value(barcode_value.toString())

            var barcode_value = manual_entry_button.text
//            res.append_res_value(barcode_value.toString())

            if(manual_entry_button.text.isEmpty()){
                Toast.makeText(this, "Enter Correct Barcode Value", Toast.LENGTH_LONG).show()
            } else {
                val i = Intent(this, Result::class.java)
                i.putExtra("key", barcode_value.toString())
                startActivity(i)
            }

        }
    }
}