package com.fourcade7.mursal.Adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fourcade7.mursal.MainActivity2
import com.fourcade7.mursal.MainActivity5
import com.fourcade7.mursal.Models.Product
import com.fourcade7.mursal.R
import com.fourcade7.mursal.databinding.RecyclerviewItem1Binding
import com.fourcade7.mursal.databinding.RecyclerviewItem2Binding
import com.fourcade7.mursal.databinding.RecyclerviewItem4Binding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class ProductAdapterAdmin(
    val context:Context,
    val arraylistproducts:ArrayList<Product>

): RecyclerView.Adapter<ProductAdapterAdmin.ProductViewholder>() {


    val activity5:MainActivity5=context as MainActivity5
    val databaseReference= FirebaseDatabase.getInstance().getReference().child("Products").child(activity5.categoryname)
    val databaseReference2= FirebaseDatabase.getInstance().getReference().child("AllProducts")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewholder {

        return ProductViewholder(RecyclerviewItem4Binding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: ProductViewholder, position: Int) {
        Glide.with(context).load(arraylistproducts.get(position).imageurl).fitCenter().placeholder(com.fourcade7.mursal.R.drawable.img).centerCrop().into(holder.binding.imageview22)
        holder.binding.textview22.setText(arraylistproducts.get(position).name)
        holder.binding.textview33.setText(arraylistproducts.get(position).description)
        holder.binding.textview44.setText(arraylistproducts.get(position).price)
        holder.binding.relativelay2.setOnLongClickListener {
            val alertdialog= AlertDialog.Builder(context)
            alertdialog.setTitle("Bu mahsulotni o`chirasizmi ?")
            alertdialog.setIcon(R.drawable.ic_round_delete_24)
            alertdialog.setMessage(arraylistproducts.get(position).name)
            alertdialog.setPositiveButton("Xa"){_,_->
                databaseReference.child(arraylistproducts.get(position).uploadkey).removeValue()
                databaseReference2.child(arraylistproducts.get(position).uploadkey).removeValue()
                //val storageReferencedel=storageReference.
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
        return arraylistproducts.size
    }
    class ProductViewholder(val binding: RecyclerviewItem4Binding): RecyclerView.ViewHolder(binding.root)



}