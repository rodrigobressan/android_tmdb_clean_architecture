package com.rodrigobresan.sampleboilerplateandroid.intro

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rodrigobresan.sampleboilerplateandroid.R

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_intro)
    }
}