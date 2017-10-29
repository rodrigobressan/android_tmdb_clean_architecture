package com.rodrigobresan.sampleboilerplateandroid.movies.ui.activity

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.rodrigobresan.sampleboilerplateandroid.R
import com.rodrigobresan.sampleboilerplateandroid.movies.model.MovieViewModel
import com.rodrigobresan.sampleboilerplateandroid.movies.ui.adapter.MoviesAdapter
import kotlinx.android.synthetic.main.view_movie_section.view.*

class MovieSectionView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
        defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    fun showProgress() {
        progress_movies.visibility = VISIBLE
    }

    fun hideProgress() {
        progress_movies.visibility = GONE
    }

    fun showErrorState() {
        txt_movie_error.visibility = VISIBLE
    }

    fun hideErrorState() {
        txt_movie_error.visibility = GONE
    }

    fun showEmptyState() {
        txt_movie_empty.visibility = VISIBLE
    }

    fun hideEmptyState() {
        txt_movie_empty.visibility = GONE
    }

    fun showMovies(moviesAdapter: MoviesAdapter, movies: List<MovieViewModel>) {
        moviesAdapter.listItems = movies
        rv_movies.visibility = View.VISIBLE
        rv_movies.adapter = moviesAdapter
        moviesAdapter.notifyDataSetChanged()
    }

    fun hideMovies() {
        rv_movies.visibility = View.INVISIBLE
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_movie_section, this, true)
        orientation = VERTICAL

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.custom_component_attributes, 0, 0)
            val title = resources.getText(typedArray
                    .getResourceId(R.styleable.custom_component_attributes_custom_component_title, R.string.default_value_movie_section))

            txt_movie_section.text = title

            typedArray.recycle()

            rv_movies.layoutManager = LinearLayoutManager(context,
                    LinearLayoutManager.HORIZONTAL, false)
        }
    }
}