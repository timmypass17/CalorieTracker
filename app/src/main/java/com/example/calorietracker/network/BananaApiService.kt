package com.example.calorietracker.network

import com.example.calorietracker.BuildConfig
import com.example.calorietracker.data.Food
import com.example.calorietracker.data.FoodResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL =
    "https://trackapi.nutritionix.com/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface BananaApiService {
    @Headers("x-app-id: ${BuildConfig.apiId}", "x-app-key: ${BuildConfig.apiKey}")
    @GET("search/instant")
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