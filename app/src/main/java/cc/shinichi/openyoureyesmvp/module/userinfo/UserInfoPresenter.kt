package cc.shinichi.openyoureyesmvp.module.userinfo

import android.content.Context
import cc.shinichi.openyoureyesmvp.api.Api
import cc.shinichi.openyoureyesmvp.api.ApiListener
import cc.shinichi.openyoureyesmvp.model.bean.TabBean
import cc.shinichi.openyoureyesmvp.module.userinfo.IUserInfo.View
import cc.shinichi.openyoureyesmvp.task.BaseTask.Companion.getGson
import com.lzy.okgo.model.Response

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyesmvp.module.userinfo
 * create at 2018/9/17  16:07
 * description:
 */
class UserInfoPresenter: IUserInfo.Presenter {

    private val context: Context
    private val iUserInfoView: IUserInfo.View

    constructor(context: Context, iUserInfoView: View) {
        this.context = context
        this.iUserInfoView = iUserInfoView
    }

    override fun getData() {
    }

    override fun getData(url: String?) {
        Api.getInstance()
                .getAsync(context, url, object : ApiListener() {

                    override fun start() {
                        super.start()
                        iUserInfoView.onShowLoading()
                    }

                    override fun success(string: String?) {
                        super.success(string)
                        iUserInfoView.onHideLoading()
                        iUserInfoView.setData(getGson().fromJson(string, TabBean::class.javaObjectType))
                    }

                    override fun error(response: Response<String>?) {
                        super.error(response)
                        iUserInfoView.loadFail(response?.message().toString())
                    }

                    override fun noNet() {
                        super.noNet()
                        iUserInfoView.onShowNetError()
                        iUserInfoView.onHideLoading()
                    }

                    override fun finish() {
                        super.finish()
                        iUserInfoView.onHideLoading()
                    }
                })
    }
}