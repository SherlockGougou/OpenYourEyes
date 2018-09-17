package cc.shinichi.openyoureyesmvp.module.base

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyesmvp.mvp.common.base
 * create at 2018/9/13  14:04
 * description:
 */
interface IBaseView<T> {

    /**
     * 显示加载动画
     */
    fun onShowLoading()

    /**
     * 隐藏加载
     */
    fun onHideLoading()

    /**
     * 显示网络错误
     */
    fun onShowNetError()
}