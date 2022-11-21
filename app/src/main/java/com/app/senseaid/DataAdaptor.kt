package com.app.senseaid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataAdaptor(val locations: ArrayList<Location>) : RecyclerView.Adapter<DataAdaptor.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.cardTitleTxt)
        val cardImg: ImageView = view.findViewById(R.id.cardImgView)
    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_card_row, parent, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.titleText.text = locations[position].title
        holder.cardImg.setImageResource(locations[position].imgId)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = locations.size


}