package com.example.calorietracker.binding

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.calorietracker.R
import com.example.calorietracker.adapter.BananaSearchListAdapter
import com.example.calorietracker.adapter.FoodListAdapter
import com.example.calorietracker.data.Food
import com.example.calorietracker.data.FoodItem
import com.example.calorietracker.viewmodels.BananaApiStatus

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
            placeholder(R.drawable.ic_food_default)
            error(R.drawable.ic_food_default)
//            error(R.drawable.ic_no_wifi)
        }
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Food>?) {
    val adapter = recyclerView.adapter as BananaSearchListAdapter
    adapter.submitList(data)
}

@BindingAdapter("foodListData")
fun bindFoodListRecyclerView(recyclerView: RecyclerView, data: List<FoodItem>?) {
    val adapter = recyclerView.adapter as FoodListAdapter
    adapter.submitList(data)
}

@BindingAdapter("progressBarStatus")
fun bindProgressBar(progressBar: ProgressBar, status: BananaApiStatus?) {
    when (status) {
        BananaApiStatus.LOADING -> {
            progressBar.visibility = View.VISIBLE
        }
        BananaApiStatus.ERROR -> {
            progressBar.visibility = View.INVISIBLE // Or View.GONE
        }
        BananaApiStatus.DONE -> {
            progressBar.visibility = View.INVISIBLE
        }
        else -> {
            progressBar.visibility = View.INVISIBLE
        }
    }
}