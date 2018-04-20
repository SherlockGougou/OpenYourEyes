package cc.shinichi.openyoureyes.api

import android.content.Context
import cc.shinichi.openyoureyes.app.App
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import okhttp3.Response

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/2/23  16:33
* description: 
*/

public class Api(context: Context) {

    companion object {
        fun getInstance(): Api {
            return InnerClass.api
        }
    }

    class InnerClass {
        companion object {
            val api: Api = Api(App.application)
        }
    }

    fun GetSync(context: Context, url: String): String? {
        val response : Response? = OkGo.get<String>(url).tag(context).execute()
        return response?.body()?.string()
    }

    fun GetAsync(context: Context, url: String, listener: ApiListener?) {
        OkGo.get<String>(url).tag(context).execute(object : StringCallback() {
            override fun onSuccess(response: com.lzy.okgo.model.Response<String>?) {
                if (listener == null) {
                    return
                }
                listener.success(response?.body()?.toString())
            }
            override fun onError(response: com.lzy.okgo.model.Response<String>?) {
                super.onError(response)
                if (listener == null) {
                    return
                }
                listener.error(response)
            }
        })
    }
}