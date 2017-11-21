package com.rodrigobresan.sampleboilerplateandroid.movie_detail.ui.fragment.overview

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rodrigobresan.presentation.movie_details.model.MovieDetailView
import com.rodrigobresan.sampleboilerplateandroid.R
import kotlinx.android.synthetic.main.fragment_movie_overview.*

class MovieOverviewFragment : Fragment() {

    companion object Factory {
        val EXTRA_MOVIE = "extra_movie"
        fun newInstance(movieDetailView: MovieDetailView): MovieOverviewFragment {
            val bundle = Bundle()
            bundle.putSerializable(EXTRA_MOVIE, movieDetailView)

            val fragment = MovieOverviewFragment()
            fragment.arguments = bundle

            return fragment
        }
    }

    private var favoriteListener: OnMovieFavoriteListener? = null

    interface OnMovieFavoriteListener {
        fun favoriteMovie()
        fun unfavoriteMovie()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnMovieFavoriteListener) {
            this.favoriteListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnMovieFavoriteListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_movie_overview, container, false)
        return view
    }

    private lateinit var movieDetail: MovieDetailView

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieDetail = arguments.getSerializable(EXTRA_MOVIE) as MovieDetailView
        txt_movie_overview.text = movieDetail.overview
        txt_movie_tagline.text = movieDetail.tagline
        txt_movie_rating.text = movieDetail.voteAverage.toString()
        txt_movie_detail_title.text = movieDetail.title

        if (movieDetail.isFavorite) {
            img_movie_star.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary))
        } else {
            img_movie_star.setColorFilter(ContextCompat.getColor(context, R.color.grey))
        }

        img_movie_star.setOnClickListener({
            handleFavoriteClick()
        })
    }

    fun handleFavoriteClick() {
        if (movieDetail.isFavorite) {
            img_movie_star.setColorFilter(ContextCompat.getColor(context, R.color.grey))
            movieDetail.isFavorite = false
            favoriteListener?.unfavoriteMovie()
        } else {
            img_movie_star.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary))
            movieDetail.isFavorite = true
            favoriteListener?.favoriteMovie()
        }
    }
}