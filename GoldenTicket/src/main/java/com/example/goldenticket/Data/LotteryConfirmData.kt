package com.example.goldenticket.Data

import java.util.*

data class LotteryConfirmData (
    var stageinfo_poster: String,
    var stageinfo_date: String,
    var stageinfo_title: String,
    var stageinfo_detail: String,
    var stageinfo_location: String,
    var stageinfo_time: String,
    var stageinfo_payment_status: Int
)