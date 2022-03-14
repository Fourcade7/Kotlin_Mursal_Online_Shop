package com.fourcade7.mursal.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.R
import com.bumptech.glide.Glide
import com.fourcade7.mursal.MainActivity
import com.fourcade7.mursal.Models.Category
import com.fourcade7.mursal.databinding.RecyclerviewItem1Binding
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class CategoryAdapter(
    val context:Context,
    val arrayListCategorys:ArrayList<Category>


): RecyclerView.Adapter<CategoryAdapter.CategoryViewholder>() {
    val mainActivity:MainActivity=context as MainActivity


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewholder {

        return CategoryViewholder(RecyclerviewItem1Binding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: CategoryViewholder, position: Int) {
       Picasso.get().load(arrayListCategorys.get(position).imageurl).fit().placeholder(com.fourcade7.mursal.R.drawable.img).centerCrop().networkPolicy(NetworkPolicy.OFFLINE).into(holder.binding.imageview1)
       //Glide.with(context).load(arrayListCategorys.get(position).imageurl).placeholder(com.fourcade7.mursal.R.drawable.loading).into(holder.binding.imageview1)

        holder.binding.textview1.setText(arrayListCategorys.get(position).name)
        holder.binding.linearlay.setOnClickListener {
            mainActivity.setupcategoryproduct(arrayListCategorys.get(position).name)
            //Toast.makeText(context,arrayListCategorys.get(position).name, Toast.LENGTH_SHORT).show()

        }
    }

    override fun getItemCount(): Int {
        return arrayListCategorys.size
    }
    class CategoryViewholder(val binding: RecyclerviewItem1Binding): RecyclerView.ViewHolder(binding.root)



}