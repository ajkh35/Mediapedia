package com.ajay.mediapedia.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ajay.mediapedia.R
import com.ajay.mediapedia.data.model.Show
import com.ajay.mediapedia.utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.show_item.view.*

class PopularShowsAdapter(list: ArrayList<Show>): RecyclerView.Adapter<PopularShowsAdapter.ShowHolder>() {

    private val mShowsList = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.show_item, parent, false)
        return ShowHolder(view)
    }

    override fun getItemCount(): Int {
        return mShowsList.size
    }

    override fun onBindViewHolder(holder: ShowHolder, position: Int) {
        val itemView = holder.itemView

        itemView.show_title.text = mShowsList[position].name
        Picasso.get()
            .load(Constants.MOVIE_IMAGE_ORIGINAL_BASE_URL + mShowsList[position].posterPath)
            .resize(300,400)
            .placeholder(R.drawable.mediapedia)
            .into(itemView.show_image)
    }

    class ShowHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}