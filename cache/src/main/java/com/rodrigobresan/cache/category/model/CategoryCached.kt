package com.rodrigobresan.cache.category.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Class that represents a cached category
 */
@Entity(tableName = "Category")
data class CategoryCached(var name: String) {

    constructor() : this("name")

    @field:PrimaryKey
    var id: String = ""
}