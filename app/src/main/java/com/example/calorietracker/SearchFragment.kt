package com.example.calorietracker

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.calorietracker.adapter.BananaSearchListAdapter
import com.example.calorietracker.data.FoodApplication
import com.example.calorietracker.data.FoodItem
import com.example.calorietracker.databinding.FragmentSearchBinding
import com.example.calorietracker.utility.convertToFoodItem
import com.example.calorietracker.viewmodels.SearchViewModel
import com.example.calorietracker.viewmodels.SearchViewModelFactory


class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by activityViewModels {
        SearchViewModelFactory(
            (activity?.application as FoodApplication).database.foodDao()
        )
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val navigationArgs: SearchFragmentArgs by navArgs()

    private var _currentCategory: String? = null
    private val currentCategory get() = _currentCategory!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // show menu
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _currentCategory = navigationArgs.foodCategory  // "breakfast"

        // list item click listener
        val adapter = BananaSearchListAdapter { banana ->
            val foodItem = convertToFoodItem(banana, currentCategory)
            goToFoodDetailFragment(foodItem)
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
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_create_food, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_create -> {
                goToCreateFragment()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToCreateFragment() {
        val action =
            SearchFragmentDirections.actionSearchFragmentToCreateFoodFragment(
                foodCategory = currentCategory
            )
        findNavController().navigate(action)

    }

    private fun goToFoodDetailFragment(item: FoodItem) {
        val action =
            SearchFragmentDirections.actionSearchFragmentToFoodDetailFragment(
                foodItem = item)
        findNavController().navigate(action)
    }
}