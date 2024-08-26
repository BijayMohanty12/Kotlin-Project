package com.example.photostream


import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class PhotoViewAlbum:  AppCompatActivity(){
    private lateinit var  back: ImageView
    private lateinit var  titleView:TextView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.photoview)
        back=findViewById(R.id.back)
        titleView=findViewById(R.id.title_toolbar)
        val img = intent.getStringArrayListExtra("img")
        Log.i("image", "Received images: $img")

        // Retrieve the album name from the intent (if needed)
        val albumName = intent.getStringExtra("albumName")
        if(albumName != null) {
            titleView.text = albumName
        }

        if (img != null) {
            // Initialize the PhotoAdapter with the list of images
            val imageAdapter = PhotoAdapter(this, img)

            // Find the RecyclerView and set its adapter and layout manager
            val rvAlbum = findViewById<RecyclerView>(R.id.photoAlbumSetup)
            rvAlbum.adapter = imageAdapter
            rvAlbum.layoutManager = GridLayoutManager(this, 3)
        }
        back.setOnClickListener{
            finish()
        }


    }
}