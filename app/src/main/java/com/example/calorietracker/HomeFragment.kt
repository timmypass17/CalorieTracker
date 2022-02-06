package com.example.calorietracker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.calorietracker.adapter.FoodListAdapter
import com.example.calorietracker.data.FoodApplication
import com.example.calorietracker.databinding.FragmentHomeBinding
import com.example.calorietracker.viewmodels.FoodListViewModel
import com.example.calorietracker.viewmodels.FoodListViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FoodListViewModel by activityViewModels {
        FoodListViewModelFactory(
            (activity?.application as FoodApplication).database.foodDao()
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FoodListAdapter { foodItem ->
            // food item onclick
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            binding.breakFastViewModel = viewModel
            binding.rvBreakfast.adapter = adapter
            btnBanana.setOnClickListener { goToSearchFragment() }
        }
    }

    private fun goToSearchFragment() {
        val action =
            HomeFragmentDirections.actionHomeFragmentToSearchFragment()
        findNavController().navigate(action)
    }
}