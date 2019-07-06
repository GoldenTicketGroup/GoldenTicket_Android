package com.example.goldenticket.Network

import com.example.goldenticket.Network.Post.PostLoginResponse
import com.example.goldenticket.Network.Post.PostSignupResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface NetworkService {
    //post방식은 HTTP Request의 Body에 Json 포맷으로 데이터를 담아서 전달.
    //로그인 api
    @POST("/auth/signin")
    fun postLoginResponse(
        @Header("Content-Type") content_type: String,
        @Body body: JsonObject
    ): Call<PostLoginResponse>

    //회원가입 api
    @POST("/auth/signup")
    fun postSignupResponse(
        @Header("Content-Type") content_type: String,
        @Body body: JsonObject
    ): Call<PostSignupResponse>


}
