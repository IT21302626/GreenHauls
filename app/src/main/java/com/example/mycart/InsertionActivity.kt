package com.example.mycart

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etCartName: EditText
    private lateinit var etCartQuantity: EditText
    private lateinit var etCartDelivery: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etCartName = findViewById(R.id.etCartName)
        etCartQuantity = findViewById(R.id.etCartQuantity)
        etCartDelivery = findViewById(R.id.etCartDelivery)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Cart")

        btnSaveData.setOnClickListener {
            saveCartData()
        }
    }

    private fun saveCartData() {

        //getting values
        val cartName = etCartName.text.toString()
        val cartQuantity = etCartQuantity.text.toString()
        val cartDelivery =  etCartDelivery.text.toString()

        if (cartName.isEmpty()) {
            etCartName.error = "Please enter item name"
        }
        if (cartQuantity.isEmpty()) {
            etCartQuantity.error = "Please enter quantity"
        }
        if (cartDelivery.isEmpty()) {
            etCartDelivery.error = "Please enter delivery place"
        }

        val cartId = dbRef.push().key!!

        val cart = CartModel(cartId, cartName, cartQuantity, cartDelivery)

        dbRef.child(cartId).setValue(cart)
            .addOnCompleteListener {
                Toast.makeText(this, "Your Item Added to the cart successfully", Toast.LENGTH_LONG).show()

                etCartName.text.clear()
                etCartQuantity.text.clear()
                etCartDelivery.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}