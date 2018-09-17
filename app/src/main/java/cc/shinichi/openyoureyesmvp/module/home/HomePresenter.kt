package cc.shinichi.openyoureyesmvp.module.home

import android.content.Context
import cc.shinichi.openyoureyesmvp.api.Api
import cc.shinichi.openyoureyesmvp.api.ApiListener
import cc.shinichi.openyoureyesmvp.constant.ApiConstant
import cc.shinichi.openyoureyesmvp.model.bean.CategoryListBean
import cc.shinichi.openyoureyesmvp.module.home.IHome.View
import cc.shinichi.openyoureyesmvp.task.BaseTask
import cc.shinichi.openyoureyesmvp.task.BaseTask.Companion.getGson
import cc.shinichi.openyoureyesmvp.task.TaskGetConfig
import cc.shinichi.openyoureyesmvp.util.CommonUtil
import com.lzy.okgo.model.Response

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyesmvp.mvp.home
 * create at 2018/9/13  11:56
 * description:
 */
class HomePresenter : IHome.Presenter {

    private val context: Context
    private val iHomeView: IHome.View

    constructor(context: Context, iHomeView: View) {
        this.context = context
        this.iHomeView = iHomeView
    }

    override fun getData() {
        iHomeView.onShowLoading()
        Api
                .getInstance()
                .getAsync(null, ApiConstant.categoryUrl, object : ApiListener() {

                    override fun noNet() {
                        super.noNet()
                        getCategoryDataFromAssets()
                    }

                    override fun success(string: String?) {
                        val categoryListBean = BaseTask.getGson().fromJson(string,
                                CategoryListBean::class.javaObjectType)
                        if (categoryListBean != null) {
                            iHomeView.setData(categoryListBean)
                        } else {
                            getCategoryDataFromAssets()
                        }
                    }

                    override fun error(response: Response<String>?) {
                        getCategoryDataFromAssets()
                    }
                })
    }

    override fun getConfig() {
        TaskGetConfig.getConfig()
    }

    private fun getCategoryDataFromAssets() {
        val categoryListBean = getGson().fromJson(
                CommonUtil.getStringFromAssets("data", "defaultConfig"),
                CategoryListBean::class.javaObjectType
        )
        if (categoryListBean != null) {
            iHomeView.setData(categoryListBean)
        } else {
            iHomeView.loadFail("数据异常，请退出重试")
        }
    }
}