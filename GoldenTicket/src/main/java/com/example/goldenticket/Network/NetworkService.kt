package com.example.goldenticket.Network

import com.example.goldenticket.Network.Delete.DeleteShowLikeResponse
import com.example.goldenticket.Network.Get.*
import com.example.goldenticket.Network.Post.*
import com.example.goldenticket.Network.Get.GetMyLotteryDetailResponse
import com.example.goldenticket.Network.Get.GetMyLotteryResponse
import com.example.goldenticket.Network.Get.GetStageInfoResponse
import com.example.goldenticket.Network.Get.GetCardDetailResponse
import com.example.goldenticket.Network.Get.GetCardListResponse
import com.example.goldenticket.Network.Get.GetContentDetailResponse
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


    //메인 뷰의 공연 리스트 조회
    @GET("/show/home")
    fun getMainPosterResponse(
        @Header("Content-Type") content_type: String
    ): Call<GetMainPosterResponse>

    // 메인 뷰의 카드 리스트 조회
    @GET("card")
    fun getCardList(
        @Header("Content-Type") content_type: String
    ): Call<GetCardListResponse>


    // 카드 상세 조회 (RV 아닌 부분)
    @GET("card/{id}")
    fun getCardDetail(
        @Header("Content-Type") content_type: String,
        @Path("id") card_id: Int
    ): Call<GetCardDetailResponse>


    // 좋아요
    @POST("show/like")
    fun postShowLike(
        @Header("Content-Type") content_type: String,
        @Header("token") user_token: String,
        @Body() body:JsonObject
    ): Call<PostShowLikeResponse>


    // 좋아요 취소
    @HTTP(method = "DELETE", path = "/api/analysis_delete", hasBody = true)
    fun deleteShowLike(
        @Header("Content-Type") content_type: String,
        @Header("token") user_token: String,
        @Body() body:JsonObject
    ): Call<DeleteShowLikeResponse>
//    @DELETE("show/like")


    //공연 상세
    @GET("/show/detail/{id}")
    fun getStageInfoResponse(
        @Header("Content-Type") content_type: String,
        @Path("id") show_idx: Int
    ): Call<GetStageInfoResponse>

    //공연내역 리스트 조회
    @GET("/ticket")
    fun getMyLotteryResponse(
        @Header("Content-Type") content_type: String,
        @Header("token") token: String
    ): Call<GetMyLotteryResponse>

    //공연내역 상세 조회
    @GET("/ticket/{id}")
    fun getMyLotteryDetailResponse(
        @Header("Content-Type") content_type: String,
        @Header("token") token: String,
        @Path("id") ticket_idx: Int
    ): Call<GetMyLotteryDetailResponse>
}
