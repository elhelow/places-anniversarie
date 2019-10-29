package com.example.placesanniversarie.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.placesanniversarie.R
import com.example.placesanniversarie.data.model.Place

class PlaceAdapter(val onPlaceClickedListener: onPlaceClicked, private val list: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_item_place, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(onPlaceClickedListener, list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var image: ImageView
        internal var name: TextView
        internal var price: TextView

        init {
            image = itemView.findViewById(R.id.image)
            name = itemView.findViewById(R.id.name)
            price = itemView.findViewById(R.id.price)
        }

        fun bind(onPlaceClickedListener: PlaceAdapter.onPlaceClicked, place: Place) {
            if (!place.imageUrl.isNullOrBlank())
                Glide.with(itemView.getContext()).load(place.imageUrl)
                    .apply(RequestOptions.circleCropTransform()).into(image)
            else
                Glide.with(itemView.getContext()).load(R.drawable.ic_launcher_background)
                    .apply(RequestOptions.circleCropTransform()).into(image)
            name.text = place.name
            price.text = place.anniversarieList.toString()
            itemView.setOnClickListener {
                onPlaceClickedListener.onPlaceClicked(place)
            }
        }
    }

    interface onPlaceClicked {
        fun onPlaceClicked(place: Place)
    }
}
