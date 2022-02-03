package com.example.calorietracker.network

import com.example.calorietracker.data.Food
import com.example.calorietracker.data.FoodResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL =
    "https://trackapi.nutritionix.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface BananaApiService {
    @Headers("x-app-id: f6fd3be2", "x-app-key: e341d992e158a5530d3ea321e34f23ac")
    @GET("/v2/search/instant")
    suspend fun getListOf(
        @Query("query") foodName: String,
        @Query("detailed") isDetailed: Boolean): FoodResponse
}

/**
 * Instance of Retrofit API service
 */
object BananaApi {
    val retrofitService : BananaApiService by lazy {
        retrofit.create(BananaApiService::class.java)
    }
}