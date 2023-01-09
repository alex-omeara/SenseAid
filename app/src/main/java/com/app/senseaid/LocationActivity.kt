package com.app.senseaid

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.location_activity)

        val intent = intent
        val locationData = intent.getParcelableExtra<Location>("locationData")
        val title: TextView = findViewById(R.id.titleTxtView)
        val bannerImg: ImageView = findViewById(R.id.bannerImgView)

        if (locationData != null) {
            title.text = locationData.title
            bannerImg.setImageResource(locationData.imgId)
        }

    }
}