package com.example.recipeappproject.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeappproject.DataClasses.Category
import com.example.recipeappproject.R

class HomeAdapterCategories(private val onItemClick: (category: String) -> Unit) :
    RecyclerView.Adapter<HomeAdapterCategories.RecipeViewHolder>() {

    private var arrMainCategory = listOf<Category>()

    class RecipeViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvCategoryName: TextView = view.findViewById(R.id.tvCategoryName)
        val imgCategory: ImageView = view.findViewById(R.id.img_category)
    }

    fun setData(arrData: List<Category>) {
        arrMainCategory = arrData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_categories, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrMainCategory.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.tvCategoryName.text = arrMainCategory[position].strCategory

        Glide.with(holder.view).load(arrMainCategory[position].strCategoryThumb)
            .into(holder.imgCategory)

        holder.itemView.rootView.setOnClickListener {
            onItemClick(arrMainCategory[position].strCategory)
        }
    }
}