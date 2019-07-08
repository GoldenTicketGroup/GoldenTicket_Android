package com.example.goldenticket.Network

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationController : Application(){

    //manifests에 name속성 설정하기
    //통신하고자 하는 API 서버의 기본 주소
    private val baseURL ="https://goldenticket.ga"
    lateinit var networkService: NetworkService

    companion object{
        lateinit var instance: ApplicationController
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        buildNetwork()
    }

    //Retrofit 객체 생성
    private fun buildNetwork() {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        //Retrofit 객체 활성화
        networkService = retrofit.create(NetworkService::class.java)
    }
}