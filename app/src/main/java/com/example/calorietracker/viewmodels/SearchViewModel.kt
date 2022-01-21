package com.example.calorietracker.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.calorietracker.data.Banana
import com.example.calorietracker.network.BananaApi
import kotlinx.coroutines.launch

enum class BananaApiStatus { LOADING, ERROR, DONE }

class SearchViewModel : ViewModel() {

    private val _status = MutableLiveData<BananaApiStatus>()
    val status: LiveData<BananaApiStatus> = _status

    private val _bananas = MutableLiveData<List<Banana>>()
    val bananas: LiveData<List<Banana>> = _bananas

    fun getListOf(food: String) {
        _status.value = BananaApiStatus.LOADING
        try {
            viewModelScope.launch {
                Log.i("SearchViewModel", "Launching query")
                _bananas.value = BananaApi.retrofitService.getListOf(food).items
                _status.value = BananaApiStatus.DONE
                Log.i("SearchViewModel", _bananas.value.toString())
                Log.i("SearchViewModel", "Finished")
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
class SearchViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}