package com.fourcade7.mursal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import com.fourcade7.mursal.databinding.ActivityMain2Binding
import com.fourcade7.mursal.databinding.ActivityMainBinding
import com.fourcade7.mursal.databinding.BottomsheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso

class MainActivity2 : AppCompatActivity() {
    lateinit var binding:ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val img=intent.getStringExtra("img")
        val name=intent.getStringExtra("name")
        val description=intent.getStringExtra("description")
        val price=intent.getStringExtra("price")


        Picasso.get().load(img).fit().centerCrop().placeholder(R.drawable.img).into(binding.imageview4)
        binding.textviewordername1.text=name
        binding.textvieworderdescription1.text="$description\n$price"

        binding.buttonorder.setOnClickListener {
        val intent=Intent(this@MainActivity2,MainActivity3::class.java).apply {
            putExtra("img",img)
            putExtra("name",name)
            putExtra("description",description)
            putExtra("price",price)
        }

        startActivity(intent)
        }

    }
}