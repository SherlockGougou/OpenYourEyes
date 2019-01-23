package cc.shinichi.openyoureyesmvp.module.campaignlist

import android.content.Context
import cc.shinichi.openyoureyesmvp.api.Api
import cc.shinichi.openyoureyesmvp.api.ApiListener
import cc.shinichi.openyoureyesmvp.constant.ApiConstant
import cc.shinichi.openyoureyesmvp.module.campaignlist.ICampaignList.View
import com.lzy.okgo.model.Response

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyesmvp.module.campaignlist
 * create at 2018/9/17  15:01
 * description:
 */
class CampaignListPresenter : ICampaignList.Presenter {

    private val context: Context
    private val iCampaignListView: ICampaignList.View

    constructor(context: Context, iCampaignListView: View) {
        this.context = context
        this.iCampaignListView = iCampaignListView
    }

    override fun getData() {
        Api.getInstance()
                .getAsync(context, ApiConstant.campaignListUrl, object : ApiListener() {
                    override fun success(string: String?) {
                        super.success(string)
                        iCampaignListView.getEntityList(string, true)
                    }

                    override fun error(response: Response<String>?) {
                        super.error(response)
                        iCampaignListView.loadFail(response?.message().toString())
                    }

                    override fun start() {
                        super.start()
                        iCampaignListView.onShowLoading()
                    }

                    override fun finish() {
                        super.finish()
                        iCampaignListView.onHideLoading()
                    }
                })

    }

    override fun getNextPageData(nextPageUrl: String?) {
        Api.getInstance()
                .getAsync(context, nextPageUrl, object : ApiListener() {

                    override fun noNet() {
                        iCampaignListView.loadMoreFail("")
                        iCampaignListView.onShowNetError()
                    }

                    override fun success(string: String?) {
                        iCampaignListView.getEntityList(string, true)
                    }

                    override fun error(response: Response<String>?) {
                        iCampaignListView.loadMoreFail(response?.message().toString())
                    }
                })
    }
}