package com.fourcade7.mursal

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fourcade7.mursal.Adapters.CategoryAdapterAdmin
import com.fourcade7.mursal.Adapters.ProductAdapter
import com.fourcade7.mursal.Adapters.ProductAdapterAdmin
import com.fourcade7.mursal.Models.Category
import com.fourcade7.mursal.Models.Product
import com.fourcade7.mursal.databinding.ActivityMain5Binding
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class MainActivity5 : AppCompatActivity() {

    lateinit var uri: Uri
    lateinit var databaseReference: DatabaseReference
    lateinit var databaseReference2: DatabaseReference
    lateinit var storageReference: StorageReference

    lateinit var categoryname:String

    val arrayListProducts=ArrayList<Product>()
    lateinit var productAdapterAdmin: ProductAdapterAdmin
    var a=true

    lateinit var binding:ActivityMain5Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain5Binding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)


        val intent=intent
        categoryname=intent.getStringExtra("name")!!

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Products").child(categoryname!!)
        databaseReference2= FirebaseDatabase.getInstance().getReference().child("AllProducts")
        storageReference= FirebaseStorage.getInstance().getReference().child("Products")
        binding.textview13.setText("$categoryname")


        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                arrayListProducts.clear()
                for (datasnapshot in snapshot.children){
                    val category=datasnapshot.getValue(Product::class.java)
                    arrayListProducts.add(category!!)
                }
                productAdapterAdmin= ProductAdapterAdmin(this@MainActivity5,arrayListProducts)
                binding.recyclerview4.apply {
                    layoutManager=
                        LinearLayoutManager(this@MainActivity5, LinearLayoutManager.VERTICAL,false)
                    adapter=productAdapterAdmin
                }



            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        binding.buttonpickimage2.setOnClickListener {
            openFileChooser()
        }

        binding.buttonaddproduct.setOnClickListener {
            binding.progressbar2.visibility=View.VISIBLE
            uploadimage()
        }

        binding.imageviewaddproduct.setOnClickListener {
            if (a){
                binding.linearlay4.visibility=View.VISIBLE
                a=false
            }else{
                binding.linearlay4.visibility=View.GONE
                a=true

            }
        }

    }



    fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.data != null) {
            uri = data.data!!
            Picasso.get().load(uri).fit().centerCrop().into(binding.imageview14)
        }
    }

    private fun getFileExtension(uri: Uri): String? {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

    fun uploadimage() {
        if (uri != null) {
            val filereference: StorageReference = storageReference.child(
                System.currentTimeMillis().toString() + "." + getFileExtension(uri)
            )
            filereference.putFile(uri)
                .addOnSuccessListener { filereference.downloadUrl.addOnSuccessListener {
                    var uploadkey=databaseReference.push().key.toString()
                    val product: Product =Product(binding.edittext10.text.toString(),it.toString(),binding.edittext11.text.toString(),binding.edittext12.text.toString(),uploadkey)
                    databaseReference.child(uploadkey).setValue(product)
                    binding.progressbar2.visibility= View.GONE
                    binding.linearlay4.visibility=View.GONE
                    a=true


                    val product2: Product =Product(binding.edittext10.text.toString(),it.toString(),binding.edittext11.text.toString(),binding.edittext12.text.toString(),uploadkey)
                    databaseReference2.child(uploadkey).setValue(product2)
                    Toast.makeText(this@MainActivity5,"Yangi mahsulot qo`shildi", Toast.LENGTH_SHORT).show()
                    binding.edittext10.setText("")
                    binding.imageview14.setImageResource(R.drawable.placeholderimage)
                } }
        }


    }
}