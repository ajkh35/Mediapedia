package com.ajay.mediapedia

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())

        initializeViews()
    }

    /**
     * Method to get the layout for current activity
     */
    abstract fun getLayout(): Int

    /**
     * Method to initialize the activity views
     */
    abstract fun initializeViews()

    /**
     * Method to start another activity
     */
    fun launchActivity(activityClass: Class<out Any>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    /**
     * Method to launch an intent
     */
    fun launchIntent(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
