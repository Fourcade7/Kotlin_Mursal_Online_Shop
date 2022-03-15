package com.fourcade7.mursal.Adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.fourcade7.mursal.Models.Order
import com.fourcade7.mursal.R
import com.fourcade7.mursal.databinding.RecyclerviewItem5Binding
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class OrderAdapter(
    val context: Context,
    val arrayListorder: ArrayList<Order>
): RecyclerView.Adapter<OrderAdapter.OrderViewholder>() {

   val databaseReference= FirebaseDatabase.getInstance().getReference().child("Orders")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewholder {
        val binding=RecyclerviewItem5Binding.inflate(LayoutInflater.from(context),parent,false)
        return OrderViewholder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewholder, position: Int) {
        Picasso.get().load(arrayListorder.get(position).imageurl).fit().centerCrop().into(holder.binding.imageview223)
       holder.binding.textview22.setText(arrayListorder.get(position).ism_familya)
        holder.binding.textview33.setText(arrayListorder.get(position).ordername+" , bo`yi: "+arrayListorder.get(position).boyi+" yelka: "+arrayListorder.get(position).yelka+" yengi: "+arrayListorder.get(position).yengi+" yoqa: "+arrayListorder.get(position).yoqa+" ko`krak aylanasi: "+arrayListorder.get(position).kokrak_aylanasi+" orqa qismi: "+arrayListorder.get(position).orqa_qismi+" bel qismi "+arrayListorder.get(position).bel_qismi)
        holder.binding.textview44.setText(arrayListorder.get(position).price+" , "+arrayListorder.get(position).telefon)
        holder.binding.relativelay23.setOnLongClickListener {
            val alertdialog= AlertDialog.Builder(context)
            alertdialog.setTitle("Bu buyurtmani o`chirasizmi ?")
            alertdialog.setIcon(R.drawable.ic_round_delete_24)
            alertdialog.setMessage(arrayListorder.get(position).ordername)
            alertdialog.setPositiveButton("Xa"){_,_->
                databaseReference.child(arrayListorder.get(position).uploadkey).removeValue()

                Toast.makeText(context,"O`chirildi", Toast.LENGTH_SHORT).show()

            }
            alertdialog.setNegativeButton("Yo`q"){_,_->

            }
            alertdialog.setNeutralButton("Orqaga"){_,_->

            }
            alertdialog.create()
            alertdialog.show()


            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return arrayListorder.size
    }

    class OrderViewholder(val binding: RecyclerviewItem5Binding) :RecyclerView.ViewHolder(binding.root)
}