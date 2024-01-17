package com.example.projecttapholdbutton

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var counter: Int = 0;
    private var handler: Handler? = null
    private var runnable: Runnable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var reset = findViewById<TextView>(R.id.Toolbar)
        val Tap_Hold = findViewById<TextView>(R.id.button)
        val num = findViewById<TextView>(R.id.count)
        reset.setOnClickListener {
            num.text = setCount().toString()
        }
        Tap_Hold.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Start increasing count when the button is pressed down
                    startCountIncrease(num)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Stop increasing count when the button is released or canceled
                    stopCountIncrease()
                }
            }
            true
        }

    }
    private fun startCountIncrease(num: TextView) {
        handler=  Handler(Looper.getMainLooper())
        runnable = Runnable {
            // Increase the count and update the TextView
            num.text = increaseCount().toString()
            // Repeat the process after a delay (adjust the delay as needed)
            handler?.postDelayed(runnable as Runnable, 100)
        }
        handler?.postDelayed(runnable as Runnable, 100)
    }
    private fun stopCountIncrease() {
        // Stop the count increase by removing the callbacks
        runnable?.let { handler?.removeCallbacks(it) }
        handler = null
        runnable = null
    }



    fun setCount(): Int {
        counter = 0;
        return counter
    }

    fun increaseCount(): Int {
        return ++counter
    }

}


