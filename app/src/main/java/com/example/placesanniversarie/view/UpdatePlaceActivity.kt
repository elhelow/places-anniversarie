package com.example.placesanniversarie.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.placesanniversarie.R
import com.example.placesanniversarie.data.model.CallState
import com.example.placesanniversarie.data.model.Place
import com.example.placesanniversarie.data.model.UploadeImage
import com.example.placesanniversarie.viewmodel.UpdatePlaceModel
import kotlinx.android.synthetic.main.activity_add_place.*


class UpdatePlaceActivity : AppCompatActivity() {
    private var place: Place? = null
    private val PICTURE_RESULT = 55
    lateinit var viewModel: UpdatePlaceModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place)

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })

        viewModel = ViewModelProviders.of(this).get(UpdatePlaceModel::class.java)
        place = intent?.getParcelableExtra(placeExtra)
        if (place != null) {
            txtName.setText(place!!.name)
            txtLat.setText(place!!.lat.toString())
            txtLng.setText(place!!.lng.toString())
            anni.setText(place!!.anniversarieList.toString())
            showImage(place!!.imageUrl)
        }
        btn_add.setOnClickListener({
            addPlace()
        })

        btn_image.setOnClickListener(View.OnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "image/jpeg"
            i.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            startActivityForResult(
                Intent.createChooser(i, getString(R.string.choose_images)),
                PICTURE_RESULT
            )

        })

    }

    private fun addPlace(): Boolean {
        if (txtName.getText()!!.trim().isEmpty() || txtLat.getText().trim().isEmpty() || txtLng.getText().trim().isEmpty()) {
            Toast.makeText(this, R.string.complete_data, Toast.LENGTH_LONG).show()
            return false
        }
        if (place == null)
            place = Place()
        place!!.name = txtName.getText().toString()
        place!!.lat = txtLat.getText().toString().toDouble()
        place!!.lng = txtLng.getText().toString().toDouble()
        viewModel.callStatusL.observe(this, object : Observer<CallState> {
            override fun onChanged(callState: CallState) {
                if (callState.state.equals(CallState.CallState.SUCCESS)) {
                    finish()
                } else {
                    Toast.makeText(this@UpdatePlaceActivity, callState.msg, Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
        viewModel.update(place!!)

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICTURE_RESULT && resultCode == Activity.RESULT_OK) {
            if (place == null)
                place = Place()
            val imageUri = data!!.data
            viewModel.uploadeImageL.observe(this, object : Observer<UploadeImage> {
                override fun onChanged(uploadeImage: UploadeImage) {
                    if (uploadeImage.imageName != null) {
                        place!!.imageName = uploadeImage.imageName
                    }
                    if (uploadeImage.imageUrl != null) {
                        place!!.imageUrl = uploadeImage.imageUrl
                        showImage(place!!.imageUrl)
                    }
                }
            })
            viewModel.uploadImage(this, imageUri)
        }
    }

    private fun showImage(url: String?) {
        if (url != null && !url.isEmpty()) {
            val width = Resources.getSystem().displayMetrics.widthPixels
            Glide.with(this).load(url).apply(RequestOptions.circleCropTransform())
                .into(uploaded_img)
        }
    }

    companion object {

        private val TAG = "Places"
        private val placeExtra = "Place"
        fun getStartIntent(activity: Context, place: Place): Intent {
            var intent = Intent(activity, UpdatePlaceActivity::class.java)
            intent.putExtra(placeExtra, place)
            return intent
        }
    }

}
