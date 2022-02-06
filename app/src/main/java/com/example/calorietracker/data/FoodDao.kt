package com.example.calorietracker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Insert
    suspend fun insert(food: FoodItem)

    @Query("SELECT * from foods where category = :category order by id")   // order by add date?
    fun getFoodList(category: String): Flow<List<FoodItem>>

    @Query("SELECT SUM(calories) from foods WHERE consumed = 1")
    fun getTotalCalories(): Flow<Int>

    @Update
    suspend fun update(food: FoodItem)

    @Query("UPDATE foods SET consumed = :isConsumed WHERE id = :id")
    suspend fun update(isConsumed: Boolean, id: Int)
}