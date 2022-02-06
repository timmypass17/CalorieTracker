package com.example.calorietracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.data.Food
import com.example.calorietracker.data.FoodItem
import com.example.calorietracker.databinding.ItemFoodBinding

class FoodListAdapter(private val onItemclicked: (FoodItem) -> Unit) :
    ListAdapter<FoodItem, FoodListAdapter.FoodListViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListViewHolder {
        return FoodListViewHolder(ItemFoodBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FoodListViewHolder, position: Int) {
        val food_item = getItem(position)
        holder.itemView.setOnClickListener {
            onItemclicked(food_item)
        }
        holder.bind(food_item)
    }

    class FoodListViewHolder(private var binding: ItemFoodBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(food_item: FoodItem) {
            binding.foodItem = food_item
            binding.executePendingBindings()

            binding.tvCalorie.text = food_item.calories
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<FoodItem>() {

        override fun areItemsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
            return oldItem.id == oldItem.id // Dont really care
        }
    }
}

