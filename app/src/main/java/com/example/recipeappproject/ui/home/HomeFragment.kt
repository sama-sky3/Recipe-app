package com.example.recipeappproject.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeappproject.DataClasses.Meal
import com.example.recipeappproject.DataClasses.MealsByCategory
import com.example.recipeappproject.R


class HomeFragment : Fragment() {
    private lateinit var homeVM: HomeViewModel
    private lateinit var categoriesAdapter: HomeAdapterCategories
    private lateinit var recipesAdapter: HomeAdapterRecipes
    private lateinit var tvCateg: TextView

    private fun onCategoryItemClick(category: String) {
        homeVM.getMealsByCategory(category)
        tvCateg.text = "$category Recipes"
    }

    private fun onRecipeItemClick(meal:MealsByCategory) {
       var action =  HomeFragmentDirections.actionNavigationHomeToDetailsFragment(meal.idMeal)
        findNavController().navigate(action)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeVM = ViewModelProviders.of(this)[HomeViewModel::class.java]
        categoriesAdapter = HomeAdapterCategories(onItemClick = { onCategoryItemClick(it) })
        recipesAdapter = HomeAdapterRecipes(onItemClick = {onRecipeItemClick(it)})
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeCategories()
        observeMeal()

        val categoriesRecyclerView: RecyclerView = view.findViewById(R.id.mainCateg)
        val recipesRecyclerView: RecyclerView = view.findViewById(R.id.subCateg)

        tvCateg = view.findViewById(R.id.tvCateg)

        categoriesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recipesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recipesRecyclerView.setHasFixedSize(true)

        categoriesRecyclerView.adapter = categoriesAdapter
        recipesRecyclerView.adapter = recipesAdapter

        onCategoryItemClick("Beef")
    }

    private fun observeCategories() {
        homeVM.observeCategories().observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.setData(categories)
            categoriesAdapter.notifyDataSetChanged() // Notify adapter of data change
        }
    }

    private fun observeMeal() {
        homeVM.observeMeal().observe(viewLifecycleOwner) { meals ->
            meals?.let {
                if (it.isNotEmpty()) {
                    recipesAdapter.setData(it)
                    recipesAdapter.notifyDataSetChanged()
                    Log.d("HomeFragment", "Meals displayed: ${it.size}")
                } else {
                    Log.d("HomeFragment", "No meals found for this category")
                }
            }
        }
    }


    /*private val onClickedSub = object : HomeAdapterRecipes.OnItemClickListener{
        override fun onClicked(id: String) {
            var intent = Intent( this ,DetailActivity::class.java)
            intent.putExtra("id",id)
            startActivity(intent)}*/
}

