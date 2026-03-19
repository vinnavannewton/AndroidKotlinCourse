package com.example.togglebutton

import android.os.Bundle
import android.widget.TextView
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var toggleButton: ToggleButton
    private lateinit var textViewHide: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        toggleButton = findViewById(R.id.toggleButton)
        textViewHide = findViewById(R.id.textView)

        toggleButton.setOnCheckedChangeListener { compoundButton, b ->

        }

        }
    }
