package com.android.zh.appdetailexample.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.zh.appdetailexample.R
import com.android.zh.appdetailexample.model.ListItemModel
import me.drakeet.multitype.ItemViewBinder

/**
 * @author : lizhi - hezihao
 * e-mail : hezihao@lizhi.fm
 * time   : 2020/04/03
 * desc   :
 */
class ListItemViewBinder : ItemViewBinder<ListItemModel, ListItemViewBinder.ViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: ListItemModel) {
        item.run {
            holder.vText.text = text
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vText: TextView = view.findViewById(R.id.text)
    }
}