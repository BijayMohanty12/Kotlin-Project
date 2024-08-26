package com.example.photostream

import android.content.Intent
import android.os.Bundle

import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class Intro : AppCompatActivity() {

    private var progressStatus = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro_layout)
         val progressBar: ProgressBar = findViewById(R.id.progressBar) // Ensure progressBar is initialized
        lifecycleScope.launch {

             while (progressStatus < 100) {
                 progressStatus += 1

                 // Update the progress bar on the main thread
                 progressBar.post {
                     progressBar.progress = progressStatus
                 }
                     delay(30)

             }

             // Start the main activity and finish the splash activity on the main thread

             startActivity(Intent(this@Intro, MainActivity::class.java))
             finish()


         }
    }




}
