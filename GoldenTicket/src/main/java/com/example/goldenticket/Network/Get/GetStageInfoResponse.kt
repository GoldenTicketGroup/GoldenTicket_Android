package com.example.goldenticket.Network.Get

import com.example.goldenticket.Data.StageInfoData

data class GetStageInfoResponse (
    val status: Int,
    val success: String,
    val message: String,
    val data: StageInfoData
)