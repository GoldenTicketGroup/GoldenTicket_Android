package com.dazzi.goldenticket.Network.Get

import com.dazzi.goldenticket.Data.KeepShowData

data class GetKeepShowResponse (
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: ArrayList<KeepShowData>
)