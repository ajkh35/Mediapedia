package com.ajay.mediapedia.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ajay.mediapedia.R
import com.ajay.mediapedia.data.model.Movie
import com.ajay.mediapedia.utils.Constants
import com.ajay.mediapedia.utils.listeners.OnItemClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*

class PopularMovieAdapter(list: List<Movie>, listener: OnItemClickListener): RecyclerView.Adapter<PopularMovieAdapter.MovieHolder>() {

    private val mMovieList = list
    private val mListener = listener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieHolder(view)
    }

    override fun getItemCount(): Int {
        return mMovieList.size
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {

        val itemView = holder.itemView

        itemView.title.text = mMovieList[position].title
        Picasso.get()
            .load(Constants.MOVIE_IMAGE_ORIGINAL_BASE_URL + mMovieList[position].posterPath)
            .resize(300,400)
            .placeholder(R.drawable.mediapedia)
            .into(itemView.movie_image)
        itemView.setOnClickListener {
            mListener.onItemClick(mMovieList[position])
        }
    }

    class MovieHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}