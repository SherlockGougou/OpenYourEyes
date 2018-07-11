package cc.shinichi.openyoureyes.api

import android.content.Context
import cc.shinichi.openyoureyes.util.CommonUtil
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.request.base.Request
import okhttp3.Response

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

  fun getSync(
    context: Context,
    url: String
  ): String? {
    val response: Response? = OkGo
        .get<String>(url)
        .tag(context)
        .execute()
    return response
        ?.body()
        ?.string()
  }

  fun getAsync(
    context: Context,
    url: String,
    listenerI: IApiListener?
  ) {
    if (!CommonUtil.isConnected()) {
      listenerI?.noNet()
      return
    }
    OkGo
        .get<String>(url)
        .tag(context)
        .execute(object : StringCallback() {

          override fun onStart(request: Request<String, out Request<Any, Request<*, *>>>?) {
            super.onStart(request)
            listenerI?.start()
          }

          override fun onSuccess(response: com.lzy.okgo.model.Response<String>?) {
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