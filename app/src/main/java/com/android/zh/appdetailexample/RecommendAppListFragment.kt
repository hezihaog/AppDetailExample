package com.android.zh.appdetailexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.zh.appdetailexample.item.RecommendAppGroupViewBinder
import com.android.zh.appdetailexample.model.RecommendAppGroupModel
import com.android.zh.appdetailexample.model.RecommendAppModel
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter

class RecommendAppListFragment : Fragment() {
    private lateinit var vRefreshLayout: SmartRefreshLayout
    private lateinit var vRefreshList: RecyclerView

    companion object {
        fun newInstance(args: Bundle? = null): RecommendAppListFragment {
            val fragment = RecommendAppListFragment()
            fragment.arguments = args ?: Bundle()
            return fragment
        }
    }

    private val mListItem by lazy {
        Items()
    }

    private val mListAdapter by lazy {
        MultiTypeAdapter(mListItem).apply {
            register(RecommendAppGroupModel::class.java, RecommendAppGroupViewBinder({
                Toast.makeText(activity, "更多：${it.groupName}", Toast.LENGTH_SHORT).show()
            }, {
                Toast.makeText(activity, "安装：${it.name}", Toast.LENGTH_SHORT).show()
            }))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.base_refresh_list_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findView(view)
        bindView()
        setData()
    }

    private fun findView(view: View) {
        vRefreshLayout = view.findViewById(R.id.refresh_layout)
        vRefreshList = view.findViewById(R.id.refresh_list)
    }

    private fun bindView() {
        vRefreshLayout.apply {
            setEnableLoadMore(false)
            setOnRefreshListener {
                refresh()
            }
        }
        vRefreshList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mListAdapter
            addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
        }
    }

    private fun setData() {
        refresh()
    }

    private fun refresh() {
        mListItem.clear()
        for (index in 0..15) {
            mListItem.add(
                RecommendAppGroupModel(
                    "group：$index",
                    mutableListOf<RecommendAppModel>().apply {
                        for (i in 0..10) {
                            add(
                                RecommendAppModel(
                                    R.mipmap.ic_launcher_round,
                                    "抖音：${i}",
                                    "com.bytedance.douyin"
                                )
                            )
                        }
                    })
            )
        }
        mListAdapter.notifyDataSetChanged()
        vRefreshLayout.finishRefresh(true)
    }
}