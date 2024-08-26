package com.example.movielist

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley


class VolleySingleton private constructor(context: Context) {
    private val requestQueue: RequestQueue = Volley.newRequestQueue(context.applicationContext)

    companion object {
        private var mInstance: VolleySingleton? = null

       @Synchronized
        fun getInstance(context: Context): VolleySingleton? {
            if (mInstance == null) {
                mInstance = VolleySingleton(context)
            }
            return mInstance
        }
    }
    fun getRequestQueue():RequestQueue{
        return requestQueue
    }
}