package com.example.calorietracker.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.calorietracker.data.Food
import com.example.calorietracker.data.FoodDao
import com.example.calorietracker.data.FoodItem
import com.example.calorietracker.network.BananaApi
import kotlinx.coroutines.launch

enum class BananaApiStatus { LOADING, ERROR, DONE }

class SearchViewModel(private val foodDao: FoodDao) : ViewModel() {

    private val _status = MutableLiveData<BananaApiStatus>()
    val status: LiveData<BananaApiStatus> = _status

    private val _currentFood = MutableLiveData<String>()
    val currFood: LiveData<String> = _currentFood

    private val _bananas = MutableLiveData<List<Food>>()
    val bananas: LiveData<List<Food>> = _bananas

    init {
    }

    fun addFood(food: FoodItem) {
        viewModelScope.launch {
            foodDao.insert(food)
        }
    }

    fun getListOf(food: String) {
        _currentFood.value = food
        _status.value = BananaApiStatus.LOADING
        try {
            viewModelScope.launch {
                Log.i("SearchViewModel", "Launching query")
                _bananas.value = BananaApi.retrofitService.getListOf(food, true).common
                _status.value = BananaApiStatus.DONE
                Log.i("SearchViewModel", bananas.value.toString())

            }
        } catch (e: Exception) {
            _bananas.value = listOf()
            _status.value = BananaApiStatus.ERROR
        }
    }
}

/**
 * Creates ViewModel instance
 */
class SearchViewModelFactory(private val foodDao: FoodDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(foodDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}