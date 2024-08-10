package com.example.recipeappproject.Adapter


import android.util.Log
import com.example.recipeappproject.DataClasses.Meal
import com.example.recipeappproject.databinding.HomeRecipesBinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class  MealListAdapter : RecyclerView.Adapter<MealListAdapter.MealViewHolder>() {

        private var mealList: List<Meal> = ArrayList()
        private lateinit var setOnMealClickListener: SetOnMealClickListener

        fun setCategoryList(mealList: List<Meal>) {
            this.mealList = mealList
        }

        fun setOnMealClickListener(setOnMealClickListener: SetOnMealClickListener) {
            this.setOnMealClickListener = setOnMealClickListener
        }

        class MealViewHolder(val binding: HomeRecipesBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
            return MealViewHolder(HomeRecipesBinding.inflate(LayoutInflater.from(parent.context)))
        }

        override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
            holder.binding.apply {
                mealName.text = mealList[position].strMeal
                Glide.with(holder.itemView)
                    .load(mealList[position].strMealThumb)
                    .into(imgMeal)
                Log.i("TAG", "onBindViewHolder: ")
            }

            holder.itemView.setOnClickListener {
                setOnMealClickListener.setOnClickListener(mealList[position])
            }
        }

        override fun getItemCount(): Int {
            return mealList.size
            notifyDataSetChanged()
        }


    interface SetOnMealClickListener {
        fun setOnClickListener(meal: Meal)
    }
}