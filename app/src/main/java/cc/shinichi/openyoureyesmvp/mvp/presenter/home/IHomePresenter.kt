package cc.shinichi.openyoureyesmvp.mvp.presenter.home

import cc.shinichi.openyoureyesmvp.model.bean.CategoryListBean

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyesmvp.mvp.home
 * create at 2018/9/13  11:47
 * description:
 */
interface IHomePresenter {

    fun getCategory()

    fun getConfig()

    fun getCategoryDataSuccess(categoryListBean: CategoryListBean)

    fun getCategoryDataFail()
}