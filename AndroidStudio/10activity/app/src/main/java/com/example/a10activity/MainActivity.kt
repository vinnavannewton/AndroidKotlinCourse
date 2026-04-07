package com.example.a10activity
//passing data between first activity to second activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var goToButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        goToButton = findViewById(R.id.button_go_to_act)
        goToButton.setOnClickListener {
            Intent(this@MainActivity, SecondActivity::class.java).also {
                it.putExtra(Constants.INTENT_MESSAGE_KEY, "Hello from first Activity")
                it.putExtra(Constants.INTENT_MESSAGE2_KEY, "How was your day?")
                it.putExtra(Constants.INTENT_DATA_NUMBER, 3.14)
                startActivity(it)

            } //Go from main activity to second activity
        }
    }
} //20:26:08
