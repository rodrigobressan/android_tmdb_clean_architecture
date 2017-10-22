package com.rodrigobresan.data.model

data class MovieEntity(val id: Long, val title: String, val rating: Double, val posterPath: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as MovieEntity

        if (!id.equals(other.id)) return false
        if (!title.equals(other.title)) return false
        if (!rating.equals(other.rating)) return false
        if (!posterPath.equals(other.posterPath)) return false

        return true
    }

}