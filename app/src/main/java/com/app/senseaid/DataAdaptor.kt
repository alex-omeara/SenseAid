package com.app.senseaid

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(val title: String, val imgId: Int) : Parcelable

class DataAdaptor(
    private val locations: ArrayList<Location>,
    private val listener: (Location) -> Unit
) : RecyclerView.Adapter<DataAdaptor.ViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_card_row, parent, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get element from  dataset at this position and replace the
        // contents of the view with that element
        val item = locations[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{ listener(item) }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = locations.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.cardTitleTxt)
        val cardImg: ImageView = view.findViewById(R.id.cardImgView)

        fun bind(item: Location) {
            titleText.text = item.title
            cardImg.setImageResource(item.imgId)
        }

    }
}