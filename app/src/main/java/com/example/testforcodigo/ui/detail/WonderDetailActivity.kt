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
import javax.inject.Inject

class WonderDetailActivity : BaseActivity() {
    companion object {
        private var tvTitle: TextView? = null
        private var tvDesc: TextView? = null
        private var ivWonder: AppCompatImageView? = null
        private var viewModel: WonderDetailViewModel? = null
        private var mActivity: Activity? = null
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
        }
    }


}