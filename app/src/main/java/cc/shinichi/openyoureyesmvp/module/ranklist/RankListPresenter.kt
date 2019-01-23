package cc.shinichi.openyoureyesmvp.module.ranklist

import android.content.Context
import cc.shinichi.openyoureyesmvp.api.Api
import cc.shinichi.openyoureyesmvp.api.ApiListener
import cc.shinichi.openyoureyesmvp.bean.RankTabBean
import cc.shinichi.openyoureyesmvp.constant.ApiConstant
import cc.shinichi.openyoureyesmvp.module.ranklist.IRankList.View
import cc.shinichi.openyoureyesmvp.task.BaseTask.Companion.getGson
import com.lzy.okgo.model.Response

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyesmvp.module.ranklist
 * create at 2018/9/17  15:38
 * description:
 */
class RankListPresenter : IRankList.Presenter {

    private val context: Context
    private val iRankListView: IRankList.View

    constructor(context: Context, iRankListView: View) {
        this.context = context
        this.iRankListView = iRankListView
    }

    override fun getData() {
        Api.getInstance()
                .getAsync(context, ApiConstant.rankListConfigUrl, object : ApiListener() {

                    override fun start() {
                        super.start()
                        iRankListView.onShowLoading()
                    }

                    override fun success(string: String?) {
                        super.success(string)
                        iRankListView.onHideLoading()
                        iRankListView.setData(getGson().fromJson(string, RankTabBean::class.javaObjectType))
                    }

                    override fun error(response: Response<String>?) {
                        super.error(response)
                        iRankListView.onHideLoading()
                    }

                    override fun noNet() {
                        super.noNet()
                        iRankListView.onHideLoading()
                        iRankListView.onShowNetError()
                    }
                })
    }
}