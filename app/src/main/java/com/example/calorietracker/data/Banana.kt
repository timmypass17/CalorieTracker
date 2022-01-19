package com.example.calorietracker.data

data class BananaResponse(
    val data: List<Banana>
)

// Quantity might be decimal.. maybe change to float
data class Banana(
    val id: String,
    val name: String,
    val description: String,
    val calories: String,
    val quantity: Int,
    val unit: String
)
