package com.rodrigobresan.tv.movies.model

import java.io.Serializable

class MovieViewModel(val id: Long, val title: String, val rating: Double, val posterPath: String) : Serializable