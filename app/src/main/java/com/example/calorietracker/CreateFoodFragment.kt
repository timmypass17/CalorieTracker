package com.example.calorietracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.calorietracker.data.FoodApplication
import com.example.calorietracker.data.FoodItem
import com.example.calorietracker.databinding.FragmentCreateFoodBinding
import com.example.calorietracker.databinding.FragmentSearchBinding
import com.example.calorietracker.viewmodels.SearchViewModel
import com.example.calorietracker.viewmodels.SearchViewModelFactory

class CreateFoodFragment : Fragment() {

    private var _binding: FragmentCreateFoodBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by activityViewModels {
        SearchViewModelFactory(
            (activity?.application as FoodApplication).database.foodDao()
        )
    }

    private val navigationArgs: SearchFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateFoodBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnCreate.setOnClickListener { createFood() }
        }
    }

    private fun createFood() {
        // Get user input
        val name = binding.etName.text.toString()
        val calories = binding.etCalories.text.toString()
        val protein = binding.etProtein.text.toString()
        val carbs = binding.etCarbs.text.toString()
        val fat = binding.etFats.text.toString()
        val quantity = binding.etQuantity.text.toString()
        val unit = binding.etUnits.text.toString()

        // Create object
        if (isEntryValid(name, calories, protein, carbs, fat)) {
            val newFoodItem = FoodItem(
                food_name = name,
                calories = calories,
                protein = protein,
                carbs = carbs,
                fat = fat,
                serving_qty = quantity,
                serving_unit = unit,
                category = navigationArgs.foodCategory
            )
            // Add to room
            viewModel.addFood(newFoodItem)
        }
    }

    fun isEntryValid(name: String, calories: String, protein: String, carbs: String, fat: String): Boolean {
        if (name.isBlank() || calories.isBlank() || protein.isBlank() || carbs.isBlank() || fat.isBlank()) {
            return false
        }
        return true
    }
}