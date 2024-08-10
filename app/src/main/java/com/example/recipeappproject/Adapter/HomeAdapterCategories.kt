package com.example.recipeappproject.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeappproject.DataClasses.Category
import com.example.recipeappproject.R

class HomeAdapterCategories: RecyclerView.Adapter<HomeAdapterCategories.RecipeViewHolder>() {

    var listner: OnItemClickListener? = null
    var ctx : Context? = null
    var arrMainCategory = ArrayList<Category>()

    class RecipeViewHolder(view: View) :RecyclerView.ViewHolder(view){
        val tvCategoryName: TextView = view.findViewById(R.id.tvCategoryName)
        val img_category: ImageView = view.findViewById(R.id.img_category)
    }

    fun setData(arrData : List<Category>){
        arrMainCategory = arrData as ArrayList<Category>
    }

    fun setClickListner(listner1:OnItemClickListener){
        listner = listner1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        ctx = parent.context
        return RecipeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_categories,parent,false))
    }

    override fun getItemCount(): Int {
        return arrMainCategory.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.tvCategoryName.setText(arrMainCategory[position].strCategory)

        Glide.with(ctx!!).load(arrMainCategory[position].strCategoryThumb).into(holder.img_category)

        holder.itemView.rootView.setOnClickListener {
            listner!!.onClicked(arrMainCategory[position].strCategory)
        }
    }

    interface OnItemClickListener{
        fun onClicked(categoryName :String)
    }
}