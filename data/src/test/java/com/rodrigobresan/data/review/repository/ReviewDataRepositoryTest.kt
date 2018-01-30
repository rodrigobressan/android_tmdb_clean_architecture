package com.rodrigobresan.data.movie.repository

import com.nhaarman.mockito_kotlin.*
import com.rodrigobresan.data.movie.sources.ReviewDataRepository
import com.rodrigobresan.data.movie.sources.data_store.ReviewDataStoreFactory
import com.rodrigobresan.data.review.mapper.ReviewMapper
import com.rodrigobresan.data.review.model.ReviewEntity
import com.rodrigobresan.data.review.sources.data_store.local.ReviewCacheDataStore
import com.rodrigobresan.data.review.sources.data_store.local.ReviewRemoteDataStore
import com.rodrigobresan.data.test.factory.ReviewFactory
import com.rodrigobresan.domain.review.model.Review
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ReviewDataRepositoryTest {

    private lateinit var reviewDataRepository: ReviewDataRepository

    private lateinit var reviewDataStoreFactory: ReviewDataStoreFactory
    private lateinit var reviewMapper: ReviewMapper

    private lateinit var reviewCacheDataStore: ReviewCacheDataStore
    private lateinit var reviewRemoteDataStore: ReviewRemoteDataStore

    @Before
    fun setUp() {
        reviewDataStoreFactory = mock()

        reviewMapper = mock()

        reviewCacheDataStore = mock()
        reviewRemoteDataStore = mock()

        reviewDataRepository = ReviewDataRepository(reviewDataStoreFactory, reviewMapper)

        stubReviewDataStoreFactoryRetrieveCacheDataStore()
        stubReviewDataStoreFactoryRetrieveRemoteDataStore()
    }

    // clear functions
    @Test
    fun clearReviewsCompletes() {
        stubReviewCacheClearReviews(Completable.complete())

        val testObserver = reviewDataRepository.clearReviews().test()
        testObserver.assertComplete()
    }

    // related to save
    @Test
    fun saveReviewDetailsCompletes() {
        val review = ReviewFactory.makeReview()
        stubReviewCacheSaveReview(review, Completable.complete())

        val testObserver = reviewDataRepository.saveReviews(0, arrayListOf(review)).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveReviewCallsCacheDataStore() {
        val review = ReviewFactory.makeReview()
        stubReviewCacheSaveReview(review, Completable.complete())

        reviewDataRepository.saveReviews(0, arrayListOf(review)).test()
        verify(reviewCacheDataStore).saveReviews(0, arrayListOf(reviewMapper.mapToEntity(review)))
    }

    @Test
    fun saveReviewNeverCallRemoteDataStore() {
        val review = ReviewFactory.makeReview()
        stubReviewCacheSaveReview(review, Completable.complete())

        reviewDataRepository.saveReviews(0, arrayListOf(review)).test()
        verify(reviewRemoteDataStore, never()).saveReviews(any(), any())
    }

    // related to get
    @Test
    fun getReviewsCompletes() {
        val reviewList = ReviewFactory.makeReviewEntityList(5)
        stubReviewDataStoreFactoryRetrieveDataStore(reviewCacheDataStore)
        stubReviewCacheDataStoreGetReviews(Single.just(reviewList))

        val testObserver = reviewDataRepository.getReviews(0).test()
        testObserver.assertComplete()
    }

    @Test
    fun getReviewsReturnsData() {
        stubReviewDataStoreFactoryRetrieveDataStore(reviewCacheDataStore)
        val reviews = ReviewFactory.makeReviewList(5)
        val reviewsEntities = ReviewFactory.makeReviewEntityList(5)

        reviews.forEachIndexed { index, review ->
            stubReviewMapperMapFromEntity(reviewsEntities[index], review)
        }

        stubReviewCacheDataStoreGetReviews(Single.just(reviewsEntities))

        val testObserver = reviewDataRepository.getReviews(0).test()
        testObserver.assertValue(reviews)
    }

    @Test
    fun getReviewsSavesReviewsWhenFromCacheDataStore() {
        val review = ReviewFactory.makeReview()
        stubReviewDataStoreFactoryRetrieveDataStore(reviewCacheDataStore)
        stubReviewCacheSaveReview(review, Completable.complete())

        reviewDataRepository.saveReviews(0, arrayListOf(review)).test()
        verify(reviewCacheDataStore).saveReviews(0, arrayListOf(reviewMapper.mapToEntity(review)))
    }

    @Test
    fun getReviewsNeverSavedReviewsWhenRemoteDataStore() {
        val reviews = ReviewFactory.makeReview()
        stubReviewDataStoreFactoryRetrieveDataStore(reviewCacheDataStore)
        stubReviewCacheSaveReview(reviews, Completable.complete())

        reviewDataRepository.saveReviews(0, arrayListOf(reviews)).test()
        verify(reviewRemoteDataStore, never()).saveReviews(any(), any())
    }

    @Test
    fun getReviewsUsingRemoteDataStore() {
        whenever(reviewDataStoreFactory.retrieveDataStore())
                .thenReturn(reviewRemoteDataStore)

        whenever(reviewRemoteDataStore.getReviews(0))
                .thenReturn(Single.just(ReviewFactory.makeReviewEntityList(5)))

        reviewDataRepository.getReviews(0).test()
    }

    private fun stubReviewMapperMapFromEntity(reviewEntity: ReviewEntity, review: Review) {
        whenever(reviewMapper.mapFromEntity(reviewEntity))
                .thenReturn(review)
    }

    private fun stubReviewCacheDataStoreGetReviews(reviewEntity: Single<List<ReviewEntity>>) {
        whenever(reviewCacheDataStore.getReviews(0))
                .thenReturn(reviewEntity)
    }

    private fun stubReviewDataStoreFactoryRetrieveDataStore(cacheDataStore: ReviewCacheDataStore) {
        whenever(reviewDataStoreFactory.retrieveDataStore())
                .thenReturn(cacheDataStore)
    }

    private fun stubReviewCacheSaveReview(review: Review, complete: Completable?) {
        whenever(reviewCacheDataStore.saveReviews(0, arrayListOf(reviewMapper.mapToEntity(review))))
                .thenReturn(complete)
    }

    private fun stubReviewCacheClearReviews(complete: Completable?) {
        whenever(reviewCacheDataStore.clearReviews())
                .thenReturn(complete)
    }

    private fun stubReviewDataStoreFactoryRetrieveCacheDataStore() {
        whenever(reviewDataStoreFactory.retrieveCachedDataStore())
                .thenReturn(reviewCacheDataStore)
    }

    private fun stubReviewDataStoreFactoryRetrieveRemoteDataStore() {
        whenever(reviewDataStoreFactory.retrieveRemoteDataStore())
                .thenReturn(reviewRemoteDataStore)
    }

}