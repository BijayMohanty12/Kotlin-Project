
package com.example.photostream


import android.content.Context
import android.content.Intent
import android.net.Uri

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class PictureAdapterFragment(val context: Context, private val items:ArrayList<ItemModel>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val VIEW_TYPE_DATE_HEADER = 0
        const val VIEW_TYPE_IMAGE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ItemModel.DateHeaderModel -> VIEW_TYPE_DATE_HEADER
            is ItemModel.PictureModel -> VIEW_TYPE_IMAGE_ITEM

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DATE_HEADER -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)
                DateHeaderViewHolder(view)
            }

            VIEW_TYPE_IMAGE_ITEM -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.picture_gallery, parent, false)
                ImageViewHolder(view)
            }

            else -> {
                throw IllegalArgumentException("Invalid item type")
            }
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
             is DateHeaderViewHolder -> {
                 val header = items[position] as ItemModel.DateHeaderModel
                holder.headerTextView.text = header.date
            }
            is ImageViewHolder -> {
                val picture = items[position] as ItemModel.PictureModel
                Glide.with(context)
                    .load(Uri.parse(picture.image))
                    .placeholder(R.drawable.placeholder)
                    .into(holder.imageView)
                holder.imageView.setOnClickListener {
                    val intent = Intent(context, ViewPicture::class.java)
                    intent.putExtra("imageFile", picture.image)
                    context.startActivity(intent)
                }

            }


        }

    }

    fun getItemSpanSize(position: Int): Int {
        return when (getItemViewType(position)) {
            VIEW_TYPE_DATE_HEADER -> 3
            VIEW_TYPE_IMAGE_ITEM -> 1
            else ->0
        }
    }


    class DateHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val headerTextView: TextView = view.findViewById(R.id.textViewDate)
        }

        // ViewHolder for image items
        class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val imageView: ImageView = view.findViewById(R.id.image)
        }


}

