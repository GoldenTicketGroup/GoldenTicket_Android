package com.dazzi.goldenticket.Network.Get

import com.dazzi.goldenticket.Data.CardListData

data class GetCardListResponse(
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: ArrayList<CardListData>
)