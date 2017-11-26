package com.rodrigobresan.cache.category.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.rodrigobresan.cache.category.model.CategoryCached

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: CategoryCached)

    @Query("SELECT * FROM Category")
    fun getAll() : List<CategoryCached>
}