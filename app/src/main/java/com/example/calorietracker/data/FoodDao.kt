package com.example.calorietracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Insert
    suspend fun insert(food: FoodItem)

    @Query("SELECT * from foods order by id")   // order by add date?
    fun getFoodList(): Flow<List<FoodItem>>
}