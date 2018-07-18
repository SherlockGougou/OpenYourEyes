package cc.shinichi.openyoureyes.task

import cc.shinichi.openyoureyes.api.Api
import cc.shinichi.openyoureyes.api.ApiListener
import cc.shinichi.openyoureyes.app.App
import cc.shinichi.openyoureyes.constant.Constant
import cc.shinichi.openyoureyes.constant.SpTag
import cc.shinichi.openyoureyes.model.bean.ConfigBean
import cc.shinichi.openyoureyes.util.log.ALog
import com.lzy.okgo.model.Response

class TaskGetConfig : BaseTask() {

  companion object {
    fun getConfig() {
      Api.getInstance()
          .getAsync(App.application, Constant.configUrl, object : ApiListener() {

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