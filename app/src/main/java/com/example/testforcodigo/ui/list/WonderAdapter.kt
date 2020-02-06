package com.example.testforcodigo.ui.list

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.testforcodigo.R
import com.example.testforcodigo.data.model.Wonder
import com.example.testforcodigo.ui.detail.WonderDetailActivity
import com.example.testforcodigo.util.Constants
import com.google.gson.Gson


class WonderAdapter(
    activity: AppCompatActivity,
    wonderList: List<Wonder>
) : RecyclerView.Adapter<WonderAdapter.ViewHolder?>() {
    private val mItems: List<Wonder> = wonderList
    private val activity: AppCompatActivity = activity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wonder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wonder: Wonder = mItems[position]
        holder.tvName.text = wonder.location
        Glide.with(activity).load(wonder.image).diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate().fitCenter().placeholder(R.drawable.ic_holder).error(R.drawable.ic_holder)
            .into(holder.ivWonder)
        holder.tvCardView.setOnClickListener(View.OnClickListener {
            val prefs = activity.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE)
            val gSon = Gson()
            val json = gSon.toJson(wonder)
            prefs!!.edit().putString(Constants.WONDER_DETAIL, json).apply()
            val intent = Intent(activity, WonderDetailActivity::class.java)
            activity.startActivity(intent)

        })
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var ivWonder: AppCompatImageView = itemView.findViewById(R.id.iv_wonder)
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
        var tvCardView: CardView = itemView.findViewById(R.id.tv_card_view)

    }

    override fun getItemCount(): Int {
        return mItems.size
    }
}
