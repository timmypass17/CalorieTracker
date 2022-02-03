package com.example.calorietracker.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.calorietracker.data.Banana
import com.example.calorietracker.data.FoodUnit
import com.example.calorietracker.network.BananaApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch
import retrofit2.Response

enum class BananaApiStatus { LOADING, ERROR, DONE }

class SearchViewModel : ViewModel() {

    private val _status = MutableLiveData<BananaApiStatus>()
    val status: LiveData<BananaApiStatus> = _status

    private val _bananas = MutableLiveData<List<Banana>>()
    val bananas: LiveData<List<Banana>> = _bananas

    private val _foodUnits = MutableLiveData<List<FoodUnit>>()
    val foodUnits: LiveData<List<FoodUnit>> = _foodUnits

    init {
        try {
            viewModelScope.launch {
                _foodUnits.value = BananaApi.retrofitService.getFoodUnits()
                Log.i("SearchViewModel", foodUnits.value.toString())
            }
        } catch (e: Exception) {
            _foodUnits.value = listOf()
        }
    }

    fun getListOf(food: String) {
        _status.value = BananaApiStatus.LOADING
        try {
            viewModelScope.launch {
                Log.i("SearchViewModel", "Launching query")
                val foodList = BananaApi.retrofitService.getListOf(food, 5).items
                // update units into actual human words
                for (item: Banana in foodList) {
                    item.unit = convertUnit(item.unit)
                }
                _bananas.value = foodList
                _status.value = BananaApiStatus.DONE

            }
        } catch (e: Exception) {
            _bananas.value = listOf()
            _status.value = BananaApiStatus.ERROR
        }
    }

    fun convertUnit(query: String): String {
        // Look for food unit
        for (unit: FoodUnit in foodUnits.value!!) {
            // Found food unit
            if (unit.id == query) {
                return unit.abbr
            }
        }
        return "something LOL"
    }
}

/**
 * Creates ViewModel instance
 */
class SearchViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}