package com.example.recipeappproject.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeappproject.DataClasses.Meal
import com.example.recipeappproject.R
import com.example.recipeappproject.databinding.FragmentSearchBinding
import com.example.recipeappproject.ui.home.HomeFragmentDirections

class SearchFragment : Fragment() {
    lateinit var searchView: SearchView
    private lateinit var myAdapter: MealListAdapter
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchMvvm: SearchViewModel
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myAdapter = MealListAdapter()
        searchMvvm = ViewModelProviders.of(this)[SearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        binding = FragmentSearchBinding.bind(view)

        // Initialize with an empty list
        binding.recyclerView1.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView1.adapter = myAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe LiveData once
        searchMvvm.observeSearchLiveData().observe(viewLifecycleOwner) { mealList ->
            if (mealList.isNullOrEmpty()) {
                Log.i("TAG", "onQueryTextSubmit: no Match")
            } else {
                Log.i("TAG", "Data received: ${mealList.size} items")
                myAdapter.setCategoryList(mealList)
                binding.recyclerView1.adapter = myAdapter
            }
        }

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchMvvm.searchMealDetail(it, requireContext())
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchMvvm.searchMealDetail(newText, requireContext())
                return true
            }
        })

        myAdapter.setOnMealClickListener(object : MealListAdapter.SetOnMealClickListener {
            override fun setOnClickListener(meal: Meal) {
                // navigate to details Fragment
                var action =  SearchFragmentDirections.actionNavigationSearchToDetailsFragment(meal.idMeal)
                findNavController().navigate(action)
            }
        })


    }
}
