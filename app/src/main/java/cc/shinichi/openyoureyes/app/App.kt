package cc.shinichi.openyoureyes.app

import android.app.Application
import cc.shinichi.openyoureyes.constant.Config
import com.facebook.drawee.backends.pipeline.Fresco
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.interceptor.HttpLoggingInterceptor
import com.lzy.okgo.interceptor.HttpLoggingInterceptor.Level.BASIC
import com.lzy.okgo.interceptor.HttpLoggingInterceptor.Level.BODY
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit.SECONDS
import java.util.logging.Level

/**
 * @author 工藤
 * @email gougou@16fan.com
 * create at 2018/2/23  14:57
 * description: application
 */
class App : Application() {

  companion object {
    lateinit var application: App
  }

  override fun onCreate() {
    super
        .onCreate()
    application = this

    // fresco
    Fresco.initialize(application)

    // okgo
    val builder = OkHttpClient
        .Builder()
    builder
        .connectTimeout(5, SECONDS)
    builder
        .writeTimeout(10, SECONDS)
    builder
        .readTimeout(10, SECONDS)
    val httpLog = HttpLoggingInterceptor("shinichi")
    httpLog.setColorLevel(Level.INFO)
    httpLog.setPrintLevel(BASIC)
    if (Config.isPrintLog) {
      builder.addInterceptor(httpLog)
    }
    OkGo
        .getInstance()
        .setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
        .setCacheTime(3600000 * 2) // 缓存2小时
        .setRetryCount(2)
        .setOkHttpClient(builder.build())
        .init(application)
  }
}