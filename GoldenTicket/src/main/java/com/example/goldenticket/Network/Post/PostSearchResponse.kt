package com.example.goldenticket.Network.Post

data class PostSearchResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: ArrayList<SearchData>
)

data class SearchData(
    val show_idx: Int,
    val image_url: String,
    val name: String
)