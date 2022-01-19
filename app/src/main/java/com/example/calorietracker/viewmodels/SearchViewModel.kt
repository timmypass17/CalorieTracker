package com.example.calorietracker.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.calorietracker.data.Banana
import com.example.calorietracker.network.BananaApi
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _bananas = MutableLiveData<List<Banana>>()
    val bananas: LiveData<List<Banana>> = _bananas

    fun getListOf(food: String) {
        try {
            viewModelScope.launch {
                Log.i("SearchViewModel", "Launching query")
                _bananas.value = BananaApi.retrofitService.getListOf(food).items
                Log.i("SearchViewModel", _bananas.toString())
                Log.i("SearchViewModel", "Finished")
            }
        } catch (e: Exception) {
            _bananas.value = listOf()
        }
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