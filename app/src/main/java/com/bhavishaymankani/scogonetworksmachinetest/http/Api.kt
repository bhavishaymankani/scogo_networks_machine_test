package com.bhavishaymankani.scogonetworksmachinetest.http

import com.bhavishaymankani.scogonetworksmachinetest.model.Coin
import com.bhavishaymankani.scogonetworksmachinetest.model.CoinDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("coins")
    suspend fun getCoins(): Response<List<Coin>>

    @GET("coins/{coinId}")
    suspend fun getCoinDetails(@Path("coinId") coinId: String): Response<CoinDetails>
}