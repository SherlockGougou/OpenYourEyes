package cc.shinichi.openyoureyesmvp.module.commonfragment

import cc.shinichi.openyoureyesmvp.module.base.IBasePresenter
import cc.shinichi.openyoureyesmvp.module.base.IBaseView

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyesmvp.module.commonfragment
 * create at 2018/9/17  12:16
 * description:
 */
interface ICommonList {

    interface Presenter : IBasePresenter {

        fun getHomeNewData(url: String?)

        fun getNextPageData(nextPageUrl: String?)
    }

    interface View : IBaseView<Presenter> {

        fun getEntityList(result: String?, isRefresh: Boolean = false)

        fun loadFail(msg: String)

        fun loadMoreFail(msg: String)
    }
}