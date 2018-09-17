package cc.shinichi.openyoureyesmvp.module.category

import cc.shinichi.openyoureyesmvp.model.bean.AllCategoryBean
import cc.shinichi.openyoureyesmvp.module.base.IBasePresenter
import cc.shinichi.openyoureyesmvp.module.base.IBaseView

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyesmvp.module.category
 * create at 2018/9/17  10:32
 * description:
 */
interface IAllCategory {

    interface Presenter : IBasePresenter

    interface View : IBaseView<Presenter> {

        fun setData(categoryBean: AllCategoryBean)

        fun loadFail(msg: String)
    }
}