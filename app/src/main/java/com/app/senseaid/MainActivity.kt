package com.app.senseaid

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Document
import org.w3c.dom.NodeList
import java.io.InputStream
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adaptor = DataAdaptor(loadLocations())
        recyclerView.adapter = adaptor

    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_dark ->
                    if (checked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                R.id.radio_light ->
                    if (checked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
            }
        }
    }

    fun loadLocations(): ArrayList<Location> {
        val stream: InputStream
        val docBuilder: DocumentBuilder
        var xmlDoc: Document? = null
        val locationList = ArrayList<Location>()

        try {
            stream = resources.openRawResource(R.raw.home_sample)
            docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            xmlDoc = docBuilder.parse(stream)
        } catch (e: Exception) {
            Log.e("ERROR", "failed to build stream: ", e)
        }

        var titleList: NodeList = xmlDoc?.getElementsByTagName("title")!!
        var imgList: NodeList = xmlDoc?.getElementsByTagName("img")!!
        for (i in 0 until titleList.length) {
            var imgId = resources.getIdentifier(imgList.item(i).firstChild.nodeValue, "drawable", packageName)
            locationList.add(Location(titleList.item(i).firstChild.nodeValue, imgId))
        }
        return locationList
    }
}