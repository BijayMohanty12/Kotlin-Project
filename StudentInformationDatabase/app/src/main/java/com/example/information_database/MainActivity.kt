package com.example.information_database

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
   private   lateinit var db:DatabaseHelper
   private lateinit var  name:EditText
   private lateinit var   room:EditText
   private lateinit var  marks:EditText
   private lateinit var  id:EditText
   private lateinit var  insert:Button
   private lateinit var   view:Button
   private lateinit var   update:Button
   private lateinit var delete :Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db=DatabaseHelper(this)
        name=findViewById(R.id.username)
        room=findViewById(R.id.class_name)
        marks=findViewById(R.id.mark_value)
        insert=findViewById(R.id.create)
        view=findViewById(R.id.view_data)
        id=findViewById(R.id.ID_value)
        update=findViewById(R.id.Update)
        delete=findViewById(R.id.Delete)

        insertValue()
        viewValue()
        updateValue()
        deleteValue()
    }

    private fun deleteValue() {
       delete.setOnClickListener{
           db.deleteData(id.text.toString())
       }
    }

    private fun updateValue() {
       update.setOnClickListener{
           db.updateData(id.text.toString(),name.text.toString(),room.text.toString(),marks.text.toString())
       }
    }

    private fun viewValue() {
         view.setOnClickListener{
            val cursor=db.viewData()
             if(cursor.count==0)
             {
              showMessage("Error","Noting found")
                 return@setOnClickListener
             }
             val builder=StringBuilder();
             while(cursor.moveToNext())
             {
                 builder.append("Id :"+cursor.getString(0)+"\n")
                 builder.append("Name :"+cursor.getString(1)+"\n")
                 builder.append("Class :"+cursor.getString(2)+"\n")
                 builder.append("Marks :"+cursor.getString(3)+"\n")
                 builder.append("\n")
                 showMessage("Data",builder.toString())
             }
         }
    }

    private fun insertValue() {
       insert.setOnClickListener{
          db.insertData(name.text.toString(),room.text.toString(),marks.text.toString())

       }
    }
    private fun showMessage(title:String, message:String) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
            .setTitle(title)
            .setMessage(message)
            .show()
    }



}