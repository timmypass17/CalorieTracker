package com.example.calorietracker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(food: FoodItem)

    @Delete
    suspend fun delete(food: FoodItem)

    @Query("SELECT * from foods where category = :category order by id")   // order by add date?
    fun getFoodList(category: String): Flow<List<FoodItem>>

    @Query("SELECT SUM(calories) from foods WHERE consumed = 1")
    fun getTotalCalories(): Flow<Int>

    @Query("SELECT SUM(protein) from foods WHERE consumed = 1")
    fun getTotalProtein(): Flow<Int>

    @Query("SELECT SUM(carbs) from foods WHERE consumed = 1")
    fun getTotalCarbs(): Flow<Int>

    @Query("SELECT SUM(fat) from foods WHERE consumed = 1")
    fun getTotalFat(): Flow<Int>

    // update consumed
    @Query("UPDATE foods SET consumed = :isConsumed WHERE id = :id")
    suspend fun update(isConsumed: Boolean, id: Int)
}