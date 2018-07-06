package cc.shinichi.openyoureyes.task

import cc.shinichi.openyoureyes.api.Api
import cc.shinichi.openyoureyes.api.ApiListener
import cc.shinichi.openyoureyes.app.App
import cc.shinichi.openyoureyes.constant.Constant
import cc.shinichi.openyoureyes.constant.SpTag
import cc.shinichi.openyoureyes.model.bean.Config
import cc.shinichi.openyoureyes.util.log.ALog
import com.lzy.okgo.model.Response
import kotlin.concurrent.thread

class TaskGetConfig : BaseTask() {

  companion object {
    fun getConfig() {
      thread {
        Api.getInstance()
            .GetAsync(App.application, Constant.配置文件接口, object : ApiListener {
              override fun success(string: String?) {
                var config: Config? = getGson().fromJson(string, Config::class.javaObjectType)
                if (config != null) {
                  val nextStartPageUrl = config.startPage.imageUrl
                  if (!isNull(nextStartPageUrl)) {
                    getSp().edit().putString(SpTag.启动图链接, nextStartPageUrl).apply()
                    ALog.log(TAG, nextStartPageUrl)
                  }
                  config = null
                }
              }

              override fun error(response: Response<String>?) {

              }
            })
      }
    }
  }
}