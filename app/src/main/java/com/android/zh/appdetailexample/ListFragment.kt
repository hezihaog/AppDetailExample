package com.android.zh.appdetailexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.zh.appdetailexample.item.ListItemViewBinder
import com.android.zh.appdetailexample.model.ListItemModel
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter

/**
 * @author : lizhi - hezihao
 * e-mail : hezihao@lizhi.fm
 * time   : 2020/04/03
 * desc   :
 */
class ListFragment : Fragment() {
    private lateinit var vRefreshLayout: SmartRefreshLayout
    private lateinit var vRefreshList: RecyclerView

    companion object {
        const val MODULE_NAME = "module_name"

        fun newInstance(args: Bundle? = null): ListFragment {
            val fragment = ListFragment()
            fragment.arguments = args ?: Bundle()
            return fragment
        }
    }

    private val mListItem by lazy {
        Items()
    }

    private val mListAdapter by lazy {
        MultiTypeAdapter(mListItem).apply {
            register(ListItemModel::class.java, ListItemViewBinder())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
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
            setOnRefreshListener {
                refresh()
            }
            setOnLoadMoreListener {
                loadMore()
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
            mListItem.add(ListItemModel("item：$index"))
        }
        mListAdapter.notifyDataSetChanged()
        vRefreshLayout.finishRefresh(true)
    }

    private fun loadMore() {
        val newItems = mutableListOf<ListItemModel>()
        for (index in 0..15) {
            newItems.add(ListItemModel("new item"))
        }
        val startIndex = mListItem.size
        mListItem.addAll(newItems)
        mListAdapter.notifyItemRangeInserted(startIndex, newItems.size)
        vRefreshLayout.finishLoadMore(true)
    }
}