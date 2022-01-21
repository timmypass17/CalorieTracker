package com.example.calorietracker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.calorietracker.adapter.BananaSearchListAdapter
import com.example.calorietracker.databinding.FragmentSearchBinding
import com.example.calorietracker.viewmodels.SearchViewModel
import com.example.calorietracker.viewmodels.SearchViewModelFactory


class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by activityViewModels { SearchViewModelFactory() }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = BananaSearchListAdapter { banana ->
            Log.i("Search Fragment", banana.description)
        }

        // data binding
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.rvBananas.adapter = adapter

        // onclick listeners
        binding.svFood.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i("SearchFragment", "$query")
                if (query != null) {
                    binding.tvResult.text = "Searching for: \"$query\"/"
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
}