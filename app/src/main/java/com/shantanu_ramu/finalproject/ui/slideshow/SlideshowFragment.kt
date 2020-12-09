package com.shantanu_ramu.finalproject.ui.slideshow

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

class SlideshowFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)
        val barcode: EditText = root.findViewById(R.id.manual_entry_text)
        /*slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        val submit : Button = root.findViewById(R.id.manual_scan)
        return root
    }
}