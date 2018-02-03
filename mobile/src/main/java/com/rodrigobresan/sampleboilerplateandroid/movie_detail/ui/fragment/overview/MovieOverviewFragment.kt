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

    private var listener: MovieOverviewListener? = null

    interface MovieOverviewListener {
        fun favoriteMovie()
        fun unfavoriteMovie()
        fun loaded()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is MovieOverviewListener) {
            this.listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement MovieOverviewListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_movie_overview, container, false)
        return view
    }

    private lateinit var movieDetail: MovieDetailView

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener?.loaded()
    }

    fun setMovieDetails(movieDetail: MovieDetailView) {
        this.movieDetail = movieDetail
        txt_movie_overview.text = movieDetail.overview
        txt_movie_tagline.text = movieDetail.tagline
        txt_movie_rating.text = movieDetail.voteAverage.toString()
        txt_movie_detail_title.text = movieDetail.title

        if (movieDetail.isFavorite) {
            showMovieAsFavorite()
        } else {
            showMovieAsNotFavorite()
        }

        img_movie_star.setOnClickListener({
            handleFavoriteClick()
        })
    }

    fun showMovieAsFavorite() {
        img_movie_star.animate()
                .scaleY(1.5f)
                .scaleX(1.5f)
                .setDuration(150)
                .start()
        img_movie_star.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary))
    }

    fun showMovieAsNotFavorite() {
        img_movie_star.animate()
                .scaleY(1f)
                .scaleX(1f)
                .setDuration(150)
                .start()
        img_movie_star.setColorFilter(ContextCompat.getColor(context, R.color.grey))
    }

    fun handleFavoriteClick() {
        if (movieDetail.isFavorite) {
            showMovieAsNotFavorite()

            movieDetail.isFavorite = true
            listener?.favoriteMovie()
        } else {
            showMovieAsFavorite()

            movieDetail.isFavorite = false
            listener?.unfavoriteMovie()
        }
    }
}