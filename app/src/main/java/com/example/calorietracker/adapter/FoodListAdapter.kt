package com.example.calorietracker.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.R
import com.example.calorietracker.data.Food
import com.example.calorietracker.data.FoodApplication
import com.example.calorietracker.data.FoodItem
import com.example.calorietracker.databinding.ItemFoodBinding
import com.example.calorietracker.viewmodels.FoodListViewModel
import com.example.calorietracker.viewmodels.FoodListViewModelFactory
import kotlinx.android.synthetic.main.item_food.view.*

class FoodListAdapter(private val viewModel: FoodListViewModel, private val onItemclicked: (FoodItem) -> Unit) :
    ListAdapter<FoodItem, FoodListAdapter.FoodListViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListViewHolder {
        return FoodListViewHolder(ItemFoodBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FoodListViewHolder, position: Int) {
        val food_item = getItem(position)
        // list item listener
        holder.itemView.setOnClickListener {
            onItemclicked(food_item)
        }
        holder.bind(food_item)
    }

    // inner to access outer class members
    inner class FoodListViewHolder(private var binding: ItemFoodBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(food_item: FoodItem) {
            binding.foodItem = food_item
            binding.executePendingBindings()

            binding.cbCalorie.setOnCheckedChangeListener { compoundButton, b ->
                if (!b) {
                    // remove
                    viewModel.updateConsumed(false, food_item.id)
                } else {
                    // add
                    viewModel.updateConsumed(true, food_item.id)
                }
            }
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

