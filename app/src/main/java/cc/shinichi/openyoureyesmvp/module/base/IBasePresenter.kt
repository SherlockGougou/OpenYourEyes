package cc.shinichi.openyoureyesmvp.module.base

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyesmvp.mvp.common.base
 * create at 2018/9/13  14:04
 * description:
 */
interface IBasePresenter {

    /**
     * 刷新数据
     */
    fun doRefresh()

    /**
     * 显示网络错误
     */
    fun doShowNetError()
}