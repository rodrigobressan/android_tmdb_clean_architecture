package com.rodrigobresan.data.test.factory

import com.rodrigobresan.base.DataFactory
import com.rodrigobresan.data.movie_category.model.MovieCategoryEntity

/**
 * Created by rodrigobresan on 05/10/17.

In case of any questions, feel free to ask me

E-mail: rcbresan@gmail.com
Slack: bresan

 */

class MovieCategoryFactory {
    companion object Factory {
        fun makeMovieCategoryEntity(): MovieCategoryEntity {
            return MovieCategoryEntity(DataFactory.randomLong(), DataFactory.randomUuid())
        }
    }
}
