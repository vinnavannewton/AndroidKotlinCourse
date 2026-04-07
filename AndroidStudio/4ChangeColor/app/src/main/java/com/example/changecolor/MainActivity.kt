package com.example.changecolor

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val colors = arrayOf(Color.RED, Color.WHITE, Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainLayout = findViewById<View>(R.id.main_layout)
        val buttonChange = findViewById<FloatingActionButton>(R.id.button_change)

        buttonChange?.setOnClickListener {
            mainLayout?.setBackgroundColor(colors[Random.nextInt(colors.size)])
        }
    }
}
