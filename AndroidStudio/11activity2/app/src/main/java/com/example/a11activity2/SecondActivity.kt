package com.example.a10activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    private lateinit var textViewDataIntent: TextView
    private lateinit var goBackButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textViewDataIntent = findViewById(R.id.textViewData)
        goBackButton = findViewById(R.id.buttonGoBack)
        goBackButton.setOnClickListener {
            val intent = intent
            intent.putExtra(Constants.INTENT_MESSAGE2_KEY, "Hello from the second activity")
            setResult(Constants.RESULT_CODE, intent)
            finish()
        }
        val data = intent.extras

        data?.let {
            val message = it.getString(Constants.INTENT_MESSAGE_KEY)
            val message2 = it.getString(Constants.INTENT_MESSAGE2_KEY)
            val Number = it.getDouble(Constants.INTENT_DATA_NUMBER)
            textViewDataIntent.text = message + "\n" + message2 + "\n" + Number

        }
    }
}