package com.example.recipeappproject.ui.favourite

//import com.example.project.data.pojo.MealDB
//import com.example.project.data.pojo.MealDetail
//import com.example.project.databinding.FavMealCardBinding


class FavAdapter {}
/*  RecyclerView.Adapter<FavAdapter.FavoriteViewHolder>() {
  private var favoriteMeals: List<MealDB> = ArrayList()
  private lateinit var onFavoriteClickListener: OnFavoriteClickListener
  private lateinit var onFavoriteLongClickListener: OnFavoriteLongClickListener

  fun setFavoriteMealsList(favoriteMeals: List<MealDB>) {
      this.favoriteMeals = favoriteMeals
      notifyDataSetChanged()
  }

  fun getMelaByPosition(position: Int):MealDB{
      return favoriteMeals[position]
  }


  fun setOnFavoriteMealClickListener(onFavoriteClickListener: OnFavoriteClickListener) {
      this.onFavoriteClickListener = onFavoriteClickListener
  }

  fun setOnFavoriteLongClickListener(onFavoriteLongClickListener: OnFavoriteLongClickListener) {
      this.onFavoriteLongClickListener = onFavoriteLongClickListener
  }

  class FavoriteViewHolder(val binding: FavMealCardBinding) :
      RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
      return FavoriteViewHolder(FavMealCardBinding.inflate(LayoutInflater.from(parent.context)))
  }

  override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
      val i = position
      holder.binding.apply {
          tvFavMealName.text = favoriteMeals[position].mealName
          Glide.with(holder.itemView)
              .load(favoriteMeals[position].mealThumb)
              .error(R.drawable.mealtest)
              .into(imgFavMeal)
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
      fun onFavoriteClick(meal: MealDB)
  }

  interface OnFavoriteLongClickListener {
      fun onFavoriteLongCLick(meal: MealDB)
  }
}*/