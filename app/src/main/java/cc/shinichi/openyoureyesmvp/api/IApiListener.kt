package cc.shinichi.openyoureyesmvp.api

import com.lzy.okgo.model.Response

/**
 * @author 工藤
 * @email gougou@16fan.com
 * cc.shinichi.openyoureyesmvp.api
 * create at 2018/4/20  17:16
 * description: 网络请求统一回调
 */
interface IApiListener {
    fun start()
    fun success(string: String?)
    fun error(response: Response<String>?)
    fun noNet()
    fun finish()
}