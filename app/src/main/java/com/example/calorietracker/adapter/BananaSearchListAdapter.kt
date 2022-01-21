package com.example.calorietracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calorietracker.data.Banana
import com.example.calorietracker.databinding.ItemBananaSearchBinding

class BananaSearchListAdapter(private val onItemClicked: (Banana) -> Unit) :
    ListAdapter<Banana, BananaSearchListAdapter.BananaSearchViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BananaSearchViewHolder {
        return BananaSearchViewHolder(ItemBananaSearchBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: BananaSearchViewHolder, position: Int) {
        val food = getItem(position)
        holder.bind(food)
    }


    class BananaSearchViewHolder(private var binding: ItemBananaSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(banana: Banana) {
                binding.food = banana
                binding.executePendingBindings()
            }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Banana>() {

        override fun areItemsTheSame(oldItem: Banana, newItem: Banana): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Banana, newItem: Banana): Boolean {
            return oldItem.id == oldItem.id // Dont really care
        }
    }
}