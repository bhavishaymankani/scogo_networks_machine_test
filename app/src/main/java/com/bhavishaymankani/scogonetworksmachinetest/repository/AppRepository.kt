package com.bhavishaymankani.scogonetworksmachinetest.repository

import com.bhavishaymankani.scogonetworksmachinetest.http.HttpAPI

class AppRepository {

    suspend fun getCoins() = HttpAPI().service.getCoins()
    suspend fun getCoinDetails(coinId: String) = HttpAPI().service.getCoinDetails(coinId)
}