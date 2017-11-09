package com.rodrigobresan.sampleboilerplateandroid.movie_detail.ui.fragment.overview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rodrigobresan.presentation.movie_details.model.MovieDetailView
import com.rodrigobresan.sampleboilerplateandroid.R
import kotlinx.android.synthetic.main.fragment_movie_overview.*
import kotlinx.android.synthetic.main.item_movie.*

class MovieOverviewFragment : Fragment() {

    companion object Factory {
        val EXTRA_MOVIE_OVERVIEW = "extra_movie_overview"
        val EXTRA_MOVIE_TAGLINE = "extra_movie_tagline"
        val EXTRA_MOVIE_VOTE_AVERAGE = "extra_movie_vote_average"
        val EXTRA_MOVIE_TITLE = "extra_movie_title"

        fun newInstance(movieDetailView: MovieDetailView): MovieOverviewFragment {
            val bundle = Bundle()
            bundle.putString(EXTRA_MOVIE_TITLE, movieDetailView.title)
            bundle.putString(EXTRA_MOVIE_OVERVIEW, movieDetailView.overview)
            bundle.putString(EXTRA_MOVIE_TAGLINE, movieDetailView.tagline)
            bundle.putDouble(EXTRA_MOVIE_VOTE_AVERAGE, movieDetailView.voteAverage)

            val fragment = MovieOverviewFragment()
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_movie_overview, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieTitle = arguments.getString(EXTRA_MOVIE_TITLE)
        val movieOverview = arguments.getString(EXTRA_MOVIE_OVERVIEW)
        val movieTagline = arguments.getString(EXTRA_MOVIE_TAGLINE)
        val movieRating = arguments.getDouble(EXTRA_MOVIE_VOTE_AVERAGE)

        txt_movie_overview.text = movieOverview
        txt_movie_tagline.text = movieTagline
        txt_movie_rating.text = movieRating.toString()
        txt_movie_detail_title.text = movieTitle
    }
}