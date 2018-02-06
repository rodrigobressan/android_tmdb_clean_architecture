package com.rodrigobresan.data.review.model

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertTrue


@RunWith(JUnit4::class)
class ReviewEntityTest {

    @Test
    fun testEquals_Symmetric() {
        val review1 = ReviewEntity("id", "author", "content", "url")
        val review2 = ReviewEntity("id", "author", "content", "url")

        assertTrue(review1.equals(review2) && review2.equals(review1))
        assertTrue(review1.hashCode() == review2.hashCode())
    }

}