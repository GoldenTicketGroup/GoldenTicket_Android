package com.example.goldenticket.Network.Post

import com.example.goldenticket.Data.ContentDetailData

data class GetContentDetailResponse (
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: ArrayList<ContentDetailData>
)