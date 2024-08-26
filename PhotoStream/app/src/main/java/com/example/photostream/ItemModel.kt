package com.example.photostream

sealed class ItemModel {
    data class PictureModel(val image: String) : ItemModel()
    data class DateHeaderModel(val date: String) : ItemModel()

}