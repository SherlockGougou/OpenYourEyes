package cc.shinichi.openyoureyes.api

import android.content.Context
import cc.shinichi.openyoureyesmvp.app.App
import cc.shinichi.openyoureyesmvp.util.CommonUtil
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.lzy.okgo.request.base.Request

/**
 * @author 工藤
 * @email gougou@16fan.com
 * create at 2018/2/23  16:33
 * description:
 */

open class Api {

    companion object {
        fun getInstance(): Api {
            return InnerClass
                    .api
        }
    }

    class InnerClass {
        companion object {
            val api: Api = Api()
        }
    }

    fun cancelAll() {
        OkGo.cancelAll(OkGo.getInstance().okHttpClient)
    }

    fun getAsync(
            context: Context?,
            url: String?,
            listenerI: IApiListener?
    ) {
        if (!CommonUtil.isConnected()) {
            listenerI?.noNet()
            return
        }
        OkGo
                .get<String>(url)
                .params("udid", "23074f87cfef48bd9a52b3ad1c054dada94e999e")
                .params("vc", "376")
                .params("vn", "4.2.2")
                .params("deviceModel", "DE106")
                .params("system_version_code", "27")
                .tag(context ?: App.application)
                .execute(object : StringCallback() {

                    override fun onStart(request: Request<String, out Request<Any, Request<*, *>>>?) {
                        super.onStart(request)
                        listenerI?.start()
                    }

                    override fun onSuccess(response: com.lzy.okgo.model.Response<String>?) {
                        listenerI
                                ?.success(response?.body()?.toString())
                    }

                    override fun onCacheSuccess(response: Response<String>?) {
                        listenerI
                                ?.success(response?.body()?.toString())
                    }

                    override fun onError(response: com.lzy.okgo.model.Response<String>?) {
                        super
                                .onError(response)
                        listenerI
                                ?.error(response)
                    }

                    override fun onFinish() {
                        super.onFinish()
                        listenerI?.finish()
                    }
                })
    }
}