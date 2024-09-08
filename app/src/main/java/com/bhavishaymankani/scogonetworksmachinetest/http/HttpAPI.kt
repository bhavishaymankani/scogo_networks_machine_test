package com.bhavishaymankani.scogonetworksmachinetest.http

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HttpAPI {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(HttpConstants.HTTP_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: Api = retrofit.create(Api::class.java)
}