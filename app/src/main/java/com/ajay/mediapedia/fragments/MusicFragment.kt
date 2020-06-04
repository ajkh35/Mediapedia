package com.ajay.mediapedia.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ajay.mediapedia.HomeActivity
import com.ajay.mediapedia.R
import com.ajay.mediapedia.adapters.GlobalTop50TracksAdapter
import com.ajay.mediapedia.data.model.music.Track
import com.ajay.mediapedia.viewmodels.SharedViewModel
import kotlinx.android.synthetic.main.fragment_music.*

private const val TITLE = "title"

class MusicFragment : Fragment() {
    private var mTitle: String? = null
    private val TAG: String = javaClass.simpleName
    private lateinit var mProgressDialog: Dialog

    private lateinit var mViewModel: SharedViewModel

    private lateinit var mGlobalTop50TracksList: ArrayList<Track>
    private lateinit var mGlobalTop50TracksAdapter: GlobalTop50TracksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mTitle = it.getString(TITLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_music, container, false)

        mViewModel = (activity as HomeActivity).getSharedViewModel()

        mProgressDialog = Dialog(activity, android.R.style.Theme_Black)
        val progressView = LayoutInflater.from(activity).inflate(R.layout.progress_dialog, null)
        mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mProgressDialog.setContentView(progressView)
        mProgressDialog.setCancelable(false)
        mProgressDialog.window?.setBackgroundDrawableResource(R.color.progress_bar_background)
        mProgressDialog.show()

        setupTopTracksRecycler(view)
        mViewModel.getGlobalTop50Tracks().observe(this, Observer {
            if(it.isNotEmpty()) {
                val tracks = ArrayList<Track>()
                for(dto in it) {
                    tracks.add(dto.toTrack())
                }

                mGlobalTop50TracksList.addAll(tracks)
                mGlobalTop50TracksAdapter.notifyDataSetChanged()

                mProgressDialog.dismiss()
            }
        })
        return view
    }

    private fun setupTopTracksRecycler(view: View) {
        val recycler = view.findViewById<RecyclerView>(R.id.top_tracks_recycler)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        mGlobalTop50TracksList = ArrayList()
        mGlobalTop50TracksAdapter = GlobalTop50TracksAdapter(mGlobalTop50TracksList)
        recycler.apply {
            this.layoutManager = layoutManager
            adapter = mGlobalTop50TracksAdapter
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param title Parameter 1.
         * @return A new instance of fragment MusicFragment.
         */
        @JvmStatic
        fun newInstance(title: String) =
            MusicFragment().apply {
                arguments = Bundle().apply {
                    putString(TITLE, title)
                }
            }
    }
}
