package com.rodrigobresan.sampleboilerplateandroid.movies.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.rodrigobresan.sampleboilerplateandroid.R
import com.rodrigobresan.sampleboilerplateandroid.movies.model.MovieViewModel
import javax.inject.Inject

class MoviesAdapter @Inject constructor(private val clickListener: MovieClickListener): RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    var listItems : List<MovieViewModel> = arrayListOf()

   // var clickListener: MovieClickListener? = null

    interface MovieClickListener {
        fun onMovieSelected(id: Long, imageView: ImageView)
    }
    // TODO set click listener interface in order to allow click events

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)

        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentItem = listItems.get(position)
        holder.txtTitle.text = currentItem.title
        holder.txtRating.text = currentItem.rating.toString()

        Glide.with(holder.imgPicture.context).load(currentItem.posterPath).into(holder.imgPicture)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtTitle: TextView
        var txtRating: TextView
        var imgPicture: ImageView

        init {
            txtTitle = itemView.findViewById(R.id.txt_title)
            txtRating = itemView.findViewById(R.id.txt_rating)
            imgPicture = itemView.findViewById(R.id.img_movie_picture)

            itemView.setOnClickListener{
                clickListener.onMovieSelected(listItems[adapterPosition].id, imgPicture)
            }
        }
    }

}