package com.ajay.mediapedia

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ajay.mediapedia.data.model.Movie
import com.ajay.mediapedia.data.model.Show
import com.ajay.mediapedia.fragments.ErrorFragment
import com.ajay.mediapedia.fragments.MovieDetailsOverviewFragment
import com.ajay.mediapedia.utils.Constants
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*
import java.text.SimpleDateFormat
import java.util.*

class MovieDetailsActivity : BaseActivity() {

    private lateinit var mMovie: Movie
    private lateinit var mShow: Show

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayout(): Int {
        return R.layout.activity_movie_details
    }

    override fun initializeViews() {
        setupAppbarLayout()
        setContents()
    }

    private fun setContents() {
        if(intent.getSerializableExtra("movie") != null) {
            mMovie = intent.getSerializableExtra("movie") as Movie
            Picasso.get()
                .load(Constants.MOVIE_IMAGE_W500_BASE_URL + mMovie.backdropPath)
                .resize(400, 500)
                .centerInside()
                .placeholder(R.drawable.mediapedia)
                .into(backdrop)
            Picasso.get()
                .load(Constants.MOVIE_IMAGE_ORIGINAL_BASE_URL + mMovie.posterPath)
                .resize(85, 125)
                .placeholder(R.drawable.mediapedia)
                .into(poster)
            title_poster.text = mMovie.title
            title_toolbar.text = mMovie.title
            setReleaseData(mMovie)
        } else {
            mShow = intent.getSerializableExtra("show") as Show
            Picasso.get()
                .load(Constants.MOVIE_IMAGE_W500_BASE_URL + mShow.backdropPath)
                .resize(400, 500)
                .centerInside()
                .placeholder(R.drawable.mediapedia)
                .into(backdrop)
            Picasso.get()
                .load(Constants.MOVIE_IMAGE_ORIGINAL_BASE_URL + mShow.posterPath)
                .resize(85,125)
                .placeholder(R.drawable.mediapedia)
                .into(poster)
            title_poster.text = mShow.name
            title_toolbar.text = mShow.name
            setReleaseData(mShow)
        }

        setupViewPager()
    }

    private fun setReleaseData(any: Any) {
        var releaseDate = Date()
        if(any is Movie) {
            releaseDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(mMovie.releaseDate)
        } else if(any is Show) {
            releaseDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(mShow.firstAirDate)
        }

        val calendar = Calendar.getInstance(Locale.US)
        calendar.time = releaseDate
        val monthName = SimpleDateFormat("MMMM", Locale.US).format(calendar.time)
        val year = calendar.get(Calendar.YEAR)
        val releaseText = "$monthName $year"
        release.text = releaseText
    }

    /**
     * Method to setup view pager
     */
    private fun setupViewPager() {
        tab_layout.setupWithViewPager(view_pager)
        view_pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                view_pager.currentItem = position
            }
        })
        val adapter = PagerAdapter(supportFragmentManager)
        view_pager.adapter = adapter
    }

    /**
     * Adapter for view pager
     */
    class PagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val pageCount = 3

        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> MovieDetailsOverviewFragment()
                1 -> MovieDetailsOverviewFragment()
                2 -> MovieDetailsOverviewFragment()
                else -> ErrorFragment()
            }
        }

        override fun getCount(): Int = pageCount

        override fun getPageTitle(position: Int): CharSequence? {
            return when(position) {
                0 -> "Overview"
                1 -> "Cast"
                2 -> "Reviews"
                else -> ""
            }
        }
    }

    /**
     * Method to setup Collapsing toolbar
     */
    private fun setupAppbarLayout() {
        appbar_layout.addOnOffsetChangedListener(object: AppBarLayout.OnOffsetChangedListener {
            var scrollRange = -1
            var isShow = true

            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if(scrollRange == -1) {
                    scrollRange = appBarLayout!!.totalScrollRange
                }
                if(scrollRange + verticalOffset == 0) {
                    title_toolbar.visibility = View.VISIBLE
                    img_back.visibility = View.VISIBLE
                    isShow = true
                } else if(isShow){
                    title_toolbar.visibility = View.GONE
                    img_back.visibility = View.GONE
                    isShow = false
                }
            }
        })
    }

    fun onClick(view: View) {
        when(view.id) {
            R.id.img_back -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
