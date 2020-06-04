package com.ajay.mediapedia.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ajay.mediapedia.R
import com.ajay.mediapedia.data.model.music.Track
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.track_item.view.*

class GlobalTop50TracksAdapter(list: ArrayList<Track>): RecyclerView.Adapter<GlobalTop50TracksAdapter.TrackHolder>() {

    private val mList = list

    class TrackHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_item, parent, false)
        return TrackHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        val itemView = holder.itemView

        Picasso.get()
            .load(mList[position].album.albumArts[0].url)
            .resize(150,200)
            .placeholder(R.drawable.mediapedia)
            .into(itemView.track_img)
        itemView.track_title.text = mList[position].name
    }
}