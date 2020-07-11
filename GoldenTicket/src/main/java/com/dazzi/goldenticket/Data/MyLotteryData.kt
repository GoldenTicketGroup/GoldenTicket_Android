package com.dazzi.goldenticket.Data

data class MyLotteryData (
    /*var stageinfo_poster: String,
    var stageinfo_date: String,
    var stageinfo_title: String,
    var stageinfo_detail: String,
    var stageinfo_location: String,
    var stageinfo_time: String,
    var stageinfo_payment_status: Int*/

    var ticket_idx: Int,
    var qr_code: String,
    var image_url: String,
    var date: String,
    var name: String,
    var seat_name: String,
    var price: String,
    var location: String,
    var running_time: String
)