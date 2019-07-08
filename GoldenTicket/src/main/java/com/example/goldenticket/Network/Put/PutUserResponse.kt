package com.example.goldenticket.Network.Put

import com.example.goldenticket.Data.UserData


data class PutUserResponse(
    val status: Int,        //status상태 코드 예)200,404
    val success: Boolean,   //성공여부
    val message: String,  //사용자를 위한 안내 메시지
    val data: UserData    //사용자 정보
)