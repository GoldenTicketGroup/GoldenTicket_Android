package com.example.goldenticket.Network

import com.example.goldenticket.Network.Get.GetStageInfoResponse
import com.example.goldenticket.Network.Post.PostLoginResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {
    //post방식은 HTTP Request의 Body에 Json 포맷으로 데이터를 담아서 전달.
    //로그인 api
    @POST("/api/auth/signin")
    fun postLoginResponse(
        @Header("Content-Type") content_type: String,
        @Body body: JsonObject
    ): Call<PostLoginResponse>

    //공연 상세
    @GET("/show/detail/{id}")
    fun getStageInfoResponse(
        @Header("Content-Type") content_type: String,
        @Path("id") show_idx: Int
    ): Call<GetStageInfoResponse>

    //공연 상세 좋아효
    //@POST
}
