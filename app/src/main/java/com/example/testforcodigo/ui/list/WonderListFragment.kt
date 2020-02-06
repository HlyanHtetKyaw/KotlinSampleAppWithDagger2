package com.example.testforcodigo.ui.list

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.testforcodigo.R
import com.example.testforcodigo.base.BaseFragment
import com.example.testforcodigo.data.model.Wonder
import com.example.testforcodigo.util.ViewModelFactory
import com.google.gson.Gson
import io.reactivex.annotations.Nullable
import javax.inject.Inject

class WonderListFragment : BaseFragment() {

    companion object {
        private const val TAG = "WonderListFrag"
        private var loadingView: ProgressBar? = null
        private var recyclerView: RecyclerView? = null
        private var tvError: TextView? = null
        private var wonderAdapter: WonderAdapter? = null
        private var mActivity: Activity? = null
        private var viewModel: WonderListViewModel? = null

    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun layoutRes(): Int {
        return R.layout.fragment_wonder_list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        mActivity = activity
        recyclerView = mActivity!!.findViewById(R.id.recycler_view)
        tvError = mActivity!!.findViewById(R.id.tv_error)
        loadingView = mActivity!!.findViewById(R.id.loading_view)
        val llm = LinearLayoutManager(mActivity)
        recyclerView!!.layoutManager = llm
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(WonderListViewModel::class.java)
        viewModel!!.getLoading().observe(this,
            LoadingObserver()
        )
        viewModel!!.getWonders().observe(this,
            WonderObserver()
        )
        viewModel!!.getError().observe(this,
            ErrorObserver()
        )
        viewModel!!.loadWondersfromDB()
    }

    private class WonderObserver : Observer<List<Wonder>?> {
        override fun onChanged(wonderList: List<Wonder>?) {
            Log.d(TAG, Gson().toJson(wonderList))
            if (wonderList == null) return
            wonderAdapter = WonderAdapter(
                mActivity!!,
                wonderList
            )
            recyclerView!!.adapter =
                wonderAdapter

        }
    }

    private class LoadingObserver : Observer<Boolean?> {
        override fun onChanged(@Nullable isLoading: Boolean?) {
            if (isLoading == null) return
            if (isLoading) {
                loadingView!!.visibility = View.VISIBLE
            } else {
                loadingView!!.visibility = View.GONE
            }
        }
    }

    private class ErrorObserver : Observer<Boolean?> {
        override fun onChanged(@Nullable isError: Boolean?) {
            if (isError == null) return
            if (isError) {
                tvError!!.visibility = View.VISIBLE
            } else {
                tvError!!.visibility = View.GONE
            }
        }
    }

}