package com.ajay.mediapedia

import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ajay.mediapedia.fragments.ErrorFragment
import com.ajay.mediapedia.fragments.MoviesFragment
import com.ajay.mediapedia.fragments.MusicFragment
import com.ajay.mediapedia.fragments.NewsFragment
import com.ajay.mediapedia.network.ApiClient
import com.ajay.mediapedia.utils.GlobalUtils
import com.ajay.mediapedia.utils.PreferenceManager
import com.ajay.mediapedia.viewmodels.BaseViewModelFactory
import com.ajay.mediapedia.viewmodels.SharedViewModel
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.ResponseBody
import okio.Utf8
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.charset.Charset

class HomeActivity : BaseActivity() {

    private val TAG: String = javaClass.simpleName
    private lateinit var mCurrentFragment: String
    private lateinit var mFragmentTitles: Array<String>
    private lateinit var mViewModel: SharedViewModel
    private lateinit var mPreferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this, BaseViewModelFactory{SharedViewModel(application)})
            .get(SharedViewModel::class.java)
        mPreferenceManager = PreferenceManager(this)

        if(GlobalUtils.isNetWorkConnected(this)) {
            getSpotifyToken()
        }
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
     * Method to get Spotify Access Token
     */
    private fun getSpotifyToken() {
        val auth = "${getString(R.string.spotify_client_id)}:${getString(R.string.spotify_client_secret)}"
        val base64Auth = Base64.encodeToString(auth.toByteArray(Charset.forName("UTF-8")), Base64.NO_WRAP)
        val loginCall = ApiClient.getMusicApi().spotifyLogin("Basic $base64Auth")

        loginCall.enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(TAG, t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    val jsonObject = JSONObject(response.body()!!.string())
                    val access_token = jsonObject.get("access_token").toString()
                    mPreferenceManager.setStringPref(PreferenceManager.KEY_SPOTIFY_AUTH_TOKEN, access_token)
                } else {
                    Log.d(TAG, response.errorBody()!!.string())
                }
            }
        })
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
