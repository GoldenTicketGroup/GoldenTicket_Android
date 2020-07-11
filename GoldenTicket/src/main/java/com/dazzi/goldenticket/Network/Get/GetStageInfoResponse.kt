package com.dazzi.goldenticket.Network.Get

import com.dazzi.goldenticket.Data.StageInfoData

data class GetStageInfoResponse (
    val status: Int,
    val success: String,
    val message: String,
    val data: StageInfoData
)