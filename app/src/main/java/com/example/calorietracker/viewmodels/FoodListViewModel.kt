package com.example.calorietracker.viewmodels

import androidx.lifecycle.*
import com.example.calorietracker.data.FoodDao
import com.example.calorietracker.data.FoodItem
import com.example.calorietracker.utility.getUpdatedFoodEntry
import kotlinx.coroutines.launch

class FoodListViewModel(private val foodDao: FoodDao) : ViewModel() {

    private val _breakfastItems = foodDao.getFoodList("breakfast")
    val breakfastItems: LiveData<List<FoodItem>> = _breakfastItems.asLiveData()

    private val _totalCalories = foodDao.getTotalCalories()
    val totalCalories: LiveData<Int> = _totalCalories.asLiveData()

//    fun updateConsumed(id: Int, name: String, qty: String, unit: String, cal: String, photo: String, category: String, consumed: Boolean) {
//        viewModelScope.launch {
//            val newFood = getUpdatedFoodEntry(id, name, qty, unit, cal, photo, category, consumed)
//            foodDao.update(newFood)
//        }
//    }

    fun updateConsumed(isConsumed: Boolean, id: Int) {
        viewModelScope.launch {
            foodDao.update(isConsumed, id)
        }
    }
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