package com.example.photostream

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class AlbumsAdapterFragment(private val context: Context,private val albumsArray:ArrayList<AlbumModel>): RecyclerView.Adapter<AlbumsAdapterFragment.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.albums_gallery, parent, false))

    }

    override fun getItemCount(): Int {
        return albumsArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val albums = albumsArray[position]
        Glide.with(context)

            .load(Uri.parse( albums.imageUris[0]))//it convert the string of image into URI
            .placeholder(R.drawable.placeholder) // Placeholder image
            .into(holder.albumCover)
        holder.albumTitle.text=albums.name
        holder.albumCover.setOnClickListener{
            val intent = Intent(context, PhotoViewAlbum::class.java)
            intent.putStringArrayListExtra("img", ArrayList(albums.imageUris))
            intent.putExtra("albumName",albums.name)
            Log.i("image","hello")
            context.startActivity(intent)
        }


    }
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val albumTitle: TextView = itemView.findViewById(R.id.tvAlbumName)
        val albumCover =itemView.findViewById<ImageView>(R.id.ivAlbumCover)

    }
}