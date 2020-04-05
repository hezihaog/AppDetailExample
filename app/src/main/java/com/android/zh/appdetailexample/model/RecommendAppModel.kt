package com.android.zh.appdetailexample.model

/**
 * @author : lizhi - hezihao
 * e-mail : hezihao@lizhi.fm
 * time   : 2020/04/03
 * desc   :推荐App模型
 */
data class RecommendAppModel(
    /**
     * 图标的资源Id
     */
    val iconResId: Int,
    /**
     * 名称
     */
    val name: String,
    /**
     * 包名
     */
    val packageName: String
)