package com.example.calorietracker.binding

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.adapter.BananaSearchListAdapter
import com.example.calorietracker.data.Banana
import com.example.calorietracker.viewmodels.BananaApiStatus

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Banana>?) {
    val adapter = recyclerView.adapter as BananaSearchListAdapter
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