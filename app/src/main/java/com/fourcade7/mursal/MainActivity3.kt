package com.fourcade7.mursal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.fourcade7.mursal.Models.Order
import com.fourcade7.mursal.databinding.ActivityMain3Binding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity3 : AppCompatActivity() {
    lateinit var databaseReference: DatabaseReference
    val arrraylistorders=ArrayList<Order>()
    lateinit var binding: ActivityMain3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val intent=intent
        var image=intent.getStringExtra("img")!!
        var name=intent.getStringExtra("name")!!
        var price=intent.getStringExtra("price")!!

        databaseReference=FirebaseDatabase.getInstance().getReference().child("Orders")


        binding.buttonorder2.setOnClickListener {
            var uploadkey=databaseReference.push().key.toString()
            val order=Order(image,name,binding.edittext1.text.toString(),binding.edittext2.text.toString(),binding.edittext3.text.toString(),binding.edittext4.text.toString(),binding.edittext5.text.toString(),binding.edittext6.text.toString(),binding.edittext77.text.toString(),binding.edittext7.text.toString(),binding.edittext8.text.toString(),price,uploadkey)
            databaseReference.child(uploadkey).setValue(order)
            Toast.makeText(this@MainActivity3,"Buyurtma berildi", Toast.LENGTH_SHORT).show()
            finish()


        }

    }
}