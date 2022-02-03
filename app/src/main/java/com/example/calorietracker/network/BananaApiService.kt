package com.example.calorietracker.network

import com.example.calorietracker.data.BananaResponse
import com.example.calorietracker.data.FoodUnit
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL =
    "https://nutrition-api.esha.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface BananaApiService {
    @Headers("Ocp-Apim-Subscription-Key: 1865e23300f248ac87cd86d6e0550e98")
    @GET("foods")
    suspend fun getListOf(
        @Query("query") food: String,
        @Query("count") count: Int
    ): BananaResponse

    @Headers("Ocp-Apim-Subscription-Key: 1865e23300f248ac87cd86d6e0550e98")
    @GET("units")
    suspend fun getFoodUnits(): List<FoodUnit>
}

/**
 * Instance of Retrofit API service
 */
object BananaApi {
    val retrofitService : BananaApiService by lazy {
        retrofit.create(BananaApiService::class.java)
    }
}