package com.rodrigobresan.sampleboilerplateandroid.movies.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rodrigobresan.sampleboilerplateandroid.R

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        fun makeIntent(context: Context, id: Long): Intent {
            var intentMovieDetail = Intent(context, MovieDetailActivity::class.java)
            intentMovieDetail.putExtra("id", id)
            return intentMovieDetail
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movie_detail)
    }
}