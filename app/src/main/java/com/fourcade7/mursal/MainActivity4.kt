package com.fourcade7.mursal

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.fourcade7.mursal.databinding.ActivityMain4Binding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

import android.content.Intent
import android.webkit.MimeTypeMap

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fourcade7.mursal.Adapters.CategoryAdapterAdmin
import com.fourcade7.mursal.Models.Category
import com.squareup.picasso.Picasso
import com.google.firebase.database.*


class MainActivity4 : AppCompatActivity() {

    lateinit var binding:ActivityMain4Binding
    lateinit var uri: Uri
    lateinit var databaseReference: DatabaseReference
    lateinit var storageReference: StorageReference

    var a=true

    val arrayListCategorys=ArrayList<Category>()
    lateinit var categoryAdapterAdmin: CategoryAdapterAdmin

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMain4Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Categories")
        storageReference=FirebaseStorage.getInstance().getReference().child("Categories")

        binding.buttonpickimage.setOnClickListener {
            openFileChooser()
        }
        binding.buttonaddcategory.setOnClickListener {
            binding.progressbar1.visibility=View.VISIBLE
            uploadimage()
        }
        databaseReference.addValueEventListener(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                arrayListCategorys.clear()
                for (datasnapshot in snapshot.children){
                    val category=datasnapshot.getValue(Category::class.java)
                    arrayListCategorys.add(category!!)
                }
                categoryAdapterAdmin= CategoryAdapterAdmin(this@MainActivity4,arrayListCategorys)
                binding.recyclerview3.apply {
                    layoutManager=LinearLayoutManager(this@MainActivity4, LinearLayoutManager.HORIZONTAL,false)
                    adapter=categoryAdapterAdmin
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        binding.imageviewaddcategory.setOnClickListener {
            if (a){
                binding.linearlay3.visibility=View.VISIBLE
                a=false
            }else{
                binding.linearlay3.visibility=View.GONE
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
            Picasso.get().load(uri).into(binding.imageview6)
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
                    val category:Category=Category(it.toString(),binding.edittext9.text.toString(),uploadkey)
                    databaseReference.child(uploadkey).setValue(category)
                    binding.progressbar1.visibility=View.GONE
                    binding.linearlay3.visibility=View.GONE
                    a=true
                    binding.edittext9.setText("")
                    binding.imageview6.setImageResource(R.drawable.placeholderimage)
                    Toast.makeText(this@MainActivity4,"Yangi ketegoriya qo`shildi",Toast.LENGTH_SHORT).show()
                } }
        }


    }
}