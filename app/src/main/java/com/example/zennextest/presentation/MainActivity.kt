package com.example.zennextest.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zennextest.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Zennex)
        setContentView(R.layout.activity_main)
    }
}