package com.fourcade7.mursal.Adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fourcade7.mursal.MainActivity5
import com.fourcade7.mursal.Models.Category
import com.fourcade7.mursal.R
import com.fourcade7.mursal.databinding.RecyclerviewItem3Binding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class CategoryAdapterAdmin(
    val context:Context,
    val arraylistcateoryadmin: ArrayList<Category>,


): RecyclerView.Adapter<CategoryAdapterAdmin.CategoryAdminViewholder>() {

   val databaseReference: DatabaseReference= FirebaseDatabase.getInstance().getReference().child("Categories")
    val storageReference: StorageReference= FirebaseStorage.getInstance().getReference().child("Categories")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdminViewholder {

        return CategoryAdminViewholder(RecyclerviewItem3Binding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: CategoryAdminViewholder, position: Int) {
        holder.binding.textview12.setText(arraylistcateoryadmin.get(position).name)
        Glide.with(context).load(arraylistcateoryadmin.get(position).imageurl).fitCenter().placeholder(com.fourcade7.mursal.R.drawable.img).centerCrop().into(holder.binding.imageview12)
        holder.binding.linearlay2.setOnLongClickListener {

            val alertdialog=AlertDialog.Builder(context)
            alertdialog.setTitle("Bu ketegoriyani o`chirasizmi ?")
            alertdialog.setMessage(arraylistcateoryadmin.get(position).name)
            alertdialog.setIcon(R.drawable.ic_round_delete_24)

            alertdialog.setPositiveButton("Xa"){_,_->
                databaseReference.child(arraylistcateoryadmin.get(position).uploadkey).removeValue()
                //val storageReferencedel=storageReference.
            }
            alertdialog.setNegativeButton("Yo`q"){_,_->

            }
            alertdialog.setNeutralButton("Orqaga"){_,_->

            }
            alertdialog.create()
            alertdialog.show()
            return@setOnLongClickListener true
        }

        holder.binding.linearlay2.setOnClickListener {
            val intent=Intent(context, MainActivity5::class.java).apply {
                putExtra("name",arraylistcateoryadmin.get(position).name)
            }
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return arraylistcateoryadmin.size
    }
    class CategoryAdminViewholder(val binding: RecyclerviewItem3Binding): RecyclerView.ViewHolder(binding.root)



}