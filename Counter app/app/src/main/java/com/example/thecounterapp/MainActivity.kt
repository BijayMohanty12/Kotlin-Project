package com.example.thecounterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var counter:Int=0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val num:TextView =findViewById(R.id.num)
        val btn: Button =findViewById(R.id.btn)
        btn.setOnClickListener{
          num.setText(""+increaseCount())
        }

    }
    fun increaseCount():Int
    {
     return ++counter;
    }
}