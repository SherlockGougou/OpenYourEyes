package cc.shinichi.openyoureyesmvp.mvp.model.home

import cc.shinichi.openyoureyesmvp.api.Api
import cc.shinichi.openyoureyesmvp.api.ApiListener
import cc.shinichi.openyoureyesmvp.constant.ApiConstant
import cc.shinichi.openyoureyesmvp.model.bean.CategoryListBean
import cc.shinichi.openyoureyesmvp.mvp.common.task.BaseTask.Companion.getGson
import cc.shinichi.openyoureyesmvp.mvp.common.task.TaskGetConfig
import cc.shinichi.openyoureyesmvp.mvp.presenter.home.IHomePresenter
import cc.shinichi.openyoureyesmvp.util.CommonUtil
import com.lzy.okgo.model.Response

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyesmvp.mvp.home
 * create at 2018/9/13  11:44
 * description:
 */
class HomeModel : IHomeModel {

    private val iHomePresenter: IHomePresenter

    constructor(iHomePresenter: IHomePresenter) {
        this.iHomePresenter = iHomePresenter
    }

    override fun getCategoryData() {
        Api
                .getInstance()
                .getAsync(null, ApiConstant.categoryUrl, object : ApiListener() {

                    override fun noNet() {
                        super.noNet()
                        getCategoryDataFromAssets()
                    }

                    override fun success(string: String?) {
                        val categoryListBean = getGson().fromJson(string, CategoryListBean::class.javaObjectType)
                        if (categoryListBean != null) {
                            iHomePresenter.getCategoryDataSuccess(categoryListBean)
                        } else {
                            getCategoryDataFromAssets()
                        }
                    }

                    override fun error(response: Response<String>?) {
                        getCategoryDataFromAssets()
                    }
                })
    }

    override fun getConfigData() {
        TaskGetConfig.getConfig()
    }

    private fun getCategoryDataFromAssets() {
        val categoryListBean = getGson().fromJson(
                CommonUtil.getStringFromAssets("data", "defaultConfig"),
                CategoryListBean::class.javaObjectType
        )
        if (categoryListBean != null) {
            iHomePresenter.getCategoryDataSuccess(categoryListBean)
        } else {
            iHomePresenter.getCategoryDataFail()
        }
    }
}