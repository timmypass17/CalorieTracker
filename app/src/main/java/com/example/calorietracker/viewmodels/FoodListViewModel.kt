package com.example.calorietracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.calorietracker.data.FoodDao
import com.example.calorietracker.data.FoodItem

class FoodListViewModel(private val foodDao: FoodDao) : ViewModel() {

    private val _foodItems = foodDao.getFoodList()
    val foodItems: LiveData<List<FoodItem>> = _foodItems.asLiveData()
}

class FoodListViewModelFactory(private val foodDao: FoodDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FoodListViewModel(foodDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}