package com.example.recipeappproject.ui.favourite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeappproject.DataClasses.Meal
import com.example.recipeappproject.databinding.HomeRecipesBinding




class FavAdapter : RecyclerView.Adapter<FavAdapter.FavoriteViewHolder>() {
  private var favoriteMeals: List<Meal> = ArrayList()
  private lateinit var onFavoriteClickListener: OnFavoriteClickListener
  private lateinit var onFavoriteLongClickListener: OnFavoriteLongClickListener

  fun setFavoriteMealsList(favoriteMeals: List<Meal>) {
      this.favoriteMeals = favoriteMeals
      notifyDataSetChanged()
  }

  fun getMelaByPosition(position: Int):Meal{
      return favoriteMeals[position]
  }


  fun setOnFavoriteMealClickListener(onFavoriteClickListener: OnFavoriteClickListener) {
      this.onFavoriteClickListener = onFavoriteClickListener
  }


  class FavoriteViewHolder(val binding: HomeRecipesBinding) :
      RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
      return FavoriteViewHolder(HomeRecipesBinding.inflate(LayoutInflater.from(parent.context)))
  }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
      val i = position
      holder.binding.apply {
          mealName.text = favoriteMeals[position].strMeal
          Glide.with(holder.itemView)
              .load(favoriteMeals[position].strMealThumb)
              .into(imgMeal)

      }

      holder.itemView.setOnClickListener {
          onFavoriteClickListener.onFavoriteClick(favoriteMeals[position])
      }

      holder.itemView.setOnLongClickListener(object : View.OnLongClickListener {
          override fun onLongClick(p0: View?): Boolean {
              onFavoriteLongClickListener.onFavoriteLongCLick(favoriteMeals[i])
              return true
          }
      })
  }

  override fun getItemCount(): Int {
      return favoriteMeals.size
  }

  interface OnFavoriteClickListener {
      fun onFavoriteClick(meal: Meal)
  }

  interface OnFavoriteLongClickListener {
      fun onFavoriteLongCLick(meal: Meal)
  }
}