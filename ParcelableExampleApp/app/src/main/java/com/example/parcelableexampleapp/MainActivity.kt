package com.example.parcelableexampleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    private lateinit var name: String
    private var age: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etName = findViewById<EditText>(R.id.etName)
        val etAge = findViewById<EditText>(R.id.etAge)
        val button = findViewById<Button>(R.id.btnSend)

        val intent = Intent(this, SecondActivity::class.java)

        button.setOnClickListener {
            // Retrieve values from EditText fields when button is clicked
            name = etName.text.toString()
            age = etAge.text.toString().toIntOrNull() ?: 0
            Log.i("TAG", "message $name and $age")

            val person = Person(name, age)
            intent.putExtra("person", person)
            startActivity(intent)
        }
    }
}


