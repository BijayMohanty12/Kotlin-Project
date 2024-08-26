package com.example.photostream

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Date
import java.util.Locale

class PicturesFragment : Fragment() {

    private lateinit var picturesRecyclerView: RecyclerView
    private val itemsArray = ArrayList<ItemModel>()
    private lateinit var adapter: PictureAdapterFragment
    private lateinit var contentObserver: ImageContentObserver
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pictures, container, false)
        picturesRecyclerView = view.findViewById(R.id.recyclerViewPicture)
        val layoutManager = GridLayoutManager(context, 3)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return adapter.getItemSpanSize(position)

            }
        }

        // Initialize the adapter
        picturesRecyclerView.layoutManager = layoutManager
        adapter = PictureAdapterFragment(requireActivity(), itemsArray)
        picturesRecyclerView.adapter = adapter
        val handler = Handler(Looper.getMainLooper())
        contentObserver = ImageContentObserver(handler) {
            loadImages()
        }


        return view
    }

    override fun onResume() {
        super.onResume()
        Log.i("PicturesFragment", "Fragment resumed")
        if (checkPermission()) {
            loadImages()
            registerContentObserver()
        } else {
            checkPermission()
        }
    }
    private fun registerContentObserver() {
        requireContext().contentResolver.registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            true,
            contentObserver
        )
    }


    override fun onPause() {
        super.onPause()
        unregisterContentObserver()
    }
    private fun unregisterContentObserver() {
        requireContext().contentResolver.unregisterContentObserver(contentObserver)
    }


    private fun checkPermission():Boolean {
        val result = ContextCompat.checkSelfPermission(
            requireContext().applicationContext,
            READ_EXTERNAL_STORAGE
        )
        return if (result == PackageManager.PERMISSION_GRANTED) {
            Log.i("PicturesFragment", "Permission granted")

            true
        } else {
            Log.i("PicturesFragment", "Requesting permission")
            requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
            false
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("PicturesFragment", "Permission granted")
            loadImages()
        } else {
            Log.d("PicturesFragment", "Permission denied")
        }
    }

    private fun loadImages() {
        // Use a Coroutine to perform the query in the background
        CoroutineScope(Dispatchers.IO).launch {
            val newItemsArray = ArrayList<ItemModel>()
            val projection = arrayOf(
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_TAKEN
            )
            val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"
            val query = requireContext().contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )

            query?.use { cursor ->
                Log.i("tag", "we are in Picture")

                var previousDateText = ""

                while (cursor.moveToNext()) {
                    val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    val dateTaken = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN))
                    val imagePath = cursor.getString(dataColumn)
                    val uri = Uri.fromFile(File(imagePath))
                    val dateText = formatDate(dateTaken)

                    if (dateText != previousDateText) {
                        Log.i("tag", dateText)
                        newItemsArray.add(ItemModel.DateHeaderModel(dateText))
                        previousDateText = dateText
                    }

                    Log.i("tag", uri.toString())
                    newItemsArray.add(ItemModel.PictureModel(uri.toString()))
                }
            }

            // Update UI on the main thread
            withContext(Dispatchers.Main) {
                itemsArray.clear()
                itemsArray.addAll(newItemsArray)
                picturesRecyclerView.adapter?.notifyDataSetChanged()

            }
        }
    }



    private fun formatDate(dateTaken: Long): String {
        return if (dateTaken != 0L) {
            val date = Date(dateTaken)
            SimpleDateFormat("dd MMM ", Locale.getDefault()).format(date)
        } else {
            "Unknown Date"
        }
    }






}





