package com.example.goldenticket.Network.GET

import com.example.goldenticket.Data.ShowData

data class GetMainPosterResponse (
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: ArrayList<ShowData>?
)
