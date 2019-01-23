package cc.shinichi.openyoureyesmvp.module.userinfo

import cc.shinichi.openyoureyesmvp.bean.TabBean
import cc.shinichi.openyoureyesmvp.module.base.IBasePresenter
import cc.shinichi.openyoureyesmvp.module.base.IBaseView

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyesmvp.module.userinfo
 * create at 2018/9/17  16:06
 * description:
 */
interface IUserInfo {

    interface Presenter : IBasePresenter {

        fun getData(url: String?)
    }

    interface View : IBaseView<Presenter> {

        fun setData(tabBean: TabBean?)

        fun loadFail(msg: String?)
    }
}