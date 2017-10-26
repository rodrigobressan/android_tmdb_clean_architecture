package com.rodrigobresan.domain.model

// TODO change to enum annotation
enum class MovieCategory(private val id: Long) {
    TOP_RATED(0),
    LATEST(1),
    NOW_PLAYING(2),
    UPCOMING(3),
    POPULAR(4),
    FAVORITE(5),
    SEEN(6);

    fun getId(): Long {
        return id
    }
}
