package com.example.photostream

data class AlbumModel(
    val name: String,
    val id: Long,
    var imageCount: Int,
    val imageUris: MutableList<String> = mutableListOf()
)


