package com.shantanu_ramu.finalproject.ui.home


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.shantanu_ramu.finalproject.R
import com.shantanu_ramu.finalproject.Result


class HomeFragment : Fragment() {
    private lateinit var loginButton: Button
/*    private lateinit var manual_scan: Button
    private lateinit var manual_entry : EditText*/
    private lateinit var homeViewModel: HomeViewModel


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val textView: TextView = root.findViewById(R.id.manual_text1)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
/*

        manual_scan = root.findViewById(R.id.manual_scan)
        manual_entry = root.findViewById(R.id.manual_entry)

        val res = Result()

        manual_scan.setOnClickListener {
            var barcode_value = manual_entry.text
            res.append_res_value(barcode_value.toString())
*/
/*            val i = Intent(this@HomeFragment, Result::class.java)
            startActivity(i)*//*

        }
*/


        return root
    }

    private fun loginScreen(){

    }
}