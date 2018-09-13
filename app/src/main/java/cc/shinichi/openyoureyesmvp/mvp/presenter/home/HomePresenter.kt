package cc.shinichi.openyoureyesmvp.mvp.presenter.home

import cc.shinichi.openyoureyesmvp.model.bean.CategoryListBean
import cc.shinichi.openyoureyesmvp.mvp.model.home.HomeModel
import cc.shinichi.openyoureyesmvp.mvp.model.home.IHomeModel
import cc.shinichi.openyoureyesmvp.mvp.view.home.IHomeView

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyesmvp.mvp.home
 * create at 2018/9/13  11:56
 * description:
 */
class HomePresenter : IHomePresenter {

    private val iHomeView: IHomeView
    private val iHomeModel: IHomeModel

    constructor(iHomeView: IHomeView) {
        this.iHomeView = iHomeView
        this.iHomeModel = HomeModel(this)
    }

    override fun getCategory() {
        iHomeView.showLoading()
        iHomeModel.getCategoryData()
    }

    override fun getConfig() {
        iHomeModel.getConfigData()
    }

    override fun getCategoryDataSuccess(categoryListBean: CategoryListBean) {
        iHomeView.dismissLoading()
        iHomeView.setData(categoryListBean)
    }

    override fun getCategoryDataFail() {
        iHomeView.dismissLoading()
        iHomeView.loadFail()
    }
}