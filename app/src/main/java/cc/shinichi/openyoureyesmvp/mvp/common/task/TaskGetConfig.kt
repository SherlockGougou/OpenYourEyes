package cc.shinichi.openyoureyesmvp.mvp.common.task

import cc.shinichi.openyoureyesmvp.api.Api
import cc.shinichi.openyoureyesmvp.api.ApiListener
import cc.shinichi.openyoureyesmvp.app.App
import cc.shinichi.openyoureyesmvp.constant.ApiConstant
import cc.shinichi.openyoureyesmvp.constant.SpTag
import cc.shinichi.openyoureyesmvp.model.bean.ConfigBean
import cc.shinichi.openyoureyesmvp.util.log.ALog
import com.lzy.okgo.model.Response

class TaskGetConfig : BaseTask() {

    companion object {
        fun getConfig() {
            Api.getInstance()
                    .getAsync(App.application, ApiConstant.configUrl, object : ApiListener() {

                        override fun success(string: String?) {
                            try {
                                val config: ConfigBean? =
                                        getGson().fromJson(string, ConfigBean::class.javaObjectType)
                                if (config != null) {
                                    val nextStartPageUrl = config.startPage?.imageUrl
                                    if (!isNull(nextStartPageUrl)) {
                                        getSp().edit()
                                                .putString(SpTag.splashNextPageUrl, nextStartPageUrl)
                                                .apply()
                                        ALog.log(TAG, "sp put nextStartPageUrl: = $nextStartPageUrl")
                                    }
                                }
                            } catch (e: Exception) {
                            }
                        }

                        override fun error(response: Response<String>?) {

                        }
                    })
        }
    }
}