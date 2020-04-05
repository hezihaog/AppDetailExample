package com.android.zh.appdetailexample

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var vAppBar: AppBarLayout
    private lateinit var vTab: TabLayout
    private lateinit var vPager: ViewPager

    private val mTabList by lazy {
        mutableListOf(
            vTab.newTab().apply {
                text = "详情"
            },
            vTab.newTab().apply {
                text = "评论"
            },
            vTab.newTab().apply {
                text = "推荐"
            }
        )
    }

    private val mFragmentList by lazy {
        mutableListOf<Fragment>().apply {
            //详情
            add(ListFragment.newInstance())
            //评论
            add(ListFragment.newInstance())
            //推荐
            add(
                RecommendAppListFragment.newInstance()
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val root: View = findViewById(android.R.id.content)
        findView(root)
        bindView()
    }

    private fun findView(view: View) {
        vAppBar = view.findViewById(R.id.app_bar)
        vTab = view.findViewById(R.id.tab)
        vPager = view.findViewById(R.id.pager)
    }

    private fun bindView() {
        vAppBar.apply {
            addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
                Log.d(TAG, "AppBarLayout => verticalOffset：$verticalOffset")
            })
        }
        vPager.apply {
            adapter = object : FragmentPagerAdapter(supportFragmentManager) {
                override fun getItem(position: Int): Fragment {
                    return mFragmentList[position]
                }

                override fun getCount(): Int {
                    return mFragmentList.size
                }

                override fun getPageTitle(position: Int): CharSequence? {
                    return mTabList[position].text
                }
            }
        }
        vTab.apply {
            setupWithViewPager(vPager)
        }
    }
}