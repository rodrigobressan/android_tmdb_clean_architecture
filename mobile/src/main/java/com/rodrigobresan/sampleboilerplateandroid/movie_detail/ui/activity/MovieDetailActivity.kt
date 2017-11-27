package com.rodrigobresan.sampleboilerplateandroid.movie_detail.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.rodrigobresan.data.movie_detail.mapper.MovieDetailMapper
import com.rodrigobresan.presentation.movie_details.contract.MovieDetailsContract
import com.rodrigobresan.presentation.movie_details.model.MovieDetailView
import com.rodrigobresan.sampleboilerplateandroid.R
import com.rodrigobresan.sampleboilerplateandroid.movie_detail.ui.fragment.overview.MovieOverviewFragment
import com.rodrigobresan.sampleboilerplateandroid.movie_detail.ui.fragment.review.MovieReviewsFragment
import com.rodrigobresan.sampleboilerplateandroid.movie_detail.ui.view_pager.ViewPagerAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_movie_detail.*
import javax.inject.Inject

class MovieDetailActivity : AppCompatActivity(), MovieDetailsContract.View, MovieOverviewFragment.OnMovieFavoriteListener {

    @Inject lateinit var movieDetailPresenter: MovieDetailsContract.Presenter
    @Inject lateinit var movieDetailMapper: MovieDetailMapper

    var movieId: Long = 0

    companion object Factory {
        val MOVIE_ID_EXTRA = "extra_movie_detail_movie_id"
        fun makeIntent(context: Context, id: Long): Intent {
            val intentMovieDetail = Intent(context, MovieDetailActivity::class.java)
            intentMovieDetail.putExtra(MOVIE_ID_EXTRA, id)
            return intentMovieDetail
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movie_detail)
        AndroidInjection.inject(this)

        retrieveReceivedMovieId()
        setUpToolbar()
        setUpTabLayout()
    }

    private fun retrieveReceivedMovieId() {
        movieId = intent.getLongExtra(MOVIE_ID_EXTRA, 0)
    }

    override fun onStart() {
        super.onStart()
        movieDetailPresenter.loadMovieDetails(movieId)
    }

    override fun setPresenter(presenter: MovieDetailsContract.Presenter) {
        this.movieDetailPresenter = presenter
    }

    override fun favoriteMovie() {
        this.movieDetailPresenter.favoriteMovie(movieId)
    }

    override fun unfavoriteMovie() {
        movieDetailPresenter.unfavoriteMovie(movieId)
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun showErrorState() {
    }

    override fun hideErrorState() {
    }

    override fun showEmptyState() {
        img_movie_detail_no_connection.visibility = View.VISIBLE
    }

    override fun hideEmptyState() {
        img_movie_detail_no_connection.visibility = View.GONE
    }

    override fun showOfflineModeCachedData() {
        showNoConnectionSnackbar(getString(R.string.no_connection_cached))
    }

    override fun showOfflineModeNoCachedData() {
        showNoConnectionSnackbar(getString(R.string.no_connection_not_cached))
    }

    private fun showNoConnectionSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", {
                    movieDetailPresenter.loadMovieDetails(movieId)
                })
                .setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .show()
    }

    @SuppressLint("ResourceAsColor")
    override fun showMovieDetails(movieDetail: MovieDetailView) {
        Glide.with(this)
                .load(movieDetail.posterPath)
                .listener(GlidePalette.with(movieDetail.posterPath)
                        .use(BitmapPalette.Profile.VIBRANT).intoCallBack {
                    if (it != null) {
                        ct_movie_details.setContentScrimColor(it.getVibrantColor(R.color.colorPrimary))
                        ct_movie_details.setStatusBarScrimColor(it.getVibrantColor(R.color.colorPrimary_700))
                    }
                }
                )
                .into(img_movie_details_header)

        setUpViewPager(movieDetail)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("RestrictedApi")
    private fun setUpToolbar() {
        val toolbar = toolbar_movie_details as Toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).setDisplayHomeAsUpEnabled(true)
            (supportActionBar as ActionBar).setDisplayShowTitleEnabled(false)
        }

        // we need to remove the state list animator due to some crash with API >= 21. Feel free to check
        // some answers for the issue here: https://github.com/lapism/SearchView/issues/195
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appbar_movie_details.stateListAnimator = null
            appbar_movie_details.elevation = 0F
        }
    }

    private fun setUpTabLayout() {
        tabs_movie_details.setupWithViewPager(vp_movie_details)
        tabs_movie_details.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
                vp_movie_details.currentItem = tab.position
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun setUpViewPager(movieDetail: MovieDetailView) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MovieOverviewFragment.newInstance(movieDetail), getString(R.string.movie_detail_section_overview))
        adapter.addFragment(MovieReviewsFragment(), getString(R.string.movie_detail_section_reviews))

        vp_movie_details.adapter = adapter
    }
}