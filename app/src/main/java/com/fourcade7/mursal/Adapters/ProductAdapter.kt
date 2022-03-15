package com.fourcade7.mursal.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fourcade7.mursal.MainActivity2
import com.fourcade7.mursal.Models.Category
import com.fourcade7.mursal.Models.Product
import com.fourcade7.mursal.databinding.RecyclerviewItem1Binding
import com.fourcade7.mursal.databinding.RecyclerviewItem2Binding
import com.squareup.picasso.Picasso

class ProductAdapter(
    val context:Context,
    var arraylistallproducts: ArrayList<Product>,


    ): RecyclerView.Adapter<ProductAdapter.ProductViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewholder {

        return ProductViewholder(RecyclerviewItem2Binding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: ProductViewholder, position: Int) {

        Picasso.get().load(arraylistallproducts.get(position).imageurl).fit().placeholder(com.fourcade7.mursal.R.drawable.img).centerCrop().into(holder.binding.imageview2)


        holder.binding.textview2.setText(arraylistallproducts.get(position).name)
        holder.binding.textview3.setText(arraylistallproducts.get(position).description)
        holder.binding.textview4.setText(arraylistallproducts.get(position).price)
        holder.binding.relativelay1.setOnClickListener {
            val intent=Intent(context,MainActivity2::class.java).apply {
                putExtra("img",arraylistallproducts.get(position).imageurl)
                putExtra("name",arraylistallproducts.get(position).name)
                putExtra("description",arraylistallproducts.get(position).description)
                putExtra("price",arraylistallproducts.get(position).price)
            }
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return arraylistallproducts.size
    }

    fun filterList(filteredList: ArrayList<Product>) {
        arraylistallproducts = filteredList
        notifyDataSetChanged()
    }
    class ProductViewholder(val binding: RecyclerviewItem2Binding): RecyclerView.ViewHolder(binding.root)



}