package com.example.calorietracker.data

data class FoodResponse(
    val common: List<Food>
)

// Quantity might be decimal.. maybe change to float
data class Food(
    val tag_id: String,
    val food_name: String,
    val serving_qty: String,
    val serving_unit: String,
    val photo: Photo
)

data class Photo(
    val thumb: String
)