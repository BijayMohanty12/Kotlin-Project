package com.example.photostream

import android.database.ContentObserver
import android.net.Uri
import android.os.Handler

class ImageContentObserver (
    handler: Handler,
    private val callback: () -> Unit
) : ContentObserver(handler) {

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        callback.invoke()
    }

    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)
        callback.invoke()
    }
}
