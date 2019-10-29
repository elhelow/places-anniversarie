package com.example.placesanniversarie.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.placesanniversarie.R
import com.example.placesanniversarie.data.model.Place
import com.example.placesanniversarie.viewmodel.PlacesListModel
import kotlinx.android.synthetic.main.activity_main.*

class PlacesListActivity : AppCompatActivity(), PlaceAdapter.onPlaceClicked {
    override fun onPlaceClicked(place: Place) {
        startActivity(PlaceDetailsActivity.getStartIntent(this, place))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            startActivity(Intent(baseContext, UpdatePlaceActivity::class.java))
        }

        setup()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setup() {
        val viewModel = ViewModelProviders.of(this).get(PlacesListModel::class.java)
        viewModel.places.observe(this, object : Observer<List<Place>> {
            override fun onChanged(places: List<Place>) {
                recyclerView.setAdapter(PlaceAdapter(this@PlacesListActivity, places))
            }
        })
    }
}
