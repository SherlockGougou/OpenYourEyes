package cc.shinichi.openyoureyes.app

import android.app.Application
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheMode
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit.SECONDS

/*
* @author 工藤
* @emil gougou@16fan.com
* create at 2018/2/23  14:57
* description: application
*/
class App : Application() {

    companion object {
        lateinit var application: App
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        // okgo
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(5, SECONDS)
        builder.writeTimeout(10, SECONDS)
        builder.readTimeout(10, SECONDS)
        OkGo.getInstance()
                .setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .setCacheTime(3600000)// 缓存1小时
                .setRetryCount(2)
                .setOkHttpClient(builder.build())
                .init(application)
    }
}