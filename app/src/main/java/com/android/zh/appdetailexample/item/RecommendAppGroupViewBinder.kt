package com.android.zh.appdetailexample.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.zh.appdetailexample.GallerySnapHelper
import com.android.zh.appdetailexample.R
import com.android.zh.appdetailexample.model.RecommendAppGroupModel
import com.android.zh.appdetailexample.model.RecommendAppModel
import me.drakeet.multitype.ItemViewBinder
import me.drakeet.multitype.MultiTypeAdapter

class RecommendAppGroupViewBinder(
    /**
     * 点击更多回调
     */
    val clickMoreBlock: (item: RecommendAppGroupModel) -> Unit,
    /**
     * 点击安装回调
     */
    val clickInstallBlock: (item: RecommendAppModel) -> Unit
) :
    ItemViewBinder<RecommendAppGroupModel, RecommendAppGroupViewBinder.ViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_recommend_app_group, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: RecommendAppGroupModel) {
        val context = holder.itemView.context
        item.run {
            holder.vGroupName.text = groupName
            holder.vAppList.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                val items = mutableListOf<RecommendAppModel>().apply {
                    addAll(appList)
                }
                adapter = MultiTypeAdapter(items).apply {
                    register(RecommendAppModel::class.java, RecommendAppViewBinder())
                }
                if (onFlingListener == null) {
                    val gallerySnapHelper = GallerySnapHelper()
                    gallerySnapHelper.attachToRecyclerView(this)
                }
            }
            //更多
            holder.vMore.setOnClickListener {
                clickMoreBlock(item)
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vGroupName: TextView = view.findViewById(R.id.group_name)
        val vAppList: RecyclerView = view.findViewById(R.id.app_list)
        val vMore: TextView = view.findViewById(R.id.more)
    }

    private inner class RecommendAppViewBinder :
        ItemViewBinder<RecommendAppModel, RecommendAppViewBinder.RecommendAppViewHolder>() {
        override fun onCreateViewHolder(
            inflater: LayoutInflater,
            parent: ViewGroup
        ): RecommendAppViewHolder {
            return RecommendAppViewHolder(
                inflater.inflate(
                    R.layout.item_recommend_app,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: RecommendAppViewHolder, item: RecommendAppModel) {
            item.run {
                holder.vIcon.setImageResource(iconResId)
                holder.vAppName.text = name
                holder.vInstall.setOnClickListener {
                    clickInstallBlock(item)
                }
            }
        }

        inner class RecommendAppViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val vIcon: ImageView = view.findViewById(R.id.icon)
            val vAppName: TextView = view.findViewById(R.id.app_name)
            val vInstall: TextView = view.findViewById(R.id.install)
        }
    }
}