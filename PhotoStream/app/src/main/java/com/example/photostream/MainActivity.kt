package com.example.photostream


import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE

import android.content.Intent
import android.content.pm.PackageManager


import android.icu.text.SimpleDateFormat
import android.location.Address
import android.location.Geocoder
import android.location.Location

import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

import java.io.IOException
import java.util.Date
import java.util.Locale


open class MainActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_CAMERA_PERMISSION = 100
    }


    private lateinit var navView: BottomNavigationView
    private lateinit var viewPager: ViewPager2
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var fab:FloatingActionButton

    private lateinit var currentPhoto:String
    private lateinit var imageFile:File
    private var hasPermissionGranted=true
    private lateinit var addresses:ArrayList<Address>




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navView = findViewById(R.id.nav_view)
        viewPager = findViewById(R.id.viewPager)
        fab=findViewById(R.id.fab)

        val adapter = ViewPagerDigitalAssetAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        // Set the default selected item
        navView.selectedItemId = R.id.nav_picture

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_picture -> viewPager.currentItem = 0
                R.id.nav_albums -> viewPager.currentItem = 1

                R.id.nav_more -> showBottomSheetDialog()
            }
            true
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> navView.selectedItemId = R.id.nav_picture
                    1 -> navView.selectedItemId = R.id.nav_albums

                }
            }
        })
        fab.setOnClickListener { view ->
            if (checkAndRequestPermissions()) {
                openCamera()
            }
        }
    }

    private fun openCamera() {
        val timeStamp = SimpleDateFormat("yyyyMMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "IMG_$timeStamp.png"
         Log.i("tag",fileName)
        val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM+"/Camera")
        Log.i("tag",storageDir.toString())
        try {


            imageFile = File( storageDir,fileName)
            Log.i("imageFile","$imageFile")

            val imageUri=FileProvider.getUriForFile(this,"com.example.photostream",imageFile)
            val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
            startActivityForResult(intent,REQUEST_IMAGE_CAPTURE)
        }
        catch (e:IOException)
        {
            e.printStackTrace()

        }

    }

    private fun setLocationInImage(imageFile: File, latitude: Double, longitude: Double) {
        try {
            ExifInterface(imageFile.absolutePath).apply {
                setLatLong(latitude, longitude)
                saveAttributes()
            }
            println("Location set in image: $imageFile")
        } catch (e: IOException) {
            println("Error setting location in image: ${e.message}")
        }
    }

    private fun checkAndRequestPermissions(): Boolean {
        val permissionsNeeded = ArrayList<String>()

        val cameraPermission = ContextCompat.checkSelfPermission(this,CAMERA)
        val writeStoragePermission = ContextCompat.checkSelfPermission(this,WRITE_EXTERNAL_STORAGE)
        val readStoragePermission = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)
        val locationPermission= ContextCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION)
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(CAMERA)
        }
        if (writeStoragePermission != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(WRITE_EXTERNAL_STORAGE)
        }
        if (readStoragePermission != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(READ_EXTERNAL_STORAGE)
        }
        if(locationPermission!=PackageManager.PERMISSION_GRANTED&&hasPermissionGranted)
        {
            permissionsNeeded.add(ACCESS_FINE_LOCATION)
            hasPermissionGranted=false
        }

        return if (permissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsNeeded.toTypedArray(), REQUEST_CAMERA_PERMISSION)
            false
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {

            //
            openCamera()

        }
        else {
                Toast.makeText(
                    this,
                    "Required Camera Permission",
                    Toast.LENGTH_SHORT
                ).show()
        }

    }







    @Deprecated("Deprecated in Java")
    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK)
        {
            if(requestCode== REQUEST_IMAGE_CAPTURE)
            {


                val imageUri = Uri.fromFile(imageFile)

                
                // Make the image available in the gallery
                MediaScannerConnection.scanFile(this, arrayOf(imageFile.toString()), null) { path, uri ->
                    Log.i("tag", "Scanned $path:")
                    Log.i("tag", "-> uri=$uri")
                }
                Log.i("tag", "Image saved to: $imageUri")





            }
            else{
                Log.i("image","Failed to capture image")
            }
        }
        else{
            Log.i("image1","Failed to get permission")
        }
    }



    




    private fun showBottomSheetDialog() {
        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dailog)
        bottomSheetDialog.window?.attributes?.windowAnimations = R.style.BottomSheetAnimation

        val behavior = bottomSheetDialog.behavior
        behavior.peekHeight = resources.getDimensionPixelSize(R.dimen.bottom_sheet_peek_height)
        behavior.maxHeight = resources.getDimensionPixelSize(R.dimen.bottom_sheet_max_height)
        // Disable dragging

        bottomSheetDialog.show()
    }

}


