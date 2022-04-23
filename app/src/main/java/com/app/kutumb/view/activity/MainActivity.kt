package com.app.kutumb.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.kutumb.R
import com.app.kutumb.databinding.ActivityMainBinding
import com.app.kutumb.viewmodel.KutumbViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    public lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}