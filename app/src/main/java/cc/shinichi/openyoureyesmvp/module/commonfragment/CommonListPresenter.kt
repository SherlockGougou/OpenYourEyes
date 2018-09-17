package cc.shinichi.openyoureyesmvp.module.commonfragment

import android.content.Context
import cc.shinichi.openyoureyesmvp.api.Api
import cc.shinichi.openyoureyesmvp.api.ApiListener
import cc.shinichi.openyoureyesmvp.module.commonfragment.ICommonList.View
import cc.shinichi.openyoureyesmvp.util.UIUtil
import com.lzy.okgo.model.Response

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyesmvp.module.commonfragment
 * create at 2018/9/17  12:15
 * description:
 */
class CommonListPresenter : ICommonList.Presenter {

    private val context: Context?
    private val iCommonListView: ICommonList.View

    constructor(context: Context?, iCommonListView: View) {
        this.context = context
        this.iCommonListView = iCommonListView
    }

    override fun getData() {

    }

    override fun getHomeNewData(url: String?) {
        if (UIUtil.isNull(url)) {
            return
        }
        Api.getInstance().cancelAll()
        Api
                .getInstance()
                .getAsync(context, url, object : ApiListener() {

                    override fun start() {
                        super.start()
                        iCommonListView.onShowLoading()
                    }

                    override fun noNet() {
                        iCommonListView.onShowNetError()
                    }

                    override fun success(string: String?) {
                        iCommonListView.getEntityList(string, true)
                    }

                    override fun error(response: Response<String>?) {
                        iCommonListView.loadFail(response?.message().toString())
                    }

                    override fun finish() {
                        super.finish()
                        iCommonListView.onShowLoading()
                    }
                })
    }

    override fun getNextPageData(nextPageUrl: String?) {
        Api.getInstance()
                .getAsync(context, nextPageUrl, object : ApiListener() {

                    override fun noNet() {
                        iCommonListView.loadMoreFail("")
                        iCommonListView.onShowNetError()
                    }

                    override fun success(string: String?) {
                        iCommonListView.getEntityList(string, false)
                    }

                    override fun error(response: Response<String>?) {
                        iCommonListView.loadMoreFail(response?.message().toString())
                    }
                })
    }
}