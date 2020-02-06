package com.example.testforcodigo.ui.detail

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.AppCompatImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.testforcodigo.R
import com.example.testforcodigo.base.BaseActivity
import com.example.testforcodigo.data.model.Wonder
import com.example.testforcodigo.util.ViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject

class WonderDetailActivity : BaseActivity(), OnMapReadyCallback {
    companion object {
        private var tvTitle: TextView? = null
        private var tvDesc: TextView? = null
        private var ivWonder: AppCompatImageView? = null
        private var viewModel: WonderDetailViewModel? = null
        private var mActivity: Activity? = null
        private var lattitude = 0.0
        private var longtitude = 0.0
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun layoutRes(): Int {
        return R.layout.fragment_wonder_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        tvTitle = findViewById(R.id.tv_title)
        tvDesc = findViewById(R.id.tv_desc)
        ivWonder = findViewById(R.id.iv_wonder)
        mActivity = this
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_view) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(WonderDetailViewModel::class.java)
        viewModel!!.getWonderDetail().observe(this, WonderDetailObserver())
        viewModel!!.loadWonderDetail(this)
    }

    private class WonderDetailObserver : Observer<Wonder?> {
        override fun onChanged(wonderDetail: Wonder?) {
            if (wonderDetail == null) return
            tvTitle!!.text = wonderDetail.location
            tvDesc!!.text = wonderDetail.description
            Glide.with(mActivity!!).load(wonderDetail.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate().fitCenter().placeholder(R.drawable.ic_holder)
                .error(R.drawable.ic_holder)
                .into(ivWonder!!)
            lattitude = wonderDetail.lat!!.toDouble()
            longtitude = wonderDetail.long!!.toDouble()
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        val latLng =
            LatLng(lattitude, longtitude)
        val map: GoogleMap = p0!!
        map.uiSettings.isMyLocationButtonEnabled = false
        map.addMarker(
            MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin))
        )
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
    }


}