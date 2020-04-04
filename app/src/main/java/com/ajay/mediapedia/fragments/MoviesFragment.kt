package com.ajay.mediapedia.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ajay.mediapedia.R
import com.synnapps.carouselview.CarouselView
import kotlinx.android.synthetic.main.fragment_movies.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val TITLE = "title"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MoviesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoviesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mTitle: String? = null
//    private var param2: String? = null
    private val mImages: Array<Int> = arrayOf(R.mipmap.mediapedia,R.mipmap.mediapedia,R.mipmap.mediapedia)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mTitle = it.getString(TITLE)
//            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movies, container, false)

        // Setup the movie carousel
        setupCarousel(view)

        // Setup movie recycler
        setupMovieRecycler(view)

        return view
    }

    /**
     * Method to setup Carousel view
     */
    private fun setupCarousel(view: View) {
        val carousel = view.findViewById(R.id.movie_carousel) as CarouselView
        carousel.pageCount = mImages.size
        carousel.setImageListener { position, imageView ->
            imageView.setImageResource(mImages[position])
        }
    }

    /**
     * Method to setup movie recycler
     */
    private fun setupMovieRecycler(view: View) {
        val recycler = view.findViewById<RecyclerView>(R.id.movie_recycler)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recycler.layoutManager = layoutManager
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MoviesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(title: String/*, param2: String*/) =
            MoviesFragment().apply {
                arguments = Bundle().apply {
                    putString(TITLE, title)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
