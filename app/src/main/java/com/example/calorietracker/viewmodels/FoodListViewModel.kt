package com.example.calorietracker.viewmodels

import androidx.lifecycle.*
import com.example.calorietracker.data.Food
import com.example.calorietracker.data.FoodDao
import com.example.calorietracker.data.FoodItem
import kotlinx.coroutines.launch

class FoodListViewModel(private val foodDao: FoodDao) : ViewModel() {

    private val _breakfastItems = foodDao.getFoodList("breakfast")
    val breakfastItems: LiveData<List<FoodItem>> = _breakfastItems.asLiveData()

    private val _lunchItems = foodDao.getFoodList("lunch")
    val lunchItems: LiveData<List<FoodItem>> = _lunchItems.asLiveData()

    private val _totalCalories = foodDao.getTotalCalories()
    val totalCalories: LiveData<Int> = _totalCalories.asLiveData()

    private val _totalProtein = foodDao.getTotalProtein()
    val totalProtein: LiveData<Int> = _totalProtein.asLiveData()

    private val _totalCarbsConsumed = foodDao.getTotalCarbsConsumed()
    val totalCarbsConsumed: LiveData<Int> = _totalCarbsConsumed.asLiveData()

    private val _totalFatsConsumed = foodDao.getTotalFatConsumed()
    val totalFatsConsumed: LiveData<Int> = _totalFatsConsumed.asLiveData()

    private val _totalBreakfastProtein = foodDao.getTotalProteinFromCategory("breakfast")
    val totalBreakfastProtein: LiveData<Int> = _totalBreakfastProtein.asLiveData()

    private val _totalBreakfastCarbs = foodDao.getTotalCarbsFromCategory("breakfast")
    val totalBreakfastCarbs: LiveData<Int> = _totalBreakfastCarbs.asLiveData()

    private val _totalBreakfastFat = foodDao.getTotalFatFromCategory("breakfast")
    val totalBreakfastFat: LiveData<Int> = _totalBreakfastFat.asLiveData()

    fun updateConsumed(isConsumed: Boolean, id: Int) {
        viewModelScope.launch {
            foodDao.update(isConsumed, id)
        }
    }

    fun deleteFood(food_item: FoodItem) {
        viewModelScope.launch {
            foodDao.delete(food_item)
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