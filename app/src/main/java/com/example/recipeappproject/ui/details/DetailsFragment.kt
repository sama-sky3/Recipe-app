package com.example.recipeappproject.ui.details

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.recipeappproject.DataClasses.Meal
import com.example.recipeappproject.MainActivity
import com.example.recipeappproject.R
import com.example.recipeappproject.Repo.FavouriteRepository
import com.example.recipeappproject.ui.favourite.FavouriteViewModel
import com.example.recipeappproject.ui.favourite.FavouriteViewModelFactory
import com.ms.square.android.expandabletextview.ExpandableTextView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class DetailsFragment : Fragment() {
    val SETTING_PREFRENCE = "com.example.sharedstorageapplication"
    lateinit var sharedPreferences: SharedPreferences
    lateinit var favouriteViewModel: FavouriteViewModel
    private lateinit var viewModel: DetailsViewModel
    var x: Boolean = false
    lateinit var meal: Meal
    private val args by navArgs<DetailsFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = DetailsViewModelFactory(args.id).create(DetailsViewModel::class.java)

        val favouriteRepository = FavouriteRepository(requireContext())

        // Create the ViewModelFactory
        val factory = FavouriteViewModelFactory(favouriteRepository)

        // Get the ViewModel
        favouriteViewModel = ViewModelProvider(this, factory).get(FavouriteViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.details_fragment_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences =
            requireActivity().getSharedPreferences(SETTING_PREFRENCE, MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("loginEmail", "")
        val expTv: ExpandableTextView = view.findViewById(R.id.expand_text_view)
        val img: ImageView = view.findViewById(R.id.meal_image)
        val playButton: ImageButton = view.findViewById(R.id.button_play_video)
        val favBtn: CheckBox = view.findViewById(R.id.icon)

        var ytUri: Uri? = null



        viewModel.meal.observe(viewLifecycleOwner) {
            ytUri = Uri.parse(it.strYoutube)

            val activity = requireActivity() as AppCompatActivity
            activity.supportActionBar?.title = it.strMeal
            meal = it

            Glide.with(view)
                .load(it.strMealThumb)
                .into(img)

            expTv.text = it.strInstructions

            favBtn.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {

                    favouriteViewModel.addMealToFavorites(userEmail!!, meal)

                } else {

                    favouriteViewModel.removeMealFromFavorites(userEmail!!, meal)
                }
            }

        favouriteViewModel.getFavouriteMeals(userEmail)

        favouriteViewModel.favouriteMeals.observe(viewLifecycleOwner) { meals ->
            if (meals.isNotEmpty()) {
                if (meals.contains(meal)) {
                    Log.i("TAG", "onViewCreated: exist ")
                    favBtn.isChecked = true

                } else {
                    favBtn.isChecked = false
                    Log.i("TAG", "onViewCreated: not exist ")
                }
            } }
        }





        playButton.setOnClickListener {
            val youtubeId = ytUri?.getQueryParameter("v")

            if (youtubeId == null) {
                Toast.makeText(requireContext(), "video id loading", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            createPopUp(youtubeId)
        }


    }

    fun createPopUp(youtubeId: String) {
        // Inflate the video view layout
        val inflater: LayoutInflater =
            requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.video_popup_view, null)

        val youTubePlayerView: YouTubePlayerView = popupView.findViewById(R.id.youtube_player_view)
        viewLifecycleOwner.lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(youtubeId, 0f)
            }
        })
        // Create the PopupWindow
        val popupWindow = PopupWindow(
            popupView,
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT,
            true
        )

        // Show the PopupWindow
        popupWindow.showAtLocation(
            requireView().findViewById(R.id.root_view),
            Gravity.CENTER, 0, 0
        )

        popupWindow.setOnDismissListener {
            viewLifecycleOwner.lifecycle.removeObserver(youTubePlayerView)
        }
    }
}