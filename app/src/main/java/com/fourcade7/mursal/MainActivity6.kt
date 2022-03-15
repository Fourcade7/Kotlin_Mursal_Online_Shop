package com.fourcade7.mursal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fourcade7.mursal.Adapters.OrderAdapter
import com.fourcade7.mursal.Adapters.ProductAdapterAdmin
import com.fourcade7.mursal.Models.Order
import com.fourcade7.mursal.Models.Product
import com.fourcade7.mursal.databinding.ActivityMain6Binding
import com.google.firebase.database.*

class MainActivity6 : AppCompatActivity() {
    lateinit var binding: ActivityMain6Binding
    lateinit var databaseReference: DatabaseReference
    lateinit var orderAdapter: OrderAdapter

    val arraylistorder=ArrayList<Order>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain6Binding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Orders")

        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                arraylistorder.clear()
                for (datasnapshot in snapshot.children){
                    val category=datasnapshot.getValue(Order::class.java)
                    arraylistorder.add(category!!)
                }
                orderAdapter= OrderAdapter(this@MainActivity6,arraylistorder)
                binding.recyclerview5.apply {
                    layoutManager=
                        LinearLayoutManager(this@MainActivity6, LinearLayoutManager.VERTICAL,false)
                    adapter=orderAdapter
                }



            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }
}