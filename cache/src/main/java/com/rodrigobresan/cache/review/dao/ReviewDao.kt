package com.rodrigobresan.cache.review.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.rodrigobresan.cache.review.model.ReviewCached

@Dao
interface ReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(review: ReviewCached)

    @Query("SELECT * FROM Review where Review.movieId = :movieId ")
    fun getAllReviews(movieId: Long): List<ReviewCached>
}