package com.ajay.mediapedia

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
}
