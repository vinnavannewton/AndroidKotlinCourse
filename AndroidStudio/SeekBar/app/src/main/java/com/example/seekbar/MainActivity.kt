package com.example.seekbar

import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var seekBar: SeekBar
    private lateinit var textViewProgress: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        seekBar = findViewById(R.id.seekBar)
        textViewProgress = findViewById(R.id.textViewProgress)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener { //made using implement members
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                Toast.makeText(this@MainActivity, "onProgressChanged", Toast.LENGTH_SHORT).show()
                textViewProgress.text = "Rate = " + seekBar?.progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(this@MainActivity, "onStartTrackingTouch", Toast.LENGTH_SHORT).show()

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(this@MainActivity, "onStopTrackingTouch", Toast.LENGTH_SHORT).show()
                textViewProgress.text = "Finale rate = " + seekBar!!.progress.toString()


            }

        })

    }
}