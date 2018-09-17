package cc.shinichi.openyoureyesmvp.module.campaignlist

import cc.shinichi.openyoureyesmvp.module.base.IBasePresenter
import cc.shinichi.openyoureyesmvp.module.base.IBaseView

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyesmvp.module.campaignlist
 * create at 2018/9/17  14:59
 * description:
 */
interface ICampaignList {

    interface Presenter : IBasePresenter {

        override fun getData() {

        }

        fun getNextPageData(nextPageUrl: String?)
    }

    interface View : IBaseView<Presenter> {

        fun getEntityList(result: String?, isRefresh: Boolean = false)

        fun loadFail(msg: String)

        fun loadMoreFail(msg: String)
    }
}