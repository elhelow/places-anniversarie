package com.example.placesanniversarie.view

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.Anniversariesanniversarie.view.AnniAdapter
import com.example.placesanniversarie.R
import com.example.placesanniversarie.data.model.Anniversarie
import com.example.placesanniversarie.data.model.Place
import com.example.placesanniversarie.viewmodel.PlaceDetailsModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_place_details.*


class PlaceDetailsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var place: Place? = null
    lateinit var viewModel: PlaceDetailsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.placesanniversarie.R.layout.activity_place_details)

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })


        viewModel = ViewModelProviders.of(this).get(PlaceDetailsModel::class.java)
        place = intent?.getParcelableExtra(placeExtra)

        if (place != null) {
            viewModel.getAnn(place!!.id!!)
            name.setText(place!!.name)

            viewModel.anniListL.observe(this, object : Observer<List<Anniversarie>> {
                override fun onChanged(aniiList: List<Anniversarie>) {
                    anni_list.setAdapter(AnniAdapter(aniiList))
                }
            })
            showImage(place!!.imageUrl)
        }
        edit_btn.setOnClickListener {
            startActivity(UpdatePlaceActivity.getStartIntent(this, place!!))
        }

        val supportMapFragment =
            supportFragmentManager.findFragmentById(com.example.placesanniversarie.R.id.mapView) as SupportMapFragment?
        supportMapFragment!!.getMapAsync(this)
    }


    private fun showImage(url: String?) {
        if (url != null && !url.isEmpty()) {
            val width = Resources.getSystem().displayMetrics.widthPixels
            Glide.with(this).load(url).into(img_details)
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val latLng = LatLng(place!!.lat, place!!.lng)
        //MarkerOptions are used to create a new Marker.You can specify location, title etc with MarkerOptions
        val markerOptions = MarkerOptions().position(latLng).title("You are Here")
        googleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f))
        //Adding the created the marker on the map
        googleMap.addMarker(markerOptions)
    }


    companion object {
        private val TAG = "Places"
        private val placeExtra = "Place"
        fun getStartIntent(activity: Context, place: Place): Intent {
            var intent = Intent(activity, PlaceDetailsActivity::class.java)
            intent.putExtra(placeExtra, place)
            return intent
        }
    }

}
