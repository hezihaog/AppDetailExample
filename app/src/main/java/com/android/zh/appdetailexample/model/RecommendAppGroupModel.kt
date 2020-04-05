package com.android.zh.appdetailexample.model

/**
 * @author : lizhi - hezihao
 * e-mail : hezihao@lizhi.fm
 * time   : 2020/04/03
 * desc   :推荐App分组模型
 */
data class RecommendAppGroupModel(
    /**
     * 分组名
     */
    val groupName: String,
    /**
     * 推荐App列表
     */
    val appList: List<RecommendAppModel>
)