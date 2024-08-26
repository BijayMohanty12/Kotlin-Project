package com.example.photostream

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide

class PhotoAdapter(val context:Context,private val photoAdapter:ArrayList<String>): RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        val imageView: ImageView =itemView.findViewById(R.id.image23)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
     return ViewHolder(LayoutInflater.from(context).inflate(R.layout.photoholder, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val picture = photoAdapter[position]
        Glide.with(context)
            .load(Uri.parse(picture))
            .placeholder(R.drawable.placeholder) // Placeholder image
            .into(holder.imageView)
        holder.imageView.setOnClickListener{
            val intent= Intent(context,ViewPicture::class.java)
            intent.putExtra("imageFile",picture)
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return photoAdapter.size
    }


}