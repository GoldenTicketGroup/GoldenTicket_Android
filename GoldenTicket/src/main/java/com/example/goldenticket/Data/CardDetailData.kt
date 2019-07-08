package com.example.goldenticket.Data

data class CardDetailData (
    var card_idx : Int,
    var imageUrl : String,
    var card_content : String,
    var title : String,
    var category : String,
    var content : ArrayList<ContentDetailData>
)