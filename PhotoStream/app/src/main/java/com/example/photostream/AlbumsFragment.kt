package com.example.photostream

import android.net.Uri
import android.os.Bundle

import android.provider.MediaStore

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


class AlbumsFragment : Fragment() {

    private lateinit var albumsRecyclerView: RecyclerView
    private val albumsArray = ArrayList<AlbumModel>()
    private lateinit var adapter: AlbumsAdapterFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_albums, container, false)
        albumsRecyclerView = view.findViewById(R.id.rvAlbumView)
        albumsRecyclerView.layoutManager = GridLayoutManager(context, 3)
        adapter = AlbumsAdapterFragment(requireContext(), albumsArray)

        albumsRecyclerView.adapter = adapter
        loadAlbums()

        return view
    }

    override fun onResume() {
        super.onResume()
        loadAlbums()


    }


    private fun loadAlbums() {
        CoroutineScope(Dispatchers.IO).launch {
            val projection = arrayOf(
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.DATA // For getting the image file path
            )
            val sortOrder = "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} ASC"
            val query = requireContext().contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, // Projection
                null, // Selection
                null, // Selection arguments
                sortOrder // Sort order
            )
            val albumMap = mutableMapOf<Long, AlbumModel>()
            query?.use { cursor ->
                val bucketNameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
                val bucketIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID)
                val imageDataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                while (cursor.moveToNext()) {
                    val bucketName = cursor.getString(bucketNameColumn)
                    val bucketId = cursor.getLong(bucketIdColumn)
                    val imagePath = cursor.getString(imageDataColumn)
                    val imageUri = Uri.fromFile(File(imagePath)).toString()

                    val album = albumMap[bucketId] ?: AlbumModel(bucketName, bucketId, 0)
                    album.imageCount += 1
                    album.imageUris.add(imageUri)
                    albumMap[bucketId] = album
                }
            }
            withContext(Dispatchers.Main) {
                albumsArray.clear()
                albumsArray.addAll(albumMap.values)
                albumsRecyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }
}
