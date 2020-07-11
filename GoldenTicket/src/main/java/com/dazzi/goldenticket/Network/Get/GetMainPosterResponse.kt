package com.dazzi.goldenticket.Network.Get

import com.dazzi.goldenticket.Data.ShowData

data class GetMainPosterResponse (
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: ArrayList<ShowData>?
)
