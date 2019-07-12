package com.dazzi.goldenticket.Network.Post

data class PostSignupResponse (
    val status: Int,        //status상태 코드 예)200,404
    val success: Boolean,   //성공여부
    val message:String,     //사용자를 위한 안내 메시지
    val data:String?        //사용자 인증 정보를 저장할 토큰값 null값을 가질 수 있다.
)