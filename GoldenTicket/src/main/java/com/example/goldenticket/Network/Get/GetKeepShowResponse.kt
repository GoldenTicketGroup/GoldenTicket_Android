package com.example.goldenticket.Network.Get

import com.example.goldenticket.Data.KeepShowData

data class GetKeepShowResponse (
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: ArrayList<KeepShowData>
)