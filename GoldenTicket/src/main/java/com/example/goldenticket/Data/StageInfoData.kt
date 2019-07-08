package com.example.goldenticket.Data

data class StageInfoData (
    val show_idx: Int,
    val image_url: String,
    val name: String,
    val location: String,
    val date: ArrayList<String>,
    val original_price: String,
    val discount_price: String,
    val draw_available: Int,
    val schedule: ArrayList<StageInfoSchedulesData>,
    val artist: ArrayList<StageInfoActorsData>,
    val poster: ArrayList<StageInfoImgsData>
)