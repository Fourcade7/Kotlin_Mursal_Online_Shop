package com.fourcade7.mursal

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fourcade7.mursal.Adapters.CategoryAdapter
import com.fourcade7.mursal.Adapters.CategoryAdapterAdmin
import com.fourcade7.mursal.Adapters.ProductAdapter
import com.fourcade7.mursal.Models.Category
import com.fourcade7.mursal.Models.Product
import com.fourcade7.mursal.databinding.ActivityMainBinding
import com.fourcade7.mursal.databinding.BottomsheetDialogBinding
import com.google.android.gms.common.util.ArrayUtils.contains
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    lateinit var categoryAdapter:CategoryAdapter


    lateinit var productAdapter: ProductAdapter
    val arraylistallproducts=ArrayList<Product>()

    val arrayListCategorys=ArrayList<Category>()
    lateinit var databaseReference: DatabaseReference
    lateinit var databaseReference2: DatabaseReference
    lateinit var storageReference: StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
//        window.statusBarColor= Color.parseColor("#FFFFFF")
//        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Categories")
        storageReference= FirebaseStorage.getInstance().getReference().child("Categories")
        databaseReference2= FirebaseDatabase.getInstance().getReference().child("AllProducts")


        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                arrayListCategorys.clear()
                for (datasnapshot in snapshot.children){
                    val category=datasnapshot.getValue(Category::class.java)
                    arrayListCategorys.add(category!!)
                }
                categoryAdapter= CategoryAdapter(this@MainActivity,arrayListCategorys)
                binding.recyclerview1.apply {
                    layoutManager=LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL,false)
                    adapter=categoryAdapter
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        databaseReference2.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                arraylistallproducts.clear()
                for (datasnapshot in snapshot.children){
                    val category=datasnapshot.getValue(Product::class.java)
                    arraylistallproducts.add(category!!)
                }
                productAdapter= ProductAdapter(this@MainActivity,arraylistallproducts)
                val mlayoutManager2=LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                binding.recyclerview2.apply {
                    layoutManager=mlayoutManager2
                    adapter=productAdapter
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        binding.imageview13.setOnLongClickListener() {

            val bottomsheetDialogBinding=BottomsheetDialogBinding.inflate(LayoutInflater.from(this@MainActivity))

            val alertdialog= AlertDialog.Builder(this@MainActivity)
            alertdialog.setTitle("Admin")
            alertdialog.setView(bottomsheetDialogBinding.root)
            alertdialog.setIcon(R.drawable.ic_baseline_security_24)

            alertdialog.setPositiveButton("ok"){_,_->


                if (bottomsheetDialogBinding.edittextpassword.text.toString().equals("")){
                    startActivity(Intent(this@MainActivity,MainActivity7::class.java))
                }else{
                    Toast.makeText(this@MainActivity,"Xato", Toast.LENGTH_SHORT).show()
                }





            }

            alertdialog.create()
            alertdialog.show()
            return@setOnLongClickListener true
        }


        binding.edittextseatch.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }


            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }
        })



    }
    fun filter(text:String){
        val searcharraylist=ArrayList<Category>()

            for (item:Category in arrayListCategorys){
                if (item.name.toLowerCase().contains(text.toLowerCase())){
                    searcharraylist.add(item)
                }
            }
            categoryAdapter?.let {
                it.filterList(searcharraylist)
            }

        val searcharraylist2=ArrayList<Product>()
        for (item:Product in arraylistallproducts){
            if (item.name.toLowerCase().contains(text.toLowerCase()) || item.description.toLowerCase().contains(text.toLowerCase()) || item.price.toLowerCase().contains(text.toLowerCase())){
                searcharraylist2.add(item)
            }
        }
        productAdapter?.let {
            it.filterList(searcharraylist2)
        }



    }

    fun setupcategoryproduct(categoryname:String){

        databaseReference2= FirebaseDatabase.getInstance().getReference().child("Products").child(categoryname)

        databaseReference2.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                arraylistallproducts.clear()
                for (datasnapshot in snapshot.children){
                    val category=datasnapshot.getValue(Product::class.java)
                    arraylistallproducts.add(category!!)
                }
                productAdapter= ProductAdapter(this@MainActivity,arraylistallproducts)
                val mlayoutManager2=LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                binding.recyclerview2.apply {
                    layoutManager=mlayoutManager2
                    adapter=productAdapter
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}