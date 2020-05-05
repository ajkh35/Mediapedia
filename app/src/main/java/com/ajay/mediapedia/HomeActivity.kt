package com.ajay.mediapedia

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ajay.mediapedia.fragments.ErrorFragment
import com.ajay.mediapedia.fragments.MoviesFragment
import com.ajay.mediapedia.fragments.MusicFragment
import com.ajay.mediapedia.fragments.NewsFragment
import com.ajay.mediapedia.viewmodels.BaseViewModelFactory
import com.ajay.mediapedia.viewmodels.SharedViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {

    private lateinit var mCurrentFragment: String
    private lateinit var mFragmentTitles: Array<String>
    private lateinit var mViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this, BaseViewModelFactory{SharedViewModel(application)})
            .get(SharedViewModel::class.java)
    }

    override fun getLayout(): Int {
        return R.layout.activity_home
    }

    override fun initializeViews() {
        setSupportActionBar(action_bar)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        mFragmentTitles = resources.getStringArray(R.array.fragments)

        // load movies fragment
        mCurrentFragment = mFragmentTitles[0]
        changeFragment()
    }

    /**
     * Method to handle click on views
     */
    fun onClicked(view: View) {
        when(view.id) {
            R.id.music_image -> {
                if(mCurrentFragment != mFragmentTitles[1]) {
                    mCurrentFragment = mFragmentTitles[1]
                    changeFragment()
                }
            }

            R.id.movies_image -> {
                if(mCurrentFragment != mFragmentTitles[0]) {
                    mCurrentFragment = mFragmentTitles[0]
                    changeFragment()
                }
            }

            R.id.news_image -> {
                if(mCurrentFragment != mFragmentTitles[2]) {
                    mCurrentFragment = mFragmentTitles[2]
                    changeFragment()
                }
            }
        }
    }

    /**
     * Method to set the fragment
     */
    private fun getFragment(title: String): Fragment {
        if(supportFragmentManager.findFragmentByTag(title) != null) {
            Log.d("HomeActivity", "Found fragment")
            return supportFragmentManager.findFragmentByTag(title)!!
        } else {
            return when(title) {
                "Movies" -> {
                    MoviesFragment.newInstance(title)
                }
                "Music" -> {
                    MusicFragment.newInstance(title)
                }

                "News" -> {
                    NewsFragment.newInstance(title)
                }

                else -> {
                    for(i in 0..supportFragmentManager.backStackEntryCount) {
                        supportFragmentManager.popBackStack()
                    }
                    ErrorFragment.newInstance()
                }
            }
        }
    }

    /**
     * Method to change the fragment
     */
    private fun changeFragment() {
        if(mCurrentFragment == mFragmentTitles[0]) {
            if(supportFragmentManager.findFragmentByTag(mFragmentTitles[0]) != null) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.show(supportFragmentManager.findFragmentByTag(mFragmentTitles[0]) as Fragment)
                transaction.commit()
            } else {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.add(R.id.fragment_container,
                    MoviesFragment.newInstance(mFragmentTitles[0]), mFragmentTitles[0])
                transaction.commit()
            }
            if(supportFragmentManager.findFragmentByTag(mFragmentTitles[1]) != null) {
                supportFragmentManager.beginTransaction()
                    .hide(supportFragmentManager.findFragmentByTag(mFragmentTitles[1]) as Fragment)
                    .commit()
            }
            if(supportFragmentManager.findFragmentByTag(mFragmentTitles[2]) != null) {
                supportFragmentManager.beginTransaction()
                    .hide(supportFragmentManager.findFragmentByTag(mFragmentTitles[2]) as Fragment)
                    .commit()
            }
        } else if(mCurrentFragment == mFragmentTitles[1]) {
            if(supportFragmentManager.findFragmentByTag(mFragmentTitles[1]) != null) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.show(supportFragmentManager.findFragmentByTag(mFragmentTitles[1]) as Fragment)
                transaction.commit()
            } else {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.add(R.id.fragment_container,
                    MusicFragment.newInstance(mFragmentTitles[1]), mFragmentTitles[1])
                transaction.commit()
            }
            if(supportFragmentManager.findFragmentByTag(mFragmentTitles[0]) != null) {
                supportFragmentManager.beginTransaction()
                    .hide(supportFragmentManager.findFragmentByTag(mFragmentTitles[0]) as Fragment)
                    .commit()
            }
            if(supportFragmentManager.findFragmentByTag(mFragmentTitles[2]) != null) {
                supportFragmentManager.beginTransaction()
                    .hide(supportFragmentManager.findFragmentByTag(mFragmentTitles[2]) as Fragment)
                    .commit()
            }
        } else if(mCurrentFragment == mFragmentTitles[2]) {
            if(supportFragmentManager.findFragmentByTag(mFragmentTitles[2]) != null) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.show(supportFragmentManager.findFragmentByTag(mFragmentTitles[2]) as Fragment)
                transaction.commit()
            } else {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.add(R.id.fragment_container,
                    NewsFragment.newInstance(mFragmentTitles[2]), mFragmentTitles[2])
                transaction.commit()
            }
            if(supportFragmentManager.findFragmentByTag(mFragmentTitles[0]) != null) {
                supportFragmentManager.beginTransaction()
                    .hide(supportFragmentManager.findFragmentByTag(mFragmentTitles[0]) as Fragment)
                    .commit()
            }
            if(supportFragmentManager.findFragmentByTag(mFragmentTitles[1]) != null) {
                supportFragmentManager.beginTransaction()
                    .hide(supportFragmentManager.findFragmentByTag(mFragmentTitles[1]) as Fragment)
                    .commit()
            }
        }

        supportActionBar!!.title = mCurrentFragment
    }

    /**
     * Method to get SharedViewModel
     */
    fun getSharedViewModel(): SharedViewModel {
        return mViewModel
    }
}
