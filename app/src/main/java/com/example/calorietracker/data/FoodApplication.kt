package com.example.calorietracker.data

import android.app.Application

class FoodApplication : Application() {
    val database: FoodRoomDatabase by lazy {
        FoodRoomDatabase.getDatabase(this)
    }
}