package com.example.calorietracker

import android.content.Context
import android.graphics.drawable.RotateDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.calorietracker.adapter.FoodListAdapter
import com.example.calorietracker.data.FoodApplication
import com.example.calorietracker.data.FoodItem
import com.example.calorietracker.databinding.FragmentHomeBinding
import com.example.calorietracker.viewmodels.FoodListViewModel
import com.example.calorietracker.viewmodels.FoodListViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val foodListViewModel: FoodListViewModel by activityViewModels {
        FoodListViewModelFactory(
            (activity?.application as FoodApplication).database.foodDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // show menu
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SharedPref
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultCalorieGoal = resources.getInteger(R.integer.saved_calorie_goal_default_key)
        val calorieGoal = sharedPref.getInt(getString(R.string.saved_calorie_goal_key), defaultCalorieGoal)
        val defaultProteinGoal = resources.getInteger(R.integer.saved_protein_goal_default_key)
        val proteinGoal = sharedPref.getInt(getString(R.string.saved_protein_goal_key), defaultProteinGoal)
        val defaultCarbsGoal = resources.getInteger(R.integer.saved_carbs_goal_default_key)
        val carbsGoal = sharedPref.getInt(getString(R.string.saved_carbs_goal_key), defaultCarbsGoal)
        val defaultFatGoal = resources.getInteger(R.integer.saved_fat_goal_default_key)
        val fatGoal = sharedPref.getInt(getString(R.string.saved_fat_goal_key), defaultFatGoal)

        val breakFastAdapter = FoodListAdapter(foodListViewModel) { foodItem ->
            // food item onclick
            goToFoodDetailFragment(foodItem)
        }

        val lunchAdapter = FoodListAdapter(foodListViewModel) { foodItem ->
            // food item onclick
            goToFoodDetailFragment(foodItem)
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            binding.viewModel = foodListViewModel
            binding.calorieGoal = calorieGoal
            binding.proteinGoal = proteinGoal
            binding.carbsGoal = carbsGoal
            binding.fatGoal = fatGoal

            rvBreakfast.adapter = breakFastAdapter
            rvLunch.adapter = lunchAdapter

            btnAddBreakfast.setOnClickListener { goToSearchFragment("breakfast") }
            btnAddLunch.setOnClickListener { goToSearchFragment("lunch") }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                goToSettingsFragment()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToSearchFragment(category: String) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToSearchFragment(
                foodCategory = category)
        findNavController().navigate(action)
    }

    private fun goToFoodDetailFragment(item: FoodItem) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToFoodDetailFragment(
                foodItem = item)
        findNavController().navigate(action)
    }

    private fun goToSettingsFragment() {
        val action =
            HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
        findNavController().navigate(action)
    }

}