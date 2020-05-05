package com.ajay.mediapedia.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ajay.mediapedia.HomeActivity

import com.ajay.mediapedia.R
import com.ajay.mediapedia.adapters.NowPlayingAdapter
import com.ajay.mediapedia.adapters.PopularMovieAdapter
import com.ajay.mediapedia.data.model.Movie
import com.ajay.mediapedia.data.model.MovieNetworkDto
import com.ajay.mediapedia.network.ApiClient
import com.ajay.mediapedia.utils.Constants
import com.ajay.mediapedia.viewmodels.SharedViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.CarouselView
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TITLE = "title"

// Carousel
private const val CAROUSEL_IMAGE_COUNT = 6

class MoviesFragment : Fragment() {

    private val TAG: String = javaClass.simpleName
    private var mTitle: String? = null

    // SharedViewModel
    private lateinit var mViewModel: SharedViewModel

    // Popular Movies
    private lateinit var mPopularMoviesList: ArrayList<Movie>
    private lateinit var mPopularMoviesAdapter: PopularMovieAdapter
    private var mPopularMoviesPage: Int = 1
    private lateinit var mPopularMoviesProgressBar: ProgressBar
    private var mPopularMoviesLoading: Boolean = true

    // Now playing
    private lateinit var mNowPlayingList: ArrayList<Movie>
    private lateinit var mNowPlayingAdapter: NowPlayingAdapter
    private var mNowPlayingPage: Int = 1
    private lateinit var mNowPlayingProgressBar: ProgressBar
    private var mNowPlayingLoading: Boolean = true

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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movies, container, false)

        // setup shared viewmodel
        mViewModel = (activity as HomeActivity).getSharedViewModel()

        // Popular movies
        mPopularMoviesProgressBar = view.findViewById(R.id.progress_bar_popular_movies)
        setupPopularMovieRecycler(view)
        mViewModel.getPopularMovies(1).observe(this, Observer<List<MovieNetworkDto>> {
            // Map to Movie list
            val moviesList = ArrayList<Movie>()
            for(movieDto in it) {
                moviesList.add(movieDto.toMovie())
            }

            mPopularMoviesList.addAll(moviesList)
            mPopularMoviesAdapter.notifyDataSetChanged()
            mPopularMoviesProgressBar.visibility = View.GONE
            mPopularMoviesLoading = true

            // Setup the movie carousel
            if(mPopularMoviesList.isNotEmpty()) {
                setupCarousel(view)
            }
        })

        // Now playing
        mNowPlayingProgressBar = view.findViewById(R.id.progress_bar_now_playing)
        setupNowPlayingRecycler(view)
        mViewModel.getNowPlayingMovies(1).observe(this, Observer<List<MovieNetworkDto>>{
            // Map to Movie list
            val moviesList = ArrayList<Movie>()
            for(movieDto in it) {
                moviesList.add(movieDto.toMovie())
            }

            mNowPlayingList.addAll(moviesList)
            mNowPlayingAdapter.notifyDataSetChanged()
            mNowPlayingProgressBar.visibility = View.GONE
            mNowPlayingLoading = true
        })

        return view
    }

    /**
     * Method to setup Carousel view
     */
    private fun setupCarousel(view: View) {
        val carousel = view.findViewById(R.id.movie_carousel) as CarouselView

        carousel.setImageListener { position, imageView ->
            if(mPopularMoviesList.isNotEmpty()) {
                Picasso.get()
                    .load(Constants.MOVIE_IMAGE_W500_BASE_URL + mPopularMoviesList[position].backdropPath)
                    .resize(300,400)
                    .centerInside()
                    .placeholder(R.mipmap.mediapedia)
                    .into(imageView)
            }
        }
        carousel.pageCount = CAROUSEL_IMAGE_COUNT

        //region Custom Carousel View
//        carousel.setViewListener {
//            val carouselView = layoutInflater.inflate(R.layout.custom_carousel, null)
//            val imageView = carouselView.findViewById<ImageView>(R.id.carousel_image)
//            val textView = carouselView.findViewById<TextView>(R.id.carousel_text)
//
//            if(mPopularMoviesList.isNotEmpty()) {
//                textView.text = mPopularMoviesList[it].title
//                Picasso.get()
//                    .load(Constants.MOVIE_IMAGE_W500_BASE_URL + mPopularMoviesList[it].backdropPath)
//                    .placeholder(R.mipmap.mediapedia)
//                    .into(imageView)
//            }
//
//            carouselView
//        }
        //endregion
    }

    /**
     * Method to setup popular movies list
     */
    private fun setupPopularMovieRecycler(view: View) {
        val recycler = view.findViewById<RecyclerView>(R.id.popular_movie_recycler)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        mPopularMoviesList = ArrayList()
        mPopularMoviesAdapter = PopularMovieAdapter(mPopularMoviesList)

        recycler.apply {
            this.layoutManager = layoutManager
            adapter = mPopularMoviesAdapter
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if(dx > 0) {
                        if(mPopularMoviesPage > 9) {
                            return
                        }
                        val manager = this@apply.layoutManager as LinearLayoutManager
                        val visibleItemCount = manager.childCount
                        val totalItemCount = manager.itemCount
                        val pastVisibleItems = manager.findFirstVisibleItemPosition()

                        if(mPopularMoviesLoading) {
                            if((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                mPopularMoviesProgressBar.visibility = View.VISIBLE
                                mPopularMoviesLoading = false
                                mViewModel.getMorePopularMovies(++mPopularMoviesPage)
                            }
                        }
                    }
                }
            })
        }
    }

    /**
     * Method to setup now playing list
     */
    private fun setupNowPlayingRecycler(view: View) {
        val recycler = view.findViewById<RecyclerView>(R.id.now_playing_recycler)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        mNowPlayingList = ArrayList()
        mNowPlayingAdapter = NowPlayingAdapter(mNowPlayingList)

        recycler.apply {
            this.layoutManager = layoutManager
            adapter = mNowPlayingAdapter
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if(dx > 0) {
                        if(mNowPlayingPage > 9) {
                            return
                        }
                        val manager = this@apply.layoutManager as LinearLayoutManager
                        val visibleItemCount = manager.childCount
                        val totalItemCount = manager.itemCount
                        val pastVisibleItems = manager.findFirstVisibleItemPosition()

                        if(mNowPlayingLoading) {
                            if((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                mNowPlayingProgressBar.visibility = View.VISIBLE
                                mNowPlayingLoading = false
                                mViewModel.getMoreNowPlayingMovies(++mNowPlayingPage)
                            }
                        }
                    }
                }
            })
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment MoviesFragment.
         */
        @JvmStatic
        fun newInstance(title: String) =
            MoviesFragment().apply {
                arguments = Bundle().apply {
                    putString(TITLE, title)
                }
            }
    }
}
