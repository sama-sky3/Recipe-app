package com.example.recipeappproject.ui.favourite

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeappproject.DataClasses.Meal
import com.example.recipeappproject.R
import com.example.recipeappproject.Repo.FavouriteRepository
import com.example.recipeappproject.databinding.FragmentFavoriteBinding
import com.example.recipeappproject.ui.search.SearchFragmentDirections

class FavoriteFragment : Fragment() {
    val SETTING_PREFRENCE = "com.example.sharedstorageapplication"
    lateinit var sharedPreferences: SharedPreferences
lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: FavAdapter
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var FavMvvm: FavouriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myAdapter = FavAdapter()
        val favouriteRepository = FavouriteRepository(requireContext())

        // Create the ViewModelFactory
        val factory = FavouriteViewModelFactory(favouriteRepository)

        // Get the ViewModel
        FavMvvm = ViewModelProvider(this, factory).get(FavouriteViewModel::class.java)
//         = ViewModelProviders.of(this)[FavouriteViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        binding = FragmentFavoriteBinding.bind(view)

        binding.favRecView.layoutManager = LinearLayoutManager(requireContext())
        binding.favRecView.adapter = myAdapter
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences =
            requireActivity().getSharedPreferences(SETTING_PREFRENCE, MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("loginEmail", "")

        FavMvvm.getFavouriteMeals(userEmail)

        FavMvvm.favouriteMeals.observe(viewLifecycleOwner) { meals ->
            if (meals != null) {
                myAdapter.setFavoriteMealsList(meals)
            }
        }
        myAdapter.setOnFavoriteMealClickListener(object : FavAdapter.OnFavoriteClickListener {
            override fun onFavoriteClick(meal: Meal) {
                var action =  FavoriteFragmentDirections.actionNavigationFavouriteToDetailsFragment(meal.idMeal)
                findNavController().navigate(action)
            }
        })
    }

}
