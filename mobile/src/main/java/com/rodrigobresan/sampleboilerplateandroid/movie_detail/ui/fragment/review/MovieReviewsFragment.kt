package com.rodrigobresan.sampleboilerplateandroid.movie_detail.ui.fragment.review

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.rodrigobresan.domain.review.model.Review
import com.rodrigobresan.presentation.movie_details.model.MovieDetailView
import com.rodrigobresan.sampleboilerplateandroid.R
import com.rodrigobresan.sampleboilerplateandroid.movie_detail.ui.ReviewAdapter
import com.rodrigobresan.sampleboilerplateandroid.movie_detail.ui.fragment.overview.MovieOverviewFragment
import kotlinx.android.synthetic.main.fragment_movie_reviews.*

class MovieReviewsFragment : Fragment() {

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movie_reviews, container, false)
        return view
    }

    fun loadReviews(review: List<Review>) {
        val adapter = ReviewAdapter()
        adapter.reviewList = review
        val layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        rv_movie_reviews.layoutManager = layoutManager
        rv_movie_reviews.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}