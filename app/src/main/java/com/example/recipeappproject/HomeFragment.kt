package com.example.recipeappproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeappproject.Adapter.HomeAdapterCategories
import com.example.recipeappproject.Adapter.HomeAdapterRecipes
import com.example.recipeappproject.Retrofit.MealApi
import com.example.recipeappproject.ViewModel.HomeViewModel
import com.example.recipeappproject.R


class HomeFragment : Fragment() {
    private lateinit var homeVM : HomeViewModel
    private lateinit var categoriesAdapter : HomeAdapterCategories
    private lateinit var recipesAdapter : HomeAdapterRecipes
    private lateinit var tvCateg: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeVM = ViewModelProviders.of(this)[HomeViewModel::class.java]
        categoriesAdapter = HomeAdapterCategories()
        recipesAdapter = HomeAdapterRecipes()


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
        homeVM.getAllCategories()
        observeCategories()

        val categoriesRecyclerView: RecyclerView = view.findViewById(R.id.mainCateg)
        val recipesRecyclerView: RecyclerView = view.findViewById(R.id.subCateg)
        tvCateg = view.findViewById(R.id.tvCateg)


        categoriesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recipesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recipesRecyclerView.setHasFixedSize(true)

        categoriesRecyclerView.adapter = categoriesAdapter
        recipesRecyclerView.adapter = recipesAdapter

        onClicked.onClicked("Beef")
        homeVM.getAllCategories()
        observeCategories()
        categoriesAdapter.setClickListner(onClicked)
        //recipesAdapter.setClickListner(onClickedSub)


    }

    private fun observeCategories() {
        homeVM.observeCategories().observe(viewLifecycleOwner, { categories ->
            categoriesAdapter.setData(categories)
            categoriesAdapter.notifyDataSetChanged() // Notify adapter of data change
        })
    }

    private fun observeMeal() {
        homeVM.observeMeal().observe(viewLifecycleOwner, { meals ->
            meals?.let {
                if (it.isNotEmpty()) {
                    recipesAdapter.setData(it)
                    recipesAdapter.notifyDataSetChanged()
                    Log.d("HomeFragment", "Meals displayed: ${it.size}")
                } else {
                    Log.d("HomeFragment", "No meals found for this category")
                }
            }
        })
    }

    private val onClicked = object : HomeAdapterCategories.OnItemClickListener{
        @SuppressLint("SetTextI18n")
        override fun onClicked(categoryName: String) {
            homeVM.getMealsByCategory(categoryName)
            tvCateg.text = "$categoryName  Recipes"
            observeMeal()
        }

    }

    /*private val onClickedSub = object : HomeAdapterRecipes.OnItemClickListener{
        override fun onClicked(id: String) {
            var intent = Intent( this ,DetailActivity::class.java)
            intent.putExtra("id",id)
            startActivity(intent)}*/
        }

