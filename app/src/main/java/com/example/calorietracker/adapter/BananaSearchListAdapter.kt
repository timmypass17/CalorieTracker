package com.example.calorietracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.data.Food
import com.example.calorietracker.databinding.ItemBananaSearchBinding

class BananaSearchListAdapter(private val onItemClicked: (Food) -> Unit) :
    ListAdapter<Food, BananaSearchListAdapter.BananaSearchViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BananaSearchViewHolder {
        return BananaSearchViewHolder(ItemBananaSearchBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: BananaSearchViewHolder, position: Int) {
        val food = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(food)
        }

        holder.bind(food)
    }


    class BananaSearchViewHolder(private var binding: ItemBananaSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food) {
                binding.food = food
                binding.executePendingBindings()
            }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Food>() {

        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.tag_id == newItem.tag_id
        }

        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.tag_id == oldItem.tag_id // Dont really care
        }
    }
}