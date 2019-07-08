package com.example.goldenticket.Data

data class MyLotteryDetailData (
    val ticket_idx: Int,
    val qr_code: String,
    val image_url: String,
    val date: String,
    val name: String,
    val seatType: String,
    val seatName: String,
    val price: String,
    val location: String,
    val running_time: String
)