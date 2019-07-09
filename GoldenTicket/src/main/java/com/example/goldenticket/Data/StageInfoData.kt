package com.example.goldenticket.Data

data class StageInfoData (
    val show_idx: Int,
    val image_url: String,
    val name: String,
    val location: String,
    val duration: String,
    val original_price: String,
    val discount_price: String,
    val schedule: ArrayList<StageInfoSchedulesData>,
    val artist: ArrayList<StageInfoActorsData>,
    val poster: ArrayList<StageInfoImgsData>
)