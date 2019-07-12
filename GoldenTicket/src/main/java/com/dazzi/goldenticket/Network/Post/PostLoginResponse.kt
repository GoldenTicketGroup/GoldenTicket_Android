package com.dazzi.goldenticket.Network.Post

import com.dazzi.goldenticket.Data.UserData

data class PostLoginResponse (
    val status: Int,        //status상태 코드 예)200,404
    val success: Boolean,   //성공여부
    val message:String,     //사용자를 위한 안내 메시지
    val data: UserData?        //사용자 인증 정보를 저장할 토큰값 null값을 가질 수 있다.
)