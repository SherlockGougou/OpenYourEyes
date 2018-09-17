package cc.shinichi.openyoureyesmvp.module.ranklist

import cc.shinichi.openyoureyesmvp.model.bean.RankTabBean
import cc.shinichi.openyoureyesmvp.module.base.IBasePresenter
import cc.shinichi.openyoureyesmvp.module.base.IBaseView

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyesmvp.module.ranklist
 * create at 2018/9/17  15:34
 * description:
 */
interface IRankList {

    interface Presenter: IBasePresenter {

    }

    interface View : IBaseView<Presenter> {

        fun setData(rankTabBean: RankTabBean?)
    }
}