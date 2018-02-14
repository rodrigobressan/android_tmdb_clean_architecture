package com.rodrigobresan.tv.movie_details;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.app.DetailsFragmentBackgroundController;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewSharedElementHelper;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.OnActionClickedListener;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.rodrigobresan.domain.review.model.Review;
import com.rodrigobresan.presentation.movie_details.contract.MovieDetailsContract;
import com.rodrigobresan.presentation.movie_details.model.MovieDetailView;
import com.rodrigobresan.tv.R;
import com.rodrigobresan.tv.movie_details.mapper.MovieDetailsMapper;
import com.rodrigobresan.tv.movie_details.model.MovieDetailViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MovieDetailsFragment extends DetailsFragment implements MovieDetailsContract.View {
    private static final String TAG = "MovieDetailsFragment";

    @Inject
    MovieDetailsContract.Presenter presenter;

    @Inject
    MovieDetailsMapper movieMapper;

    private static final int ACTION_OVERVIEW = 1;

    private ArrayObjectAdapter mAdapter;
    private ClassPresenterSelector mPresenterSelector;

    private DetailsFragmentBackgroundController mDetailsBackground;
    private MovieDetailViewModel movieDetails;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidInjection.inject(this);

        presenter.loadMovieDetails(getActivity().getIntent().getLongExtra(DetailsActivity.MOVIE_ID, 0));
        mDetailsBackground = new DetailsFragmentBackgroundController(this);
    }

    private void initializeBackground(MovieDetailViewModel data) {
        mDetailsBackground.enableParallax();
        Glide.with(getActivity())
                .asBitmap()
                .load(data.getBackdropPath())
                .apply(new RequestOptions().centerCrop())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        mDetailsBackground.setCoverBitmap(resource);
                        mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size());
                    }
                });
    }

    private void setupDetailsOverviewRow() {
        final DetailsOverviewRow row = new DetailsOverviewRow(movieDetails);

        row.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.default_background));

        Glide.with(getActivity())
                .load(movieDetails.getPosterPath())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        row.setImageDrawable(resource);
                        mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size());
                    }
                });

        ArrayObjectAdapter actionAdapter = new ArrayObjectAdapter();
        actionAdapter.add(
                new Action(
                        ACTION_OVERVIEW,
                        getResources().getString(R.string.overview)));

        row.setActionsAdapter(actionAdapter);
        mAdapter.add(row);
    }

    private void setupDetailsOverviewRowPresenter() {
        // Set detail background.
        FullWidthDetailsOverviewRowPresenter detailsPresenter =
                new FullWidthDetailsOverviewRowPresenter(new DetailsDescriptionPresenter());
        detailsPresenter.setBackgroundColor(
                ContextCompat.getColor(getActivity(), R.color.default_background));

        // Hook up transition element.
        FullWidthDetailsOverviewSharedElementHelper sharedElementHelper =
                new FullWidthDetailsOverviewSharedElementHelper();
        sharedElementHelper.setSharedElementEnterTransition(
                getActivity(), DetailsActivity.SHARED_ELEMENT_NAME);
        detailsPresenter.setListener(sharedElementHelper);
        detailsPresenter.setParticipatingEntranceTransition(true);

        detailsPresenter.setOnActionClickedListener(new OnActionClickedListener() {
            @Override
            public void onActionClicked(Action action) {
                if (action.getId() == ACTION_OVERVIEW) {
                    Toast.makeText(getActivity(), action.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        mPresenterSelector.addClassPresenter(DetailsOverviewRow.class, detailsPresenter);
    }


    public int convertDpToPixel(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showErrorState() {

    }

    @Override
    public void hideErrorState() {

    }

    @Override
    public void showMovieDetails(@NotNull MovieDetailView movieDetail) {
        this.movieDetails = movieMapper.mapToViewModel(movieDetail);
        mPresenterSelector = new ClassPresenterSelector();
        mAdapter = new ArrayObjectAdapter(mPresenterSelector);
        setupDetailsOverviewRow();
        setupDetailsOverviewRowPresenter();
        setAdapter(mAdapter);
        initializeBackground(movieMapper.mapToViewModel(movieDetail));
        setOnItemViewClickedListener(new ItemViewClickedListener());
    }

    @Override
    public void showEmptyState() {

    }

    @Override
    public void hideEmptyState() {

    }

    @Override
    public void showOfflineModeNoCachedData() {

    }

    @Override
    public void showOfflineModeCachedData() {

    }

    @Override
    public void setPresenter(@NotNull MovieDetailsContract.Presenter presenter) {

    }

    @Override
    public void loadReviews(@NotNull List<Review> review) {

    }

    @Override
    public void showErrorLoadingReviews() {

    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

            if (item instanceof MovieDetailViewModel) {
                Log.d(TAG, "Item: " + item.toString());
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(getResources().getString(R.string.movie), movieDetails);

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                        DetailsActivity.SHARED_ELEMENT_NAME).toBundle();
                getActivity().startActivity(intent, bundle);
            }
        }
    }
}
