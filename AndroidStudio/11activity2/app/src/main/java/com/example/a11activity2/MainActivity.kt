package com.example.a10activity
//passing data between first activity to second activity

import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

//GO from second act to first
class MainActivity : AppCompatActivity() {
    private lateinit var goToButton: Button
    private lateinit var textViewResult: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textViewResult = findViewById(R.id.textView)
        goToButton = findViewById(R.id.button_go_to_act)

        val getResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if(it.resultCode == Constants.RESULT_CODE) {
                    val message = it.data?.getStringExtra(Constants.INTENT_MESSAGE2_KEY)
                    textViewResult.text = message
                }
            }

        goToButton.setOnClickListener {
            Intent(this@MainActivity, SecondActivity::class.java).also {
                it.putExtra(SyncStateContract.Constants.INTENT_MESSAGE_KEY, "Hello from first Activity")
                it.putExtra(SyncStateContract.Constants.INTENT_MESSAGE2_KEY, "How was your day?")
                it.putExtra(SyncStateContract.Constants.INTENT_DATA_NUMBER, 3.14)
                getResult.launch(it)
            }
        }
    }
}
