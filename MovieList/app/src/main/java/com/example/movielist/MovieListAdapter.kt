package com.example.movielist

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieListAdapter(private val context: Context,private val movieModel:ArrayList<MovieModel>): RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
      return MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false))
    }

    override fun getItemCount(): Int {
      return movieModel.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieList=movieModel[position]
        Glide.with(context)
            .load(Uri.parse(movieList.poster))
            .placeholder(R.drawable.posterplaceholder)
            .into(holder.poster)
        holder.movieTitle.text=movieList.movieTitle
        holder.rating.text=movieList.rating
        holder.overview.text=movieList.overview

    }
    class MovieViewHolder(items: View): RecyclerView.ViewHolder(items)
    {
        val  poster:ImageView= items.findViewById(R.id.imageView)
        val  movieTitle:TextView=items.findViewById(R.id.movieTitle)
        val  rating:TextView=items.findViewById(R.id.movieRating)
        val  overview:TextView=items.findViewById(R.id.summary)
    }

}