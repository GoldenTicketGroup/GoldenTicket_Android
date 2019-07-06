package com.example.goldenticket.Network

import com.example.goldenticket.Network.Post.GetCardDetailResponse
import com.example.goldenticket.Network.Post.GetCardListResponse
import com.example.goldenticket.Network.Post.GetContentDetailResponse
import com.example.goldenticket.Network.Post.PostLoginResponse
import com.example.goldenticket.Network.Post.PostSignupResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

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



    // 메인 뷰의 카드 리스트 조회
    @GET("card")
    fun getCardList(
        @Header("Content-Type") content_type: String
    ): Call<GetCardListResponse>


    // 카드 상세 조회 (RV 아닌 부분)
    @GET("card")
    fun getCardDetail(
        @Header("Content-Type") content_type: String,
        @Header("id") card_id: Int
    ): Call<GetCardDetailResponse>


    // 콘텐츠 상세 조회 (RV 부분)
    @GET("show/content")
    fun getContentDetail(
        @Header("Content-Type") content_type: String,
        @Header("id") card_id: Int
    ): Call<GetContentDetailResponse>
}
