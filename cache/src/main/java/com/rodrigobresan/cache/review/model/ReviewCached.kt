package com.rodrigobresan.cache.review.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "Review")
data class ReviewCached(var movieId: Long, var author: String, var content: String, var url: String) {

    constructor() : this(0, "author", "content", "url")

    @field:PrimaryKey
    var id: String = ""
}