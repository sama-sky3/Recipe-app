package com.example.recipeappproject.ui.details

import android.content.Context
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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.recipeappproject.R
import com.ms.square.android.expandabletextview.ExpandableTextView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class DetailsFragment : Fragment() {

    private lateinit var viewModel:DetailsViewModel
    private val args by navArgs<DetailsFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = DetailsViewModelFactory(args.id).create(DetailsViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.details_fragment_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val expTv: ExpandableTextView = view.findViewById(R.id.expand_text_view)
        val img: ImageView = view.findViewById(R.id.meal_image)
        val playButton: ImageButton = view.findViewById(R.id.button_play_video)
        val favBtn: CheckBox = view.findViewById(R.id.icon)

        var ytUri: Uri? = null

        viewModel.meal.observe(viewLifecycleOwner){
            Log.i("DetailsFragment","youtube error ${it}")
                ytUri = Uri.parse(it.strYoutube)


            Glide.with(view)
                .load(it.strMealThumb)
                .into(img)

            expTv.text =   it.strInstructions
        }

        playButton.setOnClickListener {
            val youtubeId = ytUri?.getQueryParameter("v")

            if( youtubeId == null) {
                Toast.makeText(requireContext(), "video id loading", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            createPopUp(youtubeId)
        }

        favBtn.setOnCheckedChangeListener() { _, _ ->
            // Respond to icon toggle
            //TODO
        }
    }

    fun createPopUp (youtubeId:String){
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