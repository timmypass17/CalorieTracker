package com.example.calorietracker.data

data class BananaResponse(

    val items: List<Banana>
)

// Quantity might be decimal.. maybe change to float
data class Banana(
    val id: String,
    val description: String,
    val quantity: Float,
    val unit: String
)
