package com.rodrigobresan.sampleboilerplateandroid.movie_detail.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rodrigobresan.domain.review.model.Review
import com.rodrigobresan.sampleboilerplateandroid.R

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    var reviewList: List<Review> = arrayListOf()

    override fun onBindViewHolder(holder: ReviewViewHolder?, position: Int) {
        var item = reviewList[position]
        holder?.txtAuthor?.text = item.author
        holder?.txtContent?.text = item.content
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ReviewViewHolder {
        val inflatedView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_review, parent, false)

        return ReviewViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        if (reviewList != null) {
            return reviewList.size
        } else {
            return 0
        }
    }

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtAuthor: TextView = itemView.findViewById(R.id.txt_item_review_author)
        var txtContent: TextView = itemView.findViewById(R.id.txt_item_review_content)
    }
}