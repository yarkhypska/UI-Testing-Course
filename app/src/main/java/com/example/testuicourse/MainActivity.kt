package com.example.testuicourse

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.inputField)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.changeText -> editText.setText("Lalala")
            R.id.switchActivity -> {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("input", editText.text.toString())
                startActivity(intent)
            }
        }
    }
}