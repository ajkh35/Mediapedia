package com.ajay.mediapedia.fragments

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
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
import com.ajay.mediapedia.MovieDetailsActivity
import com.ajay.mediapedia.R
import com.ajay.mediapedia.adapters.NowPlayingAdapter
import com.ajay.mediapedia.adapters.PopularMovieAdapter
import com.ajay.mediapedia.adapters.PopularShowsAdapter
import com.ajay.mediapedia.adapters.UpcomingMoviesAdapter
import com.ajay.mediapedia.data.model.Movie
import com.ajay.mediapedia.data.model.MovieNetworkDto
import com.ajay.mediapedia.data.model.Show
import com.ajay.mediapedia.data.model.ShowNetworkDto
import com.ajay.mediapedia.utils.Constants
import com.ajay.mediapedia.utils.GlobalUtils
import com.ajay.mediapedia.utils.listeners.OnItemClickListener
import com.ajay.mediapedia.viewmodels.SharedViewModel
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.CarouselView

private const val TITLE = "title"

private const val CAROUSEL_IMAGE_COUNT = 6
private const val MAX_PAGES = 10

class MoviesFragment : Fragment() {

    private val TAG: String = javaClass.simpleName
    private var mTitle: String? = null

    private lateinit var mProgressDialog: Dialog
    private var mProgressLoadCount = 0

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

    //Upcoming Movies
    private lateinit var mUpcomingMoviesList: ArrayList<Movie>
    private lateinit var mUpcomingMoviesAdapter: UpcomingMoviesAdapter
    private var mUpcomingMoviesPage: Int = 1
    private lateinit var mUpcomingMoviesProgressBar: ProgressBar
    private var mUpcomingMoviesLoading: Boolean = true

    // Popular Shows
    private lateinit var mPopularShowsList: ArrayList<Show>
    private lateinit var mPopularShowsAdapter: PopularShowsAdapter
    private var mPopularShowsPage: Int = 1
    private lateinit var mPopularShowsProgressBar: ProgressBar
    private var mPopularShowsLoading: Boolean = true

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

        // show progress bar
        mProgressDialog = Dialog(activity, android.R.style.Theme_Black)
        val progressView = LayoutInflater.from(activity).inflate(R.layout.progress_dialog, null)
        mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mProgressDialog.setContentView(progressView)
        mProgressDialog.setCancelable(false)
        mProgressDialog.window?.setBackgroundDrawableResource(R.color.progress_bar_background)
        mProgressDialog.show()

        // Popular movies
        mPopularMoviesProgressBar = view.findViewById(R.id.progress_bar_popular_movies)
        setupPopularMovieRecycler(view)
        mViewModel.getPopularMovies(1).observe(this, Observer<List<MovieNetworkDto>> {
            if(it.isNotEmpty()) {
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
                setupCarousel(view)

                mProgressLoadCount++
                if(mProgressLoadCount == 4) {
                    mProgressDialog.dismiss()
                }
            }
        })

        // Now playing
        mNowPlayingProgressBar = view.findViewById(R.id.progress_bar_now_playing)
        setupNowPlayingRecycler(view)
        mViewModel.getNowPlayingMovies(1).observe(this, Observer<List<MovieNetworkDto>>{
            if(it.isNotEmpty()) {
                // Map to Movie list
                val moviesList = ArrayList<Movie>()
                for(movieDto in it) {
                    moviesList.add(movieDto.toMovie())
                }

                mNowPlayingList.addAll(moviesList)
                mNowPlayingAdapter.notifyDataSetChanged()
                mNowPlayingProgressBar.visibility = View.GONE
                mNowPlayingLoading = true

                mProgressLoadCount++
                if(mProgressLoadCount == 4) {
                    mProgressDialog.dismiss()
                }
            }
        })

        //Upcoming movies
        mUpcomingMoviesProgressBar = view.findViewById(R.id.progress_bar_upcoming_movies)
        setupUpcomingRecycler(view)
        mViewModel.getUpcomingMovies(1).observe(this, Observer<List<MovieNetworkDto>> {
            if(it.isNotEmpty()) {
                // Map to Movie list
                val moviesList = ArrayList<Movie>()
                for(movieDto in it) {
                    moviesList.add(movieDto.toMovie())
                }

                mUpcomingMoviesList.addAll(moviesList)
                mUpcomingMoviesAdapter.notifyDataSetChanged()
                mUpcomingMoviesProgressBar.visibility = View.GONE
                mUpcomingMoviesLoading = true

                mProgressLoadCount++
                if(mProgressLoadCount == 4) {
                    mProgressDialog.dismiss()
                }
            }
        })

        //Popular shows
        mPopularShowsProgressBar = view.findViewById(R.id.progress_bar_popular_shows)
        setupPopularShowsRecycler(view)
        mViewModel.getPopularShows(1).observe(this, Observer<List<ShowNetworkDto>> {
            if(it.isNotEmpty()) {
                // Map to Movie list
                val showsList = ArrayList<Show>()
                for(showDto in it) {
                    showsList.add(showDto.toShow())
                }

                mPopularShowsList.addAll(showsList)
                mPopularShowsAdapter.notifyDataSetChanged()
                mPopularShowsProgressBar.visibility = View.GONE
                mPopularShowsLoading = true

                mProgressLoadCount++
                if(mProgressLoadCount == 4) {
                    mProgressDialog.dismiss()
                }
            }
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
                    .resize(400,500)
                    .centerInside()
                    .placeholder(R.drawable.mediapedia)
                    .into(imageView)
            }
        }
        carousel.pageCount = CAROUSEL_IMAGE_COUNT

        carousel.setImageClickListener {
            val intent = Intent(activity, MovieDetailsActivity::class.java)
            intent.putExtra("movie", mPopularMoviesList[it])
            (activity as HomeActivity).launchIntent(intent)
        }

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
        val recycler = view.findViewById<RecyclerView>(R.id.popular_movies_recycler)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        mPopularMoviesList = ArrayList()
        mPopularMoviesAdapter = PopularMovieAdapter(mPopularMoviesList, object: OnItemClickListener {
            override fun onItemClick(movie: Movie) {
                val intent = Intent(activity, MovieDetailsActivity::class.java)
                intent.putExtra("movie", movie)
                (activity as HomeActivity).launchIntent(intent)
            }

            override fun onItemClick(show: Show) {

            }
        })

        recycler.apply {
            this.layoutManager = layoutManager
            adapter = mPopularMoviesAdapter
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if(dx > 0) {
                        if(mPopularMoviesPage > MAX_PAGES - 1) {
                            return
                        }
//                        val manager = this@apply.layoutManager as LinearLayoutManager
                        val visibleItemCount = layoutManager.childCount
                        val totalItemCount = layoutManager.itemCount
                        val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                        if(mPopularMoviesLoading) {
                            if((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                if(GlobalUtils.isNetWorkConnected(activity as HomeActivity)) {
                                    mPopularMoviesProgressBar.visibility = View.VISIBLE
                                    mPopularMoviesLoading = false
                                    mViewModel.getMorePopularMovies(++mPopularMoviesPage)
                                }
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
        mNowPlayingAdapter = NowPlayingAdapter(mNowPlayingList, object: OnItemClickListener {
            override fun onItemClick(movie: Movie) {
                val intent = Intent(activity, MovieDetailsActivity::class.java)
                intent.putExtra("movie", movie)
                (activity as HomeActivity).launchIntent(intent)
            }

            override fun onItemClick(show: Show) {

            }
        })

        recycler.apply {
            this.layoutManager = layoutManager
            adapter = mNowPlayingAdapter
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if(dx > 0) {
                        if(mNowPlayingPage > MAX_PAGES - 1) {
                            return
                        }
                        val manager = this@apply.layoutManager as LinearLayoutManager
                        val visibleItemCount = manager.childCount
                        val totalItemCount = manager.itemCount
                        val pastVisibleItems = manager.findFirstVisibleItemPosition()

                        if(mNowPlayingLoading) {
                            if((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                if(GlobalUtils.isNetWorkConnected(activity as HomeActivity)) {
                                    mNowPlayingProgressBar.visibility = View.VISIBLE
                                    mNowPlayingLoading = false
                                    mViewModel.getMoreNowPlayingMovies(++mNowPlayingPage)
                                }
                            }
                        }
                    }
                }
            })
        }
    }

    /**
     * Method to setup upcoming list
     */
    private fun setupUpcomingRecycler(view: View) {
        val recycler = view.findViewById<RecyclerView>(R.id.upcoming_movies_recycler)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        mUpcomingMoviesList = ArrayList()
        mUpcomingMoviesAdapter = UpcomingMoviesAdapter(mUpcomingMoviesList, object: OnItemClickListener {
            override fun onItemClick(movie: Movie) {
                val intent = Intent(activity, MovieDetailsActivity::class.java)
                intent.putExtra("movie", movie)
                (activity as HomeActivity).launchIntent(intent)
            }

            override fun onItemClick(show: Show) {

            }
        })

        recycler.apply {
            this.layoutManager = layoutManager
            adapter = mUpcomingMoviesAdapter
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if(dx > 0) {
                        if(mUpcomingMoviesPage > -1) {
                            return
                        }
                        val visibleItemCount = layoutManager.childCount
                        val totalItemCount = layoutManager.itemCount
                        val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                        if(mUpcomingMoviesLoading) {
                            if((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                if(GlobalUtils.isNetWorkConnected(activity as HomeActivity)) {
                                    mUpcomingMoviesProgressBar.visibility = View.VISIBLE
                                    mUpcomingMoviesLoading = false
                                    mViewModel.getMoreUpcomingMovies(++mUpcomingMoviesPage)
                                }
                            }
                        }
                    }
                }
            })
        }
    }

    /**
     * Method to setup popular shows
     */
    private fun setupPopularShowsRecycler(view: View) {
        val recycler = view.findViewById<RecyclerView>(R.id.popular_shows_recycler)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        mPopularShowsList = ArrayList()
        mPopularShowsAdapter = PopularShowsAdapter(mPopularShowsList, object: OnItemClickListener {
            override fun onItemClick(movie: Movie) {

            }

            override fun onItemClick(show: Show) {
                val intent = Intent(activity, MovieDetailsActivity::class.java)
                intent.putExtra("show", show)
                (activity as HomeActivity).launchIntent(intent)
            }
        })

        recycler.apply {
            this.layoutManager = layoutManager
            adapter = mPopularShowsAdapter
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if(dx > 0) {
                        if(mPopularShowsPage > -1) {
                            return
                        }
                        val visibleItemCount = layoutManager.childCount
                        val totalItemCount = layoutManager.itemCount
                        val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                        if(mPopularShowsLoading) {
                            if((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                if(GlobalUtils.isNetWorkConnected(activity as HomeActivity)) {
                                    mPopularShowsProgressBar.visibility = View.VISIBLE
                                    mPopularShowsLoading = false
                                    mViewModel.getMorePopularShows(++mPopularShowsPage)
                                }
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
