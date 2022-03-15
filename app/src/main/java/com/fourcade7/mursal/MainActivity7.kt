package com.fourcade7.mursal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.fourcade7.mursal.databinding.ActivityMain7Binding

class MainActivity7 : AppCompatActivity() {
    lateinit var binding:ActivityMain7Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain7Binding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        binding.linearlay5.setOnClickListener {
            startActivity(Intent(this@MainActivity7,MainActivity4::class.java))
        }
        binding.linearlay6.setOnClickListener {
            startActivity(Intent(this@MainActivity7,MainActivity6::class.java))
        }

    }
}