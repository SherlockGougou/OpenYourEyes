package cc.shinichi.openyoureyesmvp.module.tagcategory

import cc.shinichi.openyoureyesmvp.bean.TabBean
import cc.shinichi.openyoureyesmvp.module.base.IBasePresenter
import cc.shinichi.openyoureyesmvp.module.base.IBaseView

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyesmvp.module.tagcategory
 * create at 2018/9/17  15:48
 * description:
 */
interface ITagCategory {

    interface Presenter : IBasePresenter {

        fun getData(url: String?)
    }

    interface View : IBaseView<Presenter> {

        fun setData(tabBean: TabBean?)

        fun loadFail(msg: String?)
    }
}