package com.example.goldenticket.Data

data class ContentDetailData(
    var title : String,
    var subtitle : String,
    var image_url : String,
    var content : ArrayList<String>,
    var show_idx : Int
)