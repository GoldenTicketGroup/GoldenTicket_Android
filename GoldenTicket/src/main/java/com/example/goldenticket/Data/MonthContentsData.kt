package com.example.goldenticket.Data

data class MonthContentsData (
    var contentsImg : String,
    var contentsTitle : String,
    var contentsContent : String,
    var contentList : ArrayList<MonthContentsDetailData>
)