package com.example.parcelableexampleapp

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast

class SecondActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)
        val textview1=findViewById<TextView>(R.id.textView1)
        val textview2=findViewById<TextView>(R.id.textView2)

        val person: Person? = intent.getParcelableExtra("person") as? Person
        if (person != null) {
            val name = person.name ?: "Unknown Name"
            val age = person.age.toString()
            textview1.text = name
            Log.i("textview1",textview1.toString())
            textview2.text=age
            Log.i("textview2",textview2.toString())
        } else {
           Toast.makeText(this,"Parcelable is not Implement properly",Toast.LENGTH_LONG).show()
        }

    }
}