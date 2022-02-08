package com.example.calorietracker

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.calorietracker.data.FoodApplication
import com.example.calorietracker.data.FoodItem
import com.example.calorietracker.databinding.FragmentFoodDetailBinding
import com.example.calorietracker.viewmodels.SearchViewModel
import com.example.calorietracker.viewmodels.SearchViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FoodDetailFragment : Fragment() {

    private val viewModel: SearchViewModel by activityViewModels {
        SearchViewModelFactory(
            (activity?.application as FoodApplication).database.foodDao()
        )
    }

    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!

    private val navigationArgs: FoodDetailFragmentArgs by navArgs()

    private var _currentFood: FoodItem? = null
    private val currentFood get() = _currentFood!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // show menu
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFoodDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _currentFood = navigationArgs.foodItem

        binding.apply {
            val imgUri = currentFood.photo.toUri().buildUpon().scheme("https").build()
            ivFoodThumb.load(imgUri) {
                placeholder(R.drawable.ic_food_default)
                error(R.drawable.ic_food_default)
            }
            tvCalorie.text = currentFood.calories
            tvCalculator.text = this@FoodDetailFragment.getString(R.string.calorie_calc, currentFood.serving_qty, currentFood.serving_unit, currentFood.calories)
            btnAddBreakfast.setOnClickListener {
                // Create new food entry
                val newFoodEntry = FoodItem(
                    id =currentFood.id,
                    food_name = currentFood.food_name,
                    serving_qty = etQuantity.text.toString(),
                    serving_unit = currentFood.serving_unit,
                    calories = (etQuantity.text.toString().toFloat() * (currentFood.calories.toInt().div(currentFood.serving_qty.toInt()))).toInt().toString(),
                    photo = currentFood.photo,
                    category = currentFood.category,
                    protein = (etQuantity.text.toString().toFloat() * (currentFood.protein.toInt().div(currentFood.serving_qty.toInt()))).toInt().toString(),
                    carbs = (etQuantity.text.toString().toFloat() * (currentFood.carbs.toInt().div(currentFood.serving_qty.toInt()))).toInt().toString(),
                    fat = (etQuantity.text.toString().toFloat() * (currentFood.fat.toInt().div(currentFood.serving_qty.toInt()))).toInt().toString()
                )
                // Add food to room database
                addFood(newFoodEntry)
            }
        }

        binding.etQuantity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // TODO: Entering '.' crashes cause you cant do math with that
                var quantity = "1" // default
                // Get user quantity input
                if (p0 != null) {
                    if (p0.isNotEmpty()) {
                        quantity = p0.toString()
                    }
                }
                // TODO: User might put super high number, crashes
                binding.tvCalculator.text = this@FoodDetailFragment.getString(R.string.calorie_calc, quantity, currentFood.serving_unit, currentFood.calories)
                binding.tvCalorie.text = (quantity.toFloat() * (currentFood.calories.toInt().div(currentFood.serving_qty.toInt()))).toInt().toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_food, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                showConfirmationDialog()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }

    /**
     * Displays an alert dialog to get the user's confirmation before deleting the item.
     */
    fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage("Are you sure you want to delete?")
            .setCancelable(false)
            .setNegativeButton("No") { _, _ -> }
            .setPositiveButton("Yes") { _, _ ->
                deleteFood(currentFood)
            }
            .show()
    }

    private fun addFood(food: FoodItem) {
        viewModel.addFood(food)
    }

    private fun deleteFood(food: FoodItem) {
        viewModel.deleteFood(food)
        findNavController().navigateUp()
    }

    /**
     * Navigate back to home page
     */
    fun cancelPasswordCreation() {
        findNavController().navigate(R.id.action_foodDetailFragment_to_homeFragment)
    }

}