package com.example.photostream

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


import java.io.File
import java.io.IOException
import java.util.Locale

class ViewPicture : AppCompatActivity() {

    private lateinit var imageFile:String
    private lateinit var image:ImageView
    private lateinit var edit:ImageView
    private lateinit var share:ImageView
    private lateinit var  info:ImageView
    private lateinit var  back:ImageView
    private lateinit var  menu:ImageView
    private lateinit var fab:ImageView
    private lateinit var  titleView:TextView
    private var isFavourite = true

    private lateinit var dialogBox:Dialog
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        private const val EDIT_IMAGE_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_picture)

        dialogBox=Dialog(this)
        image=findViewById(R.id.fullScreenIMage)
        edit=findViewById(R.id.imageView)
        share=findViewById(R.id.imageView3)
        info=findViewById(R.id.imageView2)
        back=findViewById(R.id.back)
        menu=findViewById(R.id.menu)
        fab=findViewById(R.id.favourite)
        titleView=findViewById(R.id.title)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        imageFile= intent.getStringExtra("imageFile").toString()

        val imageUri =Uri.parse(imageFile)
        Glide.with(this)
            .load(Uri.parse(imageFile))
            .placeholder(R.drawable.placeholder) // Placeholder image
            .into(image)
        val imageTitle=getImageTitleFromUri(imageUri)

        titleView.text = imageTitle
        
        back.setOnClickListener{
            finish()
        }

        menu.setOnClickListener{
            showPopupMenu(it)
        }
        fab.setOnClickListener{
           favouriteClick()
        }
        info.setOnClickListener{


            dialogBox.setContentView(R.layout.details_box)
            val title= dialogBox.findViewById<TextView>(R.id.name1)
            title.text = imageTitle
            val path=dialogBox.findViewById<TextView>(R.id.address)
            path.text= imageUri.toString()
            getImageMetadata(imageUri)
            dialogBox.show()
        }
        share.setOnClickListener{
            shareImage(imageUri)
        }
        edit.setOnClickListener{
           editImage(imageUri)
        }



    }

    private fun editImage(imageUri: Uri?) {

    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var editedImageUri: Uri? =null
        if(resultCode== RESULT_OK)
        {
            if(requestCode== EDIT_IMAGE_REQUEST_CODE)
            {
                if(data!=null)
                {
                    editedImageUri=data.data
                    Log.i("tag","$editedImageUri and $imageFile")
                    saveEditedImageToGallery(editedImageUri,imageFile);
                }

            }
        }
    }

    private fun saveEditedImageToGallery(editedImageUri: Uri?, imageFile: String) {
        try {
            // Delete the original image
            val originalFile = File(imageFile)
            if (originalFile.exists()) {
                originalFile.delete()
            }
            val resolver = contentResolver
            val inputStream = editedImageUri?.let { resolver.openInputStream(it) }
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, originalFile.name)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.DATA, imageFile)
            }

            val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            imageUri?.let { uri ->
                resolver.openOutputStream(uri)?.use { outputStream ->
                    inputStream?.use { input ->
                        input.copyTo(outputStream)  // Copy data from InputStream to OutputStream
                    }
                }
            }

        }  catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun shareImage(imageUri: Uri?) {
        val imageFile=File(imageUri?.path!!)
        val uriToShare= FileProvider.getUriForFile(this,  "com.example.photostream",imageFile)
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uriToShare)
            type = "image/*"
        }
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        startActivity(Intent.createChooser(shareIntent, "Share Image"))

    }


    private fun getImageMetadata(imageUri: Uri) {

        val file = File(imageUri.path!!)
        if (!file.exists()) {
            Log.e("tag", "File does not exist: ${imageUri.path}")
            return
        }

        val size = file.length() / 1024 // Size in KB
        val date = file.lastModified()
        val dateFormatted = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date)
        try {
            contentResolver.openInputStream(imageUri)?.use { inputStream ->
                val exif = ExifInterface(inputStream)

                if(ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
                {
                    fusedLocationClient.lastLocation.addOnSuccessListener{location:
                                                                          Location?->
                        val currentLocation=dialogBox.findViewById<TextView>(R.id.Location_address)
                         if(location!=null)
                         {
                             val geocoder= Geocoder(this,Locale.getDefault())
                             val address=geocoder.getFromLocation(location.latitude,location.longitude,1) as ArrayList<Address>
                              Log.i("latlng","$address")


                                 Log.i("hello","$address")
                                     currentLocation.text = "${address.get(0).getAddressLine(0)}"
                         }

                         else{
                             currentLocation.text="location is not provided"
                         }


                    }
                }
                val width = exif.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, 0)
                val height = exif.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, 0)
                val imageResolution = dialogBox.findViewById<TextView>(R.id.Pixels)
                imageResolution.text = "$width x $height"
            }
        }
        catch (e: IOException) {
            Log.e("tag", "Error reading Exif data", e)
        }

        val imageSize = dialogBox.findViewById<TextView>(R.id.size1)
        val imageDate = dialogBox.findViewById<TextView>(R.id.Date)


        imageSize.text = "$size KB"
        imageDate.text = dateFormatted





    }





    private fun getImageTitleFromUri(imageUri: Uri): String? {
        return if(imageUri.scheme=="file") {
            imageUri.path?.let { File(it) }?.name
        }
        else{
            null
        }
    }

    private fun favouriteClick() {
        if (isFavourite) {
           fab.setImageResource(R.drawable.favorite);
        } else {
            fab.setImageResource(R.drawable.favorite_border);
        }
        isFavourite = !isFavourite
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this,view)
        popupMenu.menuInflater.inflate(R.menu.image_viewitem,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId)
            {
                R.id.set->{
                    Toast.makeText(this, "Set As", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.rename->{
                    Toast.makeText(this, "Renamed", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.details->{
                    Toast.makeText(this, "Details", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.picture->{
                    Toast.makeText(this, "Picture", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.print->{
                    Toast.makeText(this, "Print", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }

        }
        popupMenu.show()
    }


}


