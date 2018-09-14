package cc.shinichi.openyoureyesmvp.module.home

import cc.shinichi.openyoureyesmvp.model.bean.CategoryListBean
import cc.shinichi.openyoureyesmvp.module.base.IBasePresenter
import cc.shinichi.openyoureyesmvp.module.base.IBaseView

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyesmvp.module.home
 * create at 2018/9/13  17:58
 * description:
 */
interface IHome {

    interface View : IBaseView<Presenter> {

        fun showLoading()

        fun dismissLoading()

        fun setData(categoryListBean: CategoryListBean)

        fun loadFail()
    }

    interface Presenter : IBasePresenter {

        fun getCategory()

        fun getConfig()

        fun getCategoryDataSuccess(categoryListBean: CategoryListBean)

        fun getCategoryDataFail()
    }
}