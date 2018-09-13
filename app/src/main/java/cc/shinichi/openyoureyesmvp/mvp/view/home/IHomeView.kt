package cc.shinichi.openyoureyesmvp.mvp.view.home

import cc.shinichi.openyoureyesmvp.model.bean.CategoryListBean

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyesmvp.mvp.home
 * create at 2018/9/13  11:45
 * description:
 */
interface IHomeView {

    fun showLoading()

    fun dismissLoading()

    fun setData(categoryListBean: CategoryListBean)

    fun loadFail()
}