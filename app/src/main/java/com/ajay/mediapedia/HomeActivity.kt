package com.ajay.mediapedia

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ajay.mediapedia.fragments.MoviesFragment
import com.ajay.mediapedia.fragments.MusicFragment
import com.ajay.mediapedia.fragments.NewsFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {

    lateinit var mTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayout(): Int {
        return R.layout.activity_home
    }

    override fun initializeViews() {
        mTitle = "Home"

        setSupportActionBar(action_bar)
        supportActionBar!!.title = mTitle
    }

    /**
     * Method to handle click on views
     */
    fun onClicked(view: View) {
        var fragment: Fragment? = null

        when(view.id) {
            R.id.music_image -> {
                mTitle = "Music"
                fragment = MusicFragment.newInstance("Music")
            }

            R.id.movies_image -> {
                mTitle = "Movies"
                fragment = MoviesFragment.newInstance("Movies")
            }

            R.id.news_image -> {
                mTitle = "News"
                fragment = NewsFragment.newInstance("News")
            }
        }

        if(fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()

            supportActionBar!!.title = mTitle
        }
    }
}
