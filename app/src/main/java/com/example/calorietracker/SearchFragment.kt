package com.example.calorietracker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.calorietracker.adapter.BananaSearchListAdapter
import com.example.calorietracker.databinding.FragmentSearchBinding
import com.example.calorietracker.viewmodels.SearchViewModel
import com.example.calorietracker.viewmodels.SearchViewModelFactory
import kotlinx.android.synthetic.main.dialog_choose_food.view.*


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

        // list item click listener
        val adapter = BananaSearchListAdapter { banana ->
            showChooseFoodUnitDialog()
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

    private fun showChooseFoodUnitDialog() {
        val foodName = binding.svFood.query.toString()
        val chooseFoodDialog = LayoutInflater.from(context).inflate(R.layout.dialog_choose_food, null)
        showAlertDialog(foodName, chooseFoodDialog) {
            // OK listener
            Toast.makeText(context, "Clicked ok", Toast.LENGTH_SHORT).show()
        }
        chooseFoodDialog.etQuantity.requestFocus()
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
}