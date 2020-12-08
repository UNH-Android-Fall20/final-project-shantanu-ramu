package com.shantanu_ramu.finalproject

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


object Utils {
    fun showToast(mContext: Context?, message: String?) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
    }

    private const val TAG = "Utils"

    fun datahand(barValue: String?) {

        Log.d(TAG, barValue.toString())
    }

    fun getFireData() {
        //firestore implementation for testing

        val db = Firebase.firestore

        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
//                    let json = document.data()

                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

    }

    fun  processBar(res:DocumentSnapshot, key: String) {
        val result = res.get(key)
//        val result = res.get(key.toInt())
        Log.d(TAG, "The price of given object is $result")
/*        val Result = Result()
        Result.append_res_value(result.toString())*/

    }

    fun barValueComparision(key:String): DocumentReference {
        val db = Firebase.firestore
        val docRef = db.collection("products").document("barcode")
        val data = docRef
            .get()
            .addOnSuccessListener { res ->
                processBar(res, key)
            }

    return docRef
    }
}