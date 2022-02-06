package com.example.calorietracker

import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.calorietracker.adapter.BananaSearchListAdapter
import com.example.calorietracker.data.Food
import com.example.calorietracker.data.FoodApplication
import com.example.calorietracker.data.FoodItem
import com.example.calorietracker.databinding.FragmentSearchBinding
import com.example.calorietracker.utility.getCalories
import com.example.calorietracker.viewmodels.SearchViewModel
import com.example.calorietracker.viewmodels.SearchViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.dialog_choose_food.*
import kotlinx.android.synthetic.main.dialog_choose_food.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.item_banana_search.view.*


class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by activityViewModels {
        SearchViewModelFactory(
            (activity?.application as FoodApplication).database.foodDao()
        )
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val navigationArgs: SearchFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // list item click listener
        val adapter = BananaSearchListAdapter { banana ->
            showChooseFoodUnitDialog(banana)
        }

        // data binding
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.rvBananas.adapter = adapter

        // search view click listener
        binding.svFood.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i("SearchFragment", "$query")
                if (query != null) {
                    binding.tvResult.text = "Searching for: \"$query\""
                    viewModel.getListOf(query)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Log.i("SearchFragment", "Typing")
                return false
            }
        })

        // searchview stuff
        binding.svFood.setQuery(viewModel.currFood.value, false)    // prepopulate searchview text
        binding.svFood.requestFocus()   // set focus to search view immediately
    }

    // TODO: So messy
    private fun showChooseFoodUnitDialog(food: Food) {
        val chooseFoodDialog = LayoutInflater.from(context).inflate(R.layout.dialog_choose_food, null)

        // Set up views
//        chooseFoodDialog.etQuantity.requestFocus()
        val tvCalorie = chooseFoodDialog.findViewById<TextView>(R.id.tvCalorie) // no fancy databindings, sadge :(
        tvCalorie.text = getCalories(food)

        val tvCalculator = chooseFoodDialog.findViewById<TextView>(R.id.tvCalculator)
        tvCalculator.text = this@SearchFragment.getString(R.string.calorie_calc, food.serving_qty, food.serving_unit, getCalories(food))
        val etQuantityLayout = chooseFoodDialog.findViewById<TextInputLayout>(R.id.etQuantityLayout)
        etQuantityLayout.suffixText = food.serving_unit
        val etQuantity = chooseFoodDialog.findViewById<TextInputEditText>(R.id.etQuantity)
        etQuantity.setText(food.serving_qty)

        val calories = getCalories(food)
        etQuantity.addTextChangedListener(object : TextWatcher {
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
                tvCalculator.text = this@SearchFragment.getString(R.string.calorie_calc, quantity, food.serving_unit, calories)
                tvCalorie.text = (quantity.toFloat() * calories.toInt()).toInt().toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        showAlertDialog("Do you want to add: \"${food.food_name}\"?", chooseFoodDialog) {
            if (etQuantity.text.toString().toFloat() <= 0.0) {
                return@showAlertDialog  // Invalid input
            }

            val foodCategory = navigationArgs.foodCategory  // get food category from navigation

            val foodItem = FoodItem(
                food_name = food.food_name,
                serving_qty = etQuantity.text.toString(),
                serving_unit = food.serving_unit,
                calories = (etQuantity.text.toString().toFloat() * getCalories(food).toInt()).toInt().toString(),
                photo = food.photo.thumb,
                category = foodCategory,
                consumed = true // user adds banana for first time, banana is "consumed"
            )
            addFood(foodItem)
        }
    }

    /** Dialog Builder helper: 3 inputs
     * 1. Title
     * 2. View
     * 3. Positive click listener
     * **/
    private fun showAlertDialog(title: String, view: View?, positiveClickListener: View.OnClickListener) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setView(view)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("OK") { _, _ ->
                positiveClickListener.onClick(null)
            }.show()
    }

    private fun addFood(food: FoodItem) {
        viewModel.addFood(food)
    }
}