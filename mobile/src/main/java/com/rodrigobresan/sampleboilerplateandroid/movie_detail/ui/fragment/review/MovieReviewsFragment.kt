package com.rodrigobresan.sampleboilerplateandroid.movie_detail.ui.fragment.review

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rodrigobresan.sampleboilerplateandroid.R

class MovieReviewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movie_reviews, container, false)
        return view
    }
}