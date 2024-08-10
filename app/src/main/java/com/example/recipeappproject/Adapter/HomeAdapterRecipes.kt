package com.example.recipeappproject.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeappproject.DataClasses.Meal
import com.example.recipeappproject.DataClasses.MealsByCategory
import com.example.recipeappproject.R

class HomeAdapterRecipes : RecyclerView.Adapter<HomeAdapterRecipes.RecipeViewHolder>() {

    var listner: OnItemClickListener? = null
    var ctx : Context? = null
    var arrSubCategory = ArrayList<MealsByCategory>()  // Change this to MealsByCategory

    class RecipeViewHolder(view : View) :RecyclerView.ViewHolder(view){
        val mealName: TextView = view.findViewById(R.id.mealName)
        val img_meal: ImageView = view.findViewById(R.id.img_meal)
    }

    fun setData(arrData: List<MealsByCategory>){
        arrSubCategory = arrData as ArrayList<MealsByCategory>  // No casting needed now
        notifyDataSetChanged()  // Notify the adapter that the data has changed

    }

    fun setClickListner(listner1: OnItemClickListener){
        listner = listner1
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecipeViewHolder {
        ctx = parent.context
        return RecipeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_recipes,parent,false))
    }

    override fun getItemCount(): Int {
        return arrSubCategory.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.mealName.text = arrSubCategory[position].strMeal

        Glide.with(ctx!!).load(arrSubCategory[position].strMealThumb).into(holder.img_meal)

        holder.itemView.rootView.setOnClickListener {
            listner!!.onClicked(arrSubCategory[position].idMeal)
        }
    }

    interface OnItemClickListener{
        fun onClicked(id :String)
    }

}